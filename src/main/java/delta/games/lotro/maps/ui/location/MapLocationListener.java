package delta.games.lotro.maps.ui.location;

import delta.games.lotro.maps.data.GeoPoint;

/**
 * Listener for a map location.
 * @author DAM
 */
public interface MapLocationListener
{
  /**
   * Called when the mouse location changed on the managed map.
   * @param point New geographic point under the cursor.
   */
  void mapLocationUpdated(GeoPoint point);
}
