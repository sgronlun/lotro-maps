package delta.games.lotro.maps.data.io.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.GeoreferencedBasemap;
import delta.games.lotro.maps.data.MapLink;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.MarkersManager;

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
    String key=DOMParsingTools.getStringAttribute(attrs,MapXMLConstants.MAP_KEY_ATTR,null);
    GeoreferencedBasemap map=new GeoreferencedBasemap(key);

    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,MapXMLConstants.LABEL_ATTR,"");
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
   * Parse the markers of a map.
   * @param root Root element.
   * @return A markers manager.
   */
  public static MarkersManager parseMarkers(Element root)
  {
    // Markers
    MarkersManager markersManager=new MarkersManager();
    List<Element> markerTags=DOMParsingTools.getChildTagsByName(root,MapXMLConstants.MARKER_TAG,true);
    for(Element markerTag : markerTags)
    {
      Marker marker=parseMarker(markerTag);
      if (marker!=null)
      {
        markersManager.addMarker(marker);
      }
    }
    return markersManager;
  }

  /**
   * Parse the links of a map.
   * @param root Root element.
   * @return A collection of links.
   */
  public static List<MapLink> parseLinks(Element root)
  {
    List<MapLink> links=new ArrayList<MapLink>();
    List<Element> linkTags=DOMParsingTools.getChildTagsByName(root,MapXMLConstants.LINK_TAG,true);
    for(Element linkTag : linkTags)
    {
      MapLink link=parseLink(linkTag);
      if (link!=null)
      {
        links.add(link);
      }
    }
    return links;
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
      GeoPoint start=parsePoint(startTag);
      ret=new GeoReference(start,factor);
    }
    return ret;
  }

  /**
   * Build a link from an XML tag.
   * @param linkTag Link tag.
   * @return A link.
   */
  private static MapLink parseLink(Element linkTag)
  {
    NamedNodeMap attrs=linkTag.getAttributes();
    // Target
    String target=DOMParsingTools.getStringAttribute(attrs,MapXMLConstants.LINK_TARGET_ATTR,null);
    // Position
    Element positionTag=DOMParsingTools.getChildTagByName(linkTag,MapXMLConstants.POINT_TAG);
    GeoPoint hotPoint=null;
    if (positionTag!=null)
    {
      hotPoint=parsePoint(positionTag);
    }
    MapLink link=null;
    if ((target!=null) && (hotPoint!=null))
    {
      link=new MapLink(target,hotPoint);
    }
    return link;
  }

  /**
   * Build a marker from an XML tag.
   * @param markerTag Marker tag.
   * @return A marker.
   */
  private static Marker parseMarker(Element markerTag)
  {
    Marker marker=new Marker();
    NamedNodeMap attrs=markerTag.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,MapXMLConstants.ID_ATTR,0);
    marker.setId(id);
    // Label
    String label=DOMParsingTools.getStringAttribute(attrs,MapXMLConstants.LABEL_ATTR,"");
    marker.setLabel(label);
    // Category code
    int categoryCode=DOMParsingTools.getIntAttribute(attrs,MapXMLConstants.CATEGORY_ATTR,0);
    marker.setCategoryCode(categoryCode);
    // DID
    int did=DOMParsingTools.getIntAttribute(attrs,MapXMLConstants.DID_ATTR,0);
    marker.setDid(did);
    // Position
    GeoPoint position=parsePoint(markerTag);
    marker.setPosition(position);
    return marker;
  }

  /**
   * Build a point from an XML tag.
   * @param pointTag Point tag.
   * @return A point.
   */
  public static GeoPoint parsePoint(Element pointTag)
  {
    NamedNodeMap attrs=pointTag.getAttributes();
    // Latitude
    float latitude=DOMParsingTools.getFloatAttribute(attrs,MapXMLConstants.LATITUDE_ATTR,0);
    // Longitude
    float longitude=DOMParsingTools.getFloatAttribute(attrs,MapXMLConstants.LONGITUDE_ATTR,0);
    return new GeoPoint(longitude,latitude);
  }
}
