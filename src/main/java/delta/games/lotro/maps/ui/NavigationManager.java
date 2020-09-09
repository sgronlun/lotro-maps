package delta.games.lotro.maps.ui;

import java.util.Stack;

import delta.common.utils.ListenersManager;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.ui.layers.LinksLayer;

/**
 * Manages navigation on a map canvas.
 * @author DAM
 */
public class NavigationManager
{
  private MapCanvas _canvas;
  // Listeners
  private ListenersManager<NavigationListener> _navigationListeners;

  private MapBundle _currentMap;
  private Stack<String> _navigationHistory;

  /**
   * Constructor.
   * @param canvas Decorated canvas.
   */
  public NavigationManager(MapCanvas canvas)
  {
    _canvas=canvas;
    _navigationListeners=new ListenersManager<NavigationListener>();
    _navigationHistory=new Stack<String>();
    LinksLayer linksLayer=new LinksLayer(_canvas);
    _canvas.addLayer(linksLayer);
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
    _currentMap=null;
    _canvas=null;
  }

  /**
   * Return on previous map.
   */
  public void back()
  {
    if (!_navigationHistory.isEmpty())
    {
      String old=_navigationHistory.pop();
      requestMap(old);
    }
  }

  /**
   * Request a new map.
   * @param targetMapKey Map key.
   */
  public void forward(String targetMapKey)
  {
    _navigationHistory.push(_currentMap.getKey());
    requestMap(targetMapKey);
  }

  /**
   * Request a map change.
   * @param key Key of the map to show.
   */
  public void requestMap(String key)
  {
    for(NavigationListener navigationListener : _navigationListeners)
    {
      navigationListener.mapChangeRequest(key);
    }
  }

  /**
   * Set the current map.
   * @param map Map to set as current.
   */
  public void setMap(MapBundle map)
  {
    if (map!=null)
    {
      _currentMap=map;
    }
  }
}
