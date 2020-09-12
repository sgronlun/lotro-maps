package delta.games.lotro.maps.ui.navigation;

import java.util.Stack;

import delta.common.utils.ListenersManager;

/**
 * Manages navigation on a map canvas.
 * @author DAM
 */
public class NavigationManager
{
  // Listeners
  private ListenersManager<NavigationListener> _navigationListeners;
  // Maps history
  private Stack<String> _navigationHistory;

  /**
   * Constructor.
   */
  public NavigationManager()
  {
    _navigationListeners=new ListenersManager<NavigationListener>();
    _navigationHistory=new Stack<String>();
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
    _navigationHistory.clear();
    _navigationListeners=null;
  }

  /**
   * Return on previous map.
   */
  public void back()
  {
    if (_navigationHistory.size()<=1)
    {
      return;
    }
    _navigationHistory.pop();
    String mapKey=_navigationHistory.pop();
    requestMap(mapKey);
  }

  /**
   * Request a map change.
   * @param key Key of the map to show.
   */
  public void requestMap(String key)
  {
    if (!_navigationHistory.isEmpty())
    {
      if (_navigationHistory.peek().equals(key))
      {
        return;
      }
    }
    _navigationHistory.push(key);
    for(NavigationListener navigationListener : _navigationListeners)
    {
      navigationListener.mapChangeRequest(key);
    }
  }
}
