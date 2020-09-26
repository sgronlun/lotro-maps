package delta.games.lotro.maps.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JCheckBox;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.labels.LabelWithHalo;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.ui.layers.Layer;
import delta.games.lotro.maps.ui.layers.MarkersLayer;
import delta.games.lotro.maps.ui.location.MapLocationController;
import delta.games.lotro.maps.ui.location.MapLocationPanelController;
import delta.games.lotro.maps.ui.navigation.MapViewDefinition;

/**
 * Controller for a map panel.
 * <p>This includes:
 * <ul>
 * <li>a map canvas,
 * <li>a location display.
 * </ul>
 * @author DAM
 */
public class MapPanelController
{
  // Data
  private MapCanvas _canvas;
  // Controllers
  private MapLocationController _locationController;
  private MapLocationPanelController _locationDisplay;
  // UI
  private JLayeredPane _layers;
  private JPanel _labeled;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   */
  public MapPanelController(MapsManager mapsManager)
  {
    _canvas=new MapCanvas(mapsManager.getBasemapsManager());
    // Init zoom controller
    initZoomController();
    // Init pan controller
    initPanController();
    // Location
    _locationController=new MapLocationController(_canvas);
    // Location display
    _locationDisplay=new MapLocationPanelController();
    _locationController.addListener(_locationDisplay);
    // Assembly components in a layered pane
    _layers=new JLayeredPane();
    // - canvas
    _layers.add(_canvas,Integer.valueOf(0),0);
    _canvas.setLocation(0,0);
    // - location
    JPanel locationPanel=_locationDisplay.getPanel();
    _layers.add(locationPanel,Integer.valueOf(1),0);
    // - labeled checkbox
    _labeled=buildLabeledCheckboxPanel();
    _layers.add(_labeled,Integer.valueOf(1),0);
  }

  private void initZoomController()
  {
    // Zoom with mouse wheel
    MouseAdapter adapter=new MouseAdapter()
    {
      @Override
      public void mouseWheelMoved(MouseWheelEvent e)
      {
        int rotation=e.getWheelRotation();
        if (rotation<0)
        {
          _canvas.zoom((float)Math.sqrt(2));
        }
        else
        {
          _canvas.zoom(1/(float)Math.sqrt(2));
        }
      }
    };
    _canvas.addMouseWheelListener(adapter);
  }

  private void initPanController()
  {
    // Pan on mouse drag or shift/mouse click
    MouseAdapter adapter=new MouseAdapter()
    {
      private int _xCenter;
      private int _yCenter;
      private int _lastX;
      private int _lastY;

      public void mouseDragged(MouseEvent e)
      {
        int deltaX=e.getX()-_lastX;
        int deltaY=e.getY()-_lastY;
        _canvas.pan(_xCenter-deltaX,_yCenter-deltaY);
        _lastX=e.getX();
        _lastY=e.getY();
      }

      @Override
      public void mousePressed(MouseEvent e)
      {
        _xCenter=_canvas.getWidth()/2;
        _yCenter=_canvas.getHeight()/2;
        _lastX=e.getX();
        _lastY=e.getY();
      }

      @Override
      public void mouseClicked(MouseEvent event)
      {
        int button=event.getButton();
        int modifiers=event.getModifiers();
        int x=event.getX();
        int y=event.getY();

        if ((button==MouseEvent.BUTTON1) && ((modifiers&MouseEvent.SHIFT_MASK)!=0))
        {
          _canvas.pan(x,y);
        }
      }
    };
    _canvas.addMouseListener(adapter);
    _canvas.addMouseMotionListener(adapter);
  }

