package delta.games.lotro.maps.data.basemaps.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.maps.data.GeoBox;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemap;
import delta.games.lotro.maps.data.markers.io.xml.MarkersXMLParser;

/**
 * Parser for georeferenced basemaps stored in XML.
 * @author DAM
 */
public class GeoreferencedBasemapXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed data or <code>null</code>.
   */
  public static List<GeoreferencedBasemap> parseXML(File source)
  {
    List<GeoreferencedBasemap> ret=new ArrayList<GeoreferencedBasemap>();
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      List<Element> basemapTags=DOMParsingTools.getChildTagsByName(root,GeoreferencedBasemapsXMLConstants.MAP_TAG);
      for(Element basemapTag : basemapTags)
      {
        GeoreferencedBasemap basemap=parseMap(basemapTag);
        ret.add(basemap);
      }
    }
    return ret;
  }

  /**
   * Parse a basemap description.
   * @param root Root element.
   * @return A georeferenced basemap.
   */
  private static GeoreferencedBasemap parseMap(Element root)
  {
    NamedNodeMap attrs=root.getAttributes();
    // Identifier
    int id=DOMParsingTools.getIntAttribute(attrs,GeoreferencedBasemapsXMLConstants.MAP_ID_ATTR,0);
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,GeoreferencedBasemapsXMLConstants.MAP_NAME_ATTR,"");
    // Image ID
    int imageId=DOMParsingTools.getIntAttribute(attrs,GeoreferencedBasemapsXMLConstants.MAP_IMAGE_ID_ATTR,0);
    // Geographic reference
    GeoReference geoReference=null;
    Element geoTag=DOMParsingTools.getChildTagByName(root,GeoreferencedBasemapsXMLConstants.GEO_TAG);
    if (geoTag!=null)
    {
      geoReference=parseGeoReference(geoTag);
    }
    GeoreferencedBasemap map=new GeoreferencedBasemap(id,name,geoReference);
    // Bounding box
    Element minTag=DOMParsingTools.getChildTagByName(root,GeoreferencedBasemapsXMLConstants.MIN_TAG);
    Element maxTag=DOMParsingTools.getChildTagByName(root,GeoreferencedBasemapsXMLConstants.MAX_TAG);
    if ((minTag!=null) && (maxTag!=null))
    {
      GeoPoint min=MarkersXMLParser.parsePoint(minTag);
      GeoPoint max=MarkersXMLParser.parsePoint(maxTag);
      GeoBox box=new GeoBox(min,max);
      map.setBoundingBox(box);
    }
    map.setImageId(imageId);
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
    float factor=DOMParsingTools.getFloatAttribute(attrs,GeoreferencedBasemapsXMLConstants.GEO_FACTOR_ATTR,0);
    // Start point
    Element startTag=DOMParsingTools.getChildTagByName(geoTag,GeoreferencedBasemapsXMLConstants.POINT_TAG);
    if (startTag!=null)
    {
      GeoPoint start=MarkersXMLParser.parsePoint(startTag);
      ret=new GeoReference(start,factor);
    }
    return ret;
  }
}
