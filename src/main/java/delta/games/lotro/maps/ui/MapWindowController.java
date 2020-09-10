package delta.games.lotro.maps.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

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
import delta.games.lotro.maps.data.MapLink;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.ui.controllers.NavigationController;
import delta.games.lotro.maps.ui.controllers.ViewInputsManager;
import delta.games.lotro.maps.ui.filter.MapFilterPanelController;
import delta.games.lotro.maps.ui.filter.MapMarkersFilter;
import delta.games.lotro.maps.ui.layers.LinksLayer;
import delta.games.lotro.maps.ui.layers.MarkersLayer;
import delta.games.lotro.maps.ui.layers.SimpleMarkersProvider;

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

  // Data
  private SimpleMarkersProvider _markersProvider;
  // UI controllers
  private MapPanelController _mapPanel;
  private MapFilterPanelController _filter;
  private MapChooserController _mapChooser;
  // Map control
  private ViewInputsManager _inputsMgr;
  private NavigationManager _navigation;
  private NavigationController _navigationController;
  // Layers
  private LinksLayer _linksLayer;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   */
  public MapWindowController(MapsManager mapsManager)
  {
    _mapPanel=new MapPanelController(mapsManager);
    MapCanvas canvas=_mapPanel.getCanvas();
    // Inputs manager
    _inputsMgr=new ViewInputsManager(canvas);
    // Navigation support
    _navigation=new NavigationManager();
    _linksLayer=new LinksLayer(canvas);
    canvas.addLayer(_linksLayer);
    _navigationController=new NavigationController(canvas,_navigation);
    _inputsMgr.addInputController(_navigationController);
    addNavigationListener(this);
    // Markers filter
    MapMarkersFilter filter=new MapMarkersFilter();
    CategoriesManager categoriesManager=mapsManager.getCategories();
    _filter=new MapFilterPanelController(categoriesManager,filter,_mapPanel);
    // Markers layer
    MarkerIconProvider iconsProvider=new DefaultMarkerIconsProvider(categoriesManager);
    _markersProvider=new SimpleMarkersProvider();
    MarkersLayer markersLayer=new MarkersLayer(canvas,iconsProvider,_markersProvider);
    markersLayer.setFilter(filter);
    canvas.addLayer(markersLayer);
    // Map chooser
    _mapChooser=new MapChooserController(this,mapsManager);
  }

  /**
   * Get the managed map canvas.
   * @return the managed map canvas.
   */
  public MapCanvas getMapCanvas()
  {
    return _mapPanel.getCanvas();
  }

  /**
   * Add a navigation listener.
   * @param listener Listener to add.
   */
  public void addNavigationListener(NavigationListener listener)
  {
    _navigation.getNavigationListeners().addListener(listener);
  }

  /**
   * Request a map change.
   * @param key Key of the map to show.
   */
  public void requestMap(String key)
  {
    _navigation.requestMap(key);
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
      List<MapLink> links=map.getLinks();
      _navigationController.setLinks(links);
      _linksLayer.setLinks(links);
      pack();
    }
  }

  /**
   * Set the markers for the current map.
   * @param markers Markers to show.
   */
  public void setMarkers(List<Marker> markers)
  {
    _markersProvider.setMarkers(markers);
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
    MapsManager mapsManager=_mapPanel.getCanvas().getMapsManager();
    MapBundle mapBundle=mapsManager.getMaps().get(0);
    String key=mapBundle.getKey();
    mapChangeRequest(key);
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
    _markersProvider=null;
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
    if (_inputsMgr!=null)
    {
      _inputsMgr.dispose();
      _inputsMgr=null;
    }
    if (_navigation!=null)
    {
      _navigation.dispose();
      _navigation=null;
    }
    if (_navigationController!=null)
    {
      _navigationController.dispose();
      _navigationController=null;
    }
    super.dispose();
  }
}
