package delta.games.lotro.maps.ui.navigation;

import java.util.List;

import delta.common.utils.ListenersManager;
import delta.games.lotro.maps.data.links.MapLink;
import delta.games.lotro.maps.ui.MapCanvas;
import delta.games.lotro.maps.ui.controllers.ViewInputsManager;
import delta.games.lotro.maps.ui.layers.LinksLayer;

/**
 * Adds navigation support to a map.
 * @author DAM
 */
public class NavigationSupport
{
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
    init(canvas);
  }

  private void init(MapCanvas canvas)
  {
    // Navigation support
    _navigation=new NavigationManager();
    _linksLayer=new LinksLayer();
    canvas.addLayer(_linksLayer);
    _navigationController=new NavigationController(canvas,_navigation);
    ViewInputsManager inputsMgr=canvas.getInputsManager();
    inputsMgr.addInputController(_navigationController);
  }

  /**
   * Request a map change.
   * @param key Key of the map to show.
   */
  public void requestMap(int key)
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

  /**
   * Set the links to show.
   * @param links
   */
  public void setLinks(List<MapLink> links)
  {
    _navigationController.setLinks(links);
    _linksLayer.setLinks(links);
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
