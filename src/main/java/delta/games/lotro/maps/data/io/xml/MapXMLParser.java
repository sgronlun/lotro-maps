package delta.games.lotro.maps.data.io.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.GeoreferencedBasemap;
import delta.games.lotro.maps.data.markers.io.xml.MarkersXMLParser;

/**
 * Parser for a map stored in XML.
 * @author DAM
 */
public class MapXMLParser
{
  /**
   * Parse a map description.
   * @param root Root element.
   * @return A map.
   */
  public static GeoreferencedBasemap parseMap(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    int key=DOMParsingTools.getIntAttribute(attrs,MapXMLConstants.MAP_KEY_ATTR,0);
    GeoreferencedBasemap map=new GeoreferencedBasemap(key);

    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,MapXMLConstants.MAP_LABEL_ATTR,"");
    map.setName(name);
    // Geographic reference
    Element geoTag=DOMParsingTools.getChildTagByName(root,MapXMLConstants.GEO_TAG);
    if (geoTag!=null)
    {
      GeoReference geoReference=parseGeoReference(geoTag);
      map.setGeoReference(geoReference);
    }
    return map;
  }

  /**
   * Build a geographic reference from an XML tag.
   * @param geoTag Geographic reference tag.
   * @return A geographic reference.
   */
  private static GeoReference parseGeoReference(Element geoTag)
  {
    GeoReference ret=null;
    NamedNodeMap attrs=geoTag.getAttributes();

    // Factor
    float factor=DOMParsingTools.getFloatAttribute(attrs,MapXMLConstants.GEO_FACTOR_ATTR,0);
    // Start point
    Element startTag=DOMParsingTools.getChildTagByName(geoTag,MapXMLConstants.POINT_TAG);
    if (startTag!=null)
    {
      GeoPoint start=MarkersXMLParser.parsePoint(startTag);
      ret=new GeoReference(start,factor);
    }
    return ret;
  }
}
