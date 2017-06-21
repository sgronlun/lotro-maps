package delta.games.lotro.maps.data;

/**
 * @author dm
 */
public class GeoPoint
{
  private float _longitude;
  private float _latitude;

  public GeoPoint(float longitude, float latitude)
  {
    _longitude=longitude;
    _latitude=latitude;
  }

  public float getLongitude()
  {
    return _longitude;
  }

  public float getLatitude()
  {
    return _latitude;
  }
}
