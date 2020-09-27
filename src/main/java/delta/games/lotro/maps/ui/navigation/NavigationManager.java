package delta.games.lotro.maps.ui.navigation;

import java.util.Stack;

import delta.common.utils.ListenersManager;
import delta.games.lotro.maps.ui.BasemapPanelController;

/**
 * Manages navigation on a map canvas.
 * @author DAM
 */
public class NavigationManager
{
  // Map view
  private BasemapPanelController _view;
  // Listeners
  private ListenersManager<NavigationListener> _navigationListeners;
  // Maps history
  private Stack<MapViewDefinition> _navigationHistory;

  /**
   * Constructor.
   * @param view Associated view.
   */
  public NavigationManager(BasemapPanelController view)
  {
    _view=view;
    _navigationListeners=new ListenersManager<NavigationListener>();
    _navigationHistory=new Stack<MapViewDefinition>();
  }

  /**
   * Get the navigation listeners.
   * @return the navigation listeners.
   */
  public ListenersManager<NavigationListener> getNavigationListeners()
  {
    return _navigationListeners;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _view=null;
    _navigationHistory.clear();
    _navigationListeners=null;
  }

  /**
   * Return on previous map.
   */
  public void back()
  {
    if (_navigationHistory.size()==0)
    {
      return;
    }
    MapViewDefinition item=_navigationHistory.pop();
    requestMap(item);
  }

  /**
   * Request the display of a map.
   * @param mapKey Map identifier.
   */
  public void requestMap(int mapKey)
  {
    MapViewDefinition currentMapView=_view.getMapViewDefinition();
    if (currentMapView!=null)
    {
      _navigationHistory.push(currentMapView);
    }
    MapViewDefinition newMapView=new MapViewDefinition(mapKey,null,null);
    requestMap(newMapView);
  }

  /**
   * Request a map change.
   * @param viewDefinition Map view definition.
   */
  public void requestMap(MapViewDefinition viewDefinition)
  {
    for(NavigationListener navigationListener : _navigationListeners)
    {
      navigationListener.mapChangeRequest(viewDefinition);
    }
  }
}
