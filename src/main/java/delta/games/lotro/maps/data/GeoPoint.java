package delta.games.lotro.maps.data;

/**
 * Geographic point: latitude and longitude.
 * Instances of this class are immutable.
 * @author DAM
 */
public class GeoPoint
{
  private float _longitude;
  private float _latitude;

  /**
   * Constructor.
   * @param longitude Longitude.
   * @param latitude Latitude.
   */
  public GeoPoint(float longitude, float latitude)
  {
    _longitude=longitude;
    _latitude=latitude;
  }

  /**
   * Get the longitude.
   * @return the longitude in degrees (west is negative, east is positive).
   */
  public float getLongitude()
  {
    return _longitude;
  }

  /**
   * Get the latitude.
   * @return the latitude in degrees (south is negative, north is positive).
   */
  public float getLatitude()
  {
    return _latitude;
  }

  /**
   * Get this location as a string.
   * @return a location string.
   */
  public String asString()
  {
    int lat10=(int)(_latitude*10);

    StringBuilder sb=new StringBuilder();
    // Latitude
    boolean south=lat10<0;
    if (lat10<0) lat10=-lat10;
    sb.append(lat10/10);
    sb.append('.');
    sb.append(lat10%10);
    sb.append(south?'S':'N');
    // Separator
    sb.append(' ');
    // Longitude
    int lon10=(int)(_longitude*10);
    boolean west=lon10<0;
    if (lon10<0) lon10=-lon10;
    sb.append(lon10/10);
    sb.append('.');
    sb.append(lon10%10);
    sb.append(west?'W':'E');
    return sb.toString();
  }

  @Override
  public String toString()
  {
    return "lat="+_latitude+",lon="+_longitude;
  }
}
