package delta.games.lotro.maps.ui;

import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import delta.games.lotro.maps.data.Map;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.ui.location.MapLocationController;
import delta.games.lotro.maps.ui.location.MapLocationPanelController;

/**
 * Controller for a map panel.
 * <p>This includes:
 * <ul>
 * <li>a map canvas,
 * <li>a location display,
 * <li>navigation between maps.
 * </ul>
 * @author DAM
 */
public class MapPanelController implements NavigationListener
{
  private MapCanvas _canvas;
  private MapLocationController _locationController;
  private MapLocationPanelController _locationDisplay;
  private NavigationManager _navigation;
  private JLayeredPane _layers;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   */
  public MapPanelController(MapsManager mapsManager)
  {
    _canvas=new MapCanvas(mapsManager);
    _locationController=new MapLocationController(_canvas);
    _locationDisplay=new MapLocationPanelController();
    _locationController.addListener(_locationDisplay);
    _layers=new JLayeredPane();
    _layers.add(_canvas,Integer.valueOf(0),0);
    _canvas.setLocation(0,0);
    JPanel locationPanel=_locationDisplay.getPanel();
    _layers.add(locationPanel,Integer.valueOf(1),0);
    _navigation=new NavigationManager(_canvas);
    _navigation.setNavigationListener(this);
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

  public void mapChangeRequest(String key)
  {
    setMap(key);
  }

  /**
   * Set the map to display.
   * @param key
   */
  public void setMap(String key)
  {
    _canvas.setMap(key);
    Map map=_canvas.getMap();
    _locationController.setMap(map);
    _navigation.setMap(map);
    // Set map size
    Dimension size=_canvas.getPreferredSize();
    _canvas.setSize(size);
    _layers.setPreferredSize(size);
    int height=size.height;
    // Place location display (lower left)
    JPanel locationPanel=_locationDisplay.getPanel();
    locationPanel.setSize(100,40);
    locationPanel.setLocation(0,height-locationPanel.getHeight());
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    if (_navigation!=null)
    {
      _navigation.dispose();
      _navigation=null;
    }
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
    _canvas=null;
  }
}
