package delta.games.lotro.maps.data;

/**
 * Geographic box.
 * @author DAM
 */
public class GeoBox
{
  private GeoPoint _min;
  private GeoPoint _max;

  /**
   * Constructor.
   * @param p1 One point.
   * @param p2 Another point.
   */
  public GeoBox(GeoPoint p1, GeoPoint p2)
  {
    float minLat=Math.min(p1.getLatitude(),p2.getLatitude());
    float maxLat=Math.max(p1.getLatitude(),p2.getLatitude());
    float minLon=Math.min(p1.getLongitude(),p2.getLongitude());
    float maxLon=Math.max(p1.getLongitude(),p2.getLongitude());
    _min=new GeoPoint(minLon,minLat);
    _max=new GeoPoint(maxLon,maxLat);
  }

  /**
   * Indicates if the given point is in this box or not.
   * @param point A point.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isInBox(GeoPoint point)
  {
    float lat=point.getLatitude();
    if ((lat>=_min.getLatitude()) && (lat<=_max.getLatitude()))
    {
      float lon=point.getLongitude();
      if ((lon>=_min.getLongitude()) && (lon<=_max.getLongitude()))
      {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString()
  {
    return "min="+_min+",max="+_max;
  }
}
