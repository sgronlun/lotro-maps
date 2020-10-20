package delta.games.lotro.maps.data.markers.io.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.Marker;

/**
 * Parser for markers stored in XML.
 * @author DAM
 */
public class MarkersXMLParser
{
  /**
   * Parse the markers of a map.
   * @param root Root element.
   * @return A list of markers.
   */
  public static List<Marker> parseMarkers(Element root)
  {
    // Markers
    List<Marker> markers=new ArrayList<Marker>();
    List<Element> markerTags=DOMParsingTools.getChildTagsByName(root,MarkersXMLConstants.MARKER_TAG,true);
    for(Element markerTag : markerTags)
    {
      Marker marker=parseMarker(markerTag);
      if (marker!=null)
      {
        markers.add(marker);
      }
    }
    return markers;
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
    int id=DOMParsingTools.getIntAttribute(attrs,MarkersXMLConstants.ID_ATTR,0);
    marker.setId(id);
    // Label
    String label=DOMParsingTools.getStringAttribute(attrs,MarkersXMLConstants.LABEL_ATTR,"");
    marker.setLabel(label);
    // Category code
    int categoryCode=DOMParsingTools.getIntAttribute(attrs,MarkersXMLConstants.CATEGORY_ATTR,0);
    marker.setCategoryCode(categoryCode);
    // DID
    int did=DOMParsingTools.getIntAttribute(attrs,MarkersXMLConstants.DID_ATTR,0);
    marker.setDid(did);
    // Parent zone ID
    int parentZoneId=DOMParsingTools.getIntAttribute(attrs,MarkersXMLConstants.PARENT_ZONE_ID_ATTR,0);
    marker.setParentZoneId(parentZoneId);
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
    float latitude=DOMParsingTools.getFloatAttribute(attrs,MarkersXMLConstants.LATITUDE_ATTR,0);
    // Longitude
    float longitude=DOMParsingTools.getFloatAttribute(attrs,MarkersXMLConstants.LONGITUDE_ATTR,0);
    return new GeoPoint(longitude,latitude);
  }
}
