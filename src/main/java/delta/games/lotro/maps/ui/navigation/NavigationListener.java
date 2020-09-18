package delta.games.lotro.maps.ui.navigation;


/**
 * Listener for navigation events.
 * @author DAM
 */
public interface NavigationListener
{
  /**
   * Called when the user requested to change the map.
   * @param mapViewDefinition View definition.
   */
  void mapChangeRequest(MapViewDefinition mapViewDefinition);
}
