package delta.games.lotro.maps.data;

import java.awt.Dimension;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Geo conversions tests.
 * @author DAM
 */
public class GeoReferenceTest extends TestCase
{
  private static GeoPoint _startPoint=new GeoPoint(-114.01f, -3.826f);
  private static GeoReference _geoReference=new GeoReference(_startPoint, 7.704f*10);

  /**
   * Test conversion from geo to pixels.
   */
  public void testConversionGeo2Pixel()
  {
    GeoPoint naga=new GeoPoint(-105.8f, -7.5f);
    Dimension nagaPixels = _geoReference.geo2pixel(naga);
    System.out.println(nagaPixels);
    Assert.assertEquals(632,nagaPixels.width);
    Assert.assertEquals(283,nagaPixels.height);
  }

  /**
   * Test conversion from pixels to geo.
   */
  public void testConversionPixel2Geo()
  {
    Dimension nagaPixels=new Dimension(632,283);
    GeoPoint naga=_geoReference.pixel2geo(nagaPixels);
    System.out.println(naga);
    Assert.assertTrue(Math.abs(-105.8f-naga.getLongitude())<0.01);
    Assert.assertTrue(Math.abs(-7.5f-naga.getLatitude())<0.01);
  }
}
