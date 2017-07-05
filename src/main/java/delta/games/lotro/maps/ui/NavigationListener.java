package delta.games.lotro.maps.ui;

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
  void mapChangeRequest(String key);
}
