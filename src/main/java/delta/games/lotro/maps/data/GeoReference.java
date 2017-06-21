package delta.games.lotro.maps.data;

import java.awt.Dimension;

/**
 * @author DAM
 */
public class GeoReference
{
  private GeoPoint _start; // Geo position of top-left corner
  private float _geo2pixel; // ScaleToMap
  private float _pixel2geo; // ScaleToIg = 1/ScaleToMap

  public GeoReference(GeoPoint start, float geo2pixel)
  {
    _start=start;
    _geo2pixel=geo2pixel;
    _pixel2geo=1/geo2pixel;
  }

  public GeoPoint getStart()
  {
    return _start;
  }

  public float getGeo2PixelFactor()
  {
    return _geo2pixel;
  }

  public GeoPoint pixel2geo(Dimension pixels)
  {
    float longitude = Math.round((((pixels.width * _pixel2geo) / 10) + _start.getLongitude())*10) / 10;
    float latitude = Math.round((((pixels.height * _pixel2geo) / 10) + _start.getLatitude())*10) / 10;
    return new GeoPoint(longitude, latitude);
  }

  public Dimension geo2pixel(GeoPoint position)
  {
    float deltaLongitude = position.getLongitude() - _start.getLongitude();
    int x=Math.round(deltaLongitude * _geo2pixel * 10);
    float deltaLatitude = _start.getLatitude() - position.getLatitude();
    int y=Math.round(deltaLatitude*_geo2pixel * 10);
    return new Dimension(x,y);
  }
}
