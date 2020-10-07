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
   * Get the minimum point of this box (most south-western point).
   * @return a point.
   */
  public GeoPoint getMin()
  {
    return _min;
  }

  /**
   * Get the maximum point of this box (most north-eastern point).
   * @return a point.
   */
  public GeoPoint getMax()
  {
    return _max;
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

  /**
   * Extend this box so that it includes the given box.
   * @param inputBox Box to include.
   */
  public void extend(GeoBox inputBox)
  {
    GeoPoint inputMin=inputBox.getMin();
    if ((inputMin.getLatitude()<_min.getLatitude()) || (inputMin.getLongitude()<_min.getLatitude()))
    {
      float minLat=Math.min(inputMin.getLatitude(),_min.getLatitude());
      float minLon=Math.min(inputMin.getLongitude(),_min.getLongitude());
      _min=new GeoPoint(minLon,minLat);
    }
    GeoPoint inputMax=inputBox.getMax();
    if ((inputMax.getLatitude()>_max.getLatitude()) || (inputMax.getLongitude()>_max.getLongitude()))
    {
      float maxLat=Math.max(inputMax.getLatitude(),_max.getLatitude());
      float maxLon=Math.max(inputMax.getLongitude(),_max.getLongitude());
      _max=new GeoPoint(maxLon,maxLat);
    }
  }

  @Override
  public String toString()
  {
    return "min="+_min+",max="+_max;
  }
}
