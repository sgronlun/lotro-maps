package delta.games.lotro.maps.data;

import java.awt.Dimension;

/**
 * Geographic reference for a map.
 * Instances of this class are immutable.
 * @author DAM
 */
public class GeoReference
{
  /**
   * Geographics position of top-left corner.
   */
  private GeoPoint _start; 
  /**
   * Scale factor from geographic to pixel.
   */
  private float _geo2pixel; // ScaleToMap
  /**
   * Scale factor from pixel to geographic.
   */
  private float _pixel2geo; // ScaleToIg = 1/ScaleToMap

  /**
   * Constructor.
   * @param start Geographic position of the top left corner.
   * @param geo2pixel Geographic to pixel factor.
   */
  public GeoReference(GeoPoint start, float geo2pixel)
  {
    _start=start;
    _geo2pixel=geo2pixel;
    _pixel2geo=1/_geo2pixel;
  }

  /**
   * Get the start point of the map.
   * @return a geographic point.
   */
  public GeoPoint getStart()
  {
    return _start;
  }

  /**
   * Get the geographic to pixel factor.
   * @return A factor.
   */
  public float getGeo2PixelFactor()
  {
    return _geo2pixel;
  }

  /**
   * Convert a pixel position to a geographic point.
   * @param pixels Pixel position.
   * @return A geographic point.
   */
  public GeoPoint pixel2geo(Dimension pixels)
  {
    float longitude=_start.getLongitude() + (pixels.width * _pixel2geo / 10);
    float latitude=_start.getLatitude() - (pixels.height * _pixel2geo / 10);
    return new GeoPoint(longitude, latitude);
  }

  /**
   * Convert a geographic point to a pixel position.
   * @param point Geographic point.
   * @return A pixel position.
   */
  public Dimension geo2pixel(GeoPoint point)
  {
    float deltaLongitude = point.getLongitude() - _start.getLongitude();
    int x=Math.round(deltaLongitude * _geo2pixel * 10);
    float deltaLatitude = _start.getLatitude() - point.getLatitude();
    int y=Math.round(deltaLatitude*_geo2pixel * 10);
    return new Dimension(x,y);
  }

  @Override
  public String toString()
  {
    return "Start="+_start+", scale="+_geo2pixel;
  }
}
