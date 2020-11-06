package delta.games.lotro.maps.data;

import java.util.Comparator;

/**
 * Comparator for GeoPoints using their coordinates.
 * @author DAM
 */
public class GeoPointComparator implements Comparator<GeoPoint>
{
  @Override
  public int compare(GeoPoint o1, GeoPoint o2)
  {
    float lat1=o1.getLatitude();
    float lat2=o2.getLatitude();
    if (lat1<lat2) return 1;
    if (lat1>lat2) return -1;
    float lon1=o1.getLongitude();
    float lon2=o2.getLongitude();
    if (lon1<lon2) return 1;
    if (lon1>lon2) return -1;
    return 0;
  }
}
