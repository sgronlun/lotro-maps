package delta.games.lotro.maps.data.markers.io.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.NumericTools;
import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.maps.data.GeoPoint;

/**
 * Parser for markers stored in XML.
 * @author DAM
 */
public class MarkersXMLParser
{
  /**
   * Build a point from an XML tag.
   * @param pointTag Point tag.
   * @return A point.
   */
  public static GeoPoint parsePoint(Element pointTag)
  {
    NamedNodeMap attrs=pointTag.getAttributes();
    // Latitude
    float latitude=DOMParsingTools.getFloatAttribute(attrs,MarkersXMLConstants.LATITUDE_ATTR,0);
    // Longitude
    float longitude=DOMParsingTools.getFloatAttribute(attrs,MarkersXMLConstants.LONGITUDE_ATTR,0);
    return new GeoPoint(longitude,latitude);
  }

  /**
   * Build a point from an string value.
   * @param lonLatStr Input valu: longitude/latitude.
   * @return A point.
   */
  public static GeoPoint parsePoint(String lonLatStr)
  {
    if (lonLatStr==null)
    {
      return null;
    }
    int index=lonLatStr.indexOf('/');
    // Longitude
    float longitude=NumericTools.parseFloat(lonLatStr.substring(0,index),0);
    // Latitude
    float latitude=NumericTools.parseFloat(lonLatStr.substring(index+1),0);
    return new GeoPoint(longitude,latitude);
  }
}
