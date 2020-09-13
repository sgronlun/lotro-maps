package delta.games.lotro.maps.ui.constraints;

import delta.games.lotro.maps.data.GeoPoint;

/**
 * @author dm
 */
public interface MapConstraint
{
  /**
   * Check if the given 'new start' point verifies the map constraints.
   * @param newStart New start point.
   * @param geo2Pixel New zoom factor.
   * @return the given 'new start' point or a fixed one.
   */
  public GeoPoint checkNewStart(GeoPoint newStart, float geo2Pixel);
}
