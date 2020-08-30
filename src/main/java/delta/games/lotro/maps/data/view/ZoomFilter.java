package delta.games.lotro.maps.data.view;

/**
 * Zoom filter.
 * @author DAM
 */
public interface ZoomFilter
{
  /**
   * Indicates if the given zoom level is acceptable or not.
   * @param geo2pixel Geo to pixel factor to test.
   * @return <code>true</code> if accepted, <code>false</code> otherwise.
   */
  boolean acceptZoomLevel(float geo2pixel);
}
