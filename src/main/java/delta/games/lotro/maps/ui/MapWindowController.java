package delta.games.lotro.maps.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.combobox.ComboBoxItem;
import delta.common.ui.swing.windows.DefaultWindowController;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.ui.filter.MapFilterPanelController;
import delta.games.lotro.maps.ui.filter.MapMarkersFilter;

/**
 * Controller for a map window.
 * @author DAM
 */
public class MapWindowController extends DefaultWindowController implements NavigationListener
{
  /**
   * Identifier for this window.
   */
  public static final String IDENTIFIER="MAP";

  // Controllers
  private MapPanelController _mapPanel;
  private MapFilterPanelController _filter;
  private MapChooserController _mapChooser;
  private NavigationManager _navigation;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   */
  public MapWindowController(MapsManager mapsManager)
  {
    _mapPanel=new MapPanelController(mapsManager);
    // Navigation support
    _navigation=new NavigationManager(_mapPanel.getCanvas());
    _navigation.getNavigationListeners().addListener(this);
    // Markers filter
    MapMarkersFilter filter=new MapMarkersFilter();
    _filter=new MapFilterPanelController(mapsManager,filter,_mapPanel);
    _mapPanel.getMarkersLayer().setFilter(filter);
    // Map chooser
    _mapChooser=new MapChooserController(this,mapsManager);
  }

  @Override
  public void mapChangeRequest(String key)
  {
    MapsManager mapsManager=_mapPanel.getCanvas().getMapsManager();
    MapBundle map=mapsManager.getMapByKey(key);
    if (map!=null)
    {
      _navigation.setMap(map);
      _mapPanel.setMap(key);
      _mapChooser.selectMap(key);
    }
  }

  @Override
  public String getWindowIdentifier()
  {
    return IDENTIFIER;
  }

  @Override
  protected JFrame build()
  {
    JFrame frame=super.build();
    mapChangeRequest("angmar");
    frame.setTitle("Middle Earth maps");
    frame.setLocation(100,100);
    frame.pack();
    frame.setResizable(false);
    frame.getContentPane().setBackground(GuiFactory.getBackgroundColor());
    return frame;
  }

  @Override
  protected JComponent buildContents()
  {
    JPanel panel=GuiFactory.buildBackgroundPanel(new BorderLayout());
    // Top
    JPanel topPanel=buildTopPanel();
    panel.add(topPanel,BorderLayout.NORTH);
    // Center
    JLayeredPane layers=_mapPanel.getLayers();
    panel.add(layers,BorderLayout.CENTER);
    return panel;
  }

  private JPanel buildTopPanel()
  {
    JPanel topPanel=GuiFactory.buildPanel(new GridBagLayout());
    // Map chooser
    JComboBox<ComboBoxItem<MapBundle>> mapChooserCombo=_mapChooser.getCombo();
    JPanel chooserPanel=GuiFactory.buildPanel(new BorderLayout());
    chooserPanel.add(mapChooserCombo,BorderLayout.CENTER);
    TitledBorder mapChooserBorder=GuiFactory.buildTitledBorder("Map chooser");
    chooserPanel.setBorder(mapChooserBorder);
    GridBagConstraints c=new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.NONE,new Insets(0,5,0,0),0,0);
    topPanel.add(chooserPanel,c);
    // Markers filter
    JPanel filterPanel=_filter.getPanel();
    TitledBorder filterBorder=GuiFactory.buildTitledBorder("Filter");
    filterPanel.setBorder(filterBorder);
    c.gridx=2;
    topPanel.add(filterPanel,c);
    c.gridx=3;
    c.fill=GridBagConstraints.BOTH;
    c.weightx=1.0;
    JPanel emptyPanel=GuiFactory.buildPanel(new FlowLayout());
    topPanel.add(emptyPanel,c);
    return topPanel;
  }

  /**
   * Release all managed resources.
   */
  @Override
  public void dispose()
  {
    if (_filter!=null)
    {
      _filter.dispose();
      _filter=null;
    }
    if (_mapChooser==null)
    {
      _mapChooser.dispose();
      _mapChooser=null;
    }
    if (_mapPanel!=null)
    {
      _mapPanel.dispose();
      _mapPanel=null;
    }
    if (_navigation!=null)
    {
      _navigation.dispose();
      _navigation=null;
    }
    super.dispose();
  }
}
