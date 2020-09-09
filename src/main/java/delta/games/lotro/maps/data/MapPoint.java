package delta.games.lotro.maps.data;

/**
 * Map point (marker...).
 * @author DAM
 */
public interface MapPoint
{
  /**
   * Get the label for this point.
   * @return a label or <code>null</code> if none.
   */
  public String getLabel();

  /**
   * Get the position of this point.
   * @return a geographic position or <code>null</code> if not set.
   */
  public GeoPoint getPosition();
}
