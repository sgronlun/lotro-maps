package delta.games.lotro.maps.ui.navigation;

/**
 * Listener for navigation events.
 * @author DAM
 */
public interface NavigationListener
{
  /**
   * Called when the user requested to change the map.
   * @param key Key of the new map.
   */
  void mapChangeRequest(int key);
}