  private JPanel buildLabeledCheckboxPanel()
  {
    JPanel panel=GuiFactory.buildPanel(new FlowLayout(FlowLayout.LEADING));
    final JCheckBox labeled=GuiFactory.buildCheckbox("");
    panel.add(labeled);
    labeled.setFocusPainted(false);
    LabelWithHalo haloLabel=new LabelWithHalo();
    haloLabel.setForeground(Color.WHITE);
    haloLabel.setText("Labeled");
    panel.add(haloLabel);
    ActionListener al=new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        useLabels(labeled.isSelected());
        _canvas.repaint();
      }
    };
    labeled.addActionListener(al);
    return panel;
  }

  private void useLabels(boolean useLabels)
  {
    for(Layer layer : _canvas.getLayers())
    {
      if (layer instanceof MarkersLayer)
      {
        MarkersLayer markersLayer=(MarkersLayer)layer;
        markersLayer.useLabels(useLabels);
      }
    }
  }

  /**
   * Get the managed canvas.
   * @return the managed canvas.
   */
  public MapCanvas getCanvas()
  {
    return _canvas;
  }

  /**
   * Get the managed layered pane.
   * @return the managed layered pane.
   */
  public JLayeredPane getLayers()
  {
    return _layers;
  }

  /**
   * Set the map to display.
   * @param mapKey Map identifier.
   */
  public void setMap(int mapKey)
  {
    MapViewDefinition mapViewDefinition=new MapViewDefinition(mapKey,null,null);
    setMap(mapViewDefinition);
  }

  /**
   * Set the map to display.
   * @param mapViewDefinition Map view definition.
   */
  public void setMap(MapViewDefinition mapViewDefinition)
  {
    int mapKey=mapViewDefinition.getMapKey();
    _canvas.setMap(mapKey);
    //System.out.println("Key: "+key);
    // Set map size
    Dimension viewSize=mapViewDefinition.getDimension();
    if (viewSize==null)
    {
      Dimension size=new Dimension(1024,768);
      viewSize=fitInSize(size);
    }
    GeoReference viewReference=mapViewDefinition.getViewReference();
    if (viewReference!=null)
    {
      _canvas.setViewReference(viewReference);
    }
    _canvas.setSize(viewSize);
    _layers.setPreferredSize(viewSize);
    int height=viewSize.height;
    // Place location display (lower left)
    JPanel locationPanel=_locationDisplay.getPanel();
    locationPanel.setSize(100,40);
    locationPanel.setLocation(0,height-locationPanel.getHeight());
    // Place 'labeled' checkbox
    _labeled.setLocation(55,17);
    _labeled.setSize(_labeled.getPreferredSize());
    _canvas.repaint();
  }

  private Dimension fitInSize(Dimension maxSize)
  {
    // Compute adequate zoom factor
    final Dimension mapSize=_canvas.getPreferredSize();
    //System.out.println("Map size: "+mapSize);
    //System.out.println("Size: "+maxSize);
    if ((mapSize.width<=maxSize.width) && (mapSize.height<=maxSize.height))
    {
      return mapSize;
    }
    float xFactor=(float)mapSize.width/maxSize.width;
    float yFactor=(float)mapSize.height/maxSize.height;
    final float factor=Math.max(xFactor,yFactor);
    //System.out.println("Factor: "+factor);
    Runnable r=new Runnable()
    {
      public void run()
      {
        _canvas.pan(mapSize.width/2,mapSize.height/2);
        _canvas.zoom(1/factor);
      }
    };
    SwingUtilities.invokeLater(r);
    Dimension size=new Dimension((int)(mapSize.width/factor),(int)(mapSize.height/factor));
    return size;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Controllers
    if (_locationDisplay!=null)
    {
      if (_locationController!=null)
      {
        _locationController.removeListener(_locationDisplay);
      }
      _locationDisplay.dispose();
      _locationDisplay=null;
    }
    if (_locationController!=null)
    {
      _locationController.dispose();
      _locationController=null;
    }
    // Data
    if (_canvas!=null)
    {
      _canvas.dispose();
      _canvas=null;
    }
    // UI
    _layers=null;
    _labeled=null;
  }
}
