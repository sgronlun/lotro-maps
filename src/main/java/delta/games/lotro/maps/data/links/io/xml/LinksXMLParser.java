package delta.games.lotro.maps.data.links.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.links.MapLink;
import delta.games.lotro.maps.data.markers.io.xml.MarkersXMLParser;

/**
 * Parser for links stored in XML.
 * @author DAM
 */
public class LinksXMLParser
{

  /**
   * Load links from a file.
   * @param linksFile Links file.
   * @return a list of map links.
   */
  public static List<MapLink> loadLinks(File linksFile)
  {
    List<MapLink> ret=new ArrayList<MapLink>();
    if (linksFile.exists())
    {
      Element root=DOMParsingTools.parse(linksFile);
      if (root!=null)
      {
        ret=LinksXMLParser.parseLinks(root);
      }
    }
    return ret;
  }

  /**
   * Parse some links.
   * @param root Root element.
   * @return A collection of links.
   */
  public static List<MapLink> parseLinks(Element root)
  {
    List<MapLink> links=new ArrayList<MapLink>();
    List<Element> linkTags=DOMParsingTools.getChildTagsByName(root,LinksXMLConstants.LINK_TAG,true);
    for(Element linkTag : linkTags)
    {
      MapLink link=parseLink(linkTag);
      links.add(link);
    }
    return links;
  }

  /**
   * Build a link from an XML tag.
   * @param linkTag Link tag.
   * @return A link.
   */
  private static MapLink parseLink(Element linkTag)
  {
    NamedNodeMap attrs=linkTag.getAttributes();
    // Parent ID
    int parentId=DOMParsingTools.getIntAttribute(attrs,LinksXMLConstants.LINK_PARENT_ID_ATTR,0);
    // Content layer ID
    int contentLayerId=DOMParsingTools.getIntAttribute(attrs,LinksXMLConstants.LINK_CONTENT_LAYER_ATTR,0);
    // Target
    int target=DOMParsingTools.getIntAttribute(attrs,LinksXMLConstants.LINK_TARGET_ATTR,0);
    // Label
    String label=DOMParsingTools.getStringAttribute(attrs,LinksXMLConstants.LINK_LABEL_ATTR,null);
    // Position
    GeoPoint hotPoint=MarkersXMLParser.parsePoint(linkTag);
    MapLink link=new MapLink(parentId,contentLayerId,target,hotPoint);
    link.setLabel(label);
    return link;
  }
}
