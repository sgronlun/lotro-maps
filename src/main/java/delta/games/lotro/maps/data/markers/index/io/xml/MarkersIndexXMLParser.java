package delta.games.lotro.maps.data.markers.index.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.maps.data.markers.index.MarkersIndex;

/**
 * Parser for the markers indexes stored in XML.
 * @author DAM
 */
public class MarkersIndexXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed index or <code>null</code>.
   */
  public MarkersIndex parseXML(File source)
  {
    MarkersIndex index=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      index=parseIndex(root);
    }
    return index;
  }

  /**
   * Build a markers index from an XML tag.
   * @param rootTag Root tag.
   * @return A markers index.
   */
  private MarkersIndex parseIndex(Element rootTag)
  {
    NamedNodeMap attrs=rootTag.getAttributes();
    // Key
    int key=DOMParsingTools.getIntAttribute(attrs,MarkersIndexXMLConstants.INDEX_KEY_ATTR,0);
    MarkersIndex index=new MarkersIndex(key);
    // Values
    List<Element> markerTags=DOMParsingTools.getChildTagsByName(rootTag,MarkersIndexXMLConstants.MARKER_TAG);
    for(Element markerTag : markerTags)
    {
      int markerId=DOMParsingTools.getIntAttribute(markerTag.getAttributes(),MarkersIndexXMLConstants.MARKER_ID_ATTR,0);
      index.addMarker(markerId);
    }
    return index;
  }
}
