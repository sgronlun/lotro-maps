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

  @Override
  public String toString()
  {
    return "lat="+_latitude+",lon="+_longitude;
  }
}
