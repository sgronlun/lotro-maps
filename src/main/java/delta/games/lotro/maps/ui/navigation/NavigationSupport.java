package delta.games.lotro.maps.ui.navigation;

import java.util.List;

import delta.common.utils.ListenersManager;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapLink;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.ui.MapCanvas;
import delta.games.lotro.maps.ui.controllers.ViewInputsManager;
import delta.games.lotro.maps.ui.layers.LinksLayer;

/**
 * Adds navigation support to a map.
 * @author DAM
 */
public class NavigationSupport implements NavigationListener
{
  // View
  private MapCanvas _canvas;
  // Navigation
  private NavigationManager _navigation;
  private NavigationController _navigationController;
  // Links
  private LinksLayer _linksLayer;

  /**
   * Constructor.
   * @param canvas Map view to use.
   */
  public NavigationSupport(MapCanvas canvas)
  {
    _canvas=canvas;
    init(canvas);
  }

  private void init(MapCanvas canvas)
  {
    // Navigation support
    _navigation=new NavigationManager();
    _linksLayer=new LinksLayer(canvas);
    canvas.addLayer(_linksLayer);
    _navigationController=new NavigationController(canvas,_navigation);
    ViewInputsManager inputsMgr=canvas.getInputsManager();
    inputsMgr.addInputController(_navigationController);
    _navigation.getNavigationListeners().addListener(this);
  }

  /**
   * Request a map change.
   * @param key Key of the map to show.
   */
  public void requestMap(String key)
  {
    _navigation.requestMap(key);
  }

  /**
   * Get the navigation listeners.
   * @return the navigation listeners.
   */
  public ListenersManager<NavigationListener> getNavigationListeners()
  {
    return _navigation.getNavigationListeners();
  }

  @Override
  public void mapChangeRequest(String key)
  {
    MapsManager mapsManager=_canvas.getMapsManager();
    MapBundle map=mapsManager.getMapByKey(key);
    if (map!=null)
    {
      List<MapLink> links=map.getLinks();
      _navigationController.setLinks(links);
      _linksLayer.setLinks(links);
    }
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
  }
}
