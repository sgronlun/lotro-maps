package delta.games.lotro.maps.data;

import java.awt.Dimension;

import junit.framework.TestCase;

/**
 * Geo conversions tests.
 * @author DAM
 */
public class GeoReferenceTest extends TestCase
{
  /**
   * Test conversion from geo to pixels.
   */
  public void testConversionGeo2Pixel()
  {
    GeoPoint start=new GeoPoint(-114.01f, -3.826f);
    GeoReference geoReference=new GeoReference(start, 7.704f);
    GeoPoint naga=new GeoPoint(-105.8f, -7.5f);
    Dimension nagaPixels = geoReference.geo2pixel(naga);
    System.out.println(nagaPixels);
  }
}
