package delta.games.lotro.maps.data.markers.index.io.xml;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import delta.common.utils.NumericTools;
import delta.games.lotro.maps.data.markers.index.MarkersIndex;

/**
 * Parser for the markers indexes stored in XML.
 * @author DAM
 */
public class MarkersIndexXMLParser extends DefaultHandler
{
  private static final Logger LOGGER=Logger.getLogger(MarkersIndexXMLParser.class);

  private MarkersIndex _index;

  /**
   * Parse the XML file.
   * @param source Source file.
   * @param key Index key.
   * @return Parsed index or <code>null</code>.
   */
  public MarkersIndex parseXML(File source, int key)
  {
    _index=new MarkersIndex(key);
    try
    {
      // Use the default (non-validating) parser
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
      saxParser.parse(source, this);
      saxParser.reset();
      return _index;
    }
    catch(Exception e)
    {
      LOGGER.error("Error when loading markers index file " + source, e);
    }
    return null;
  }

  @Override
  public void startElement(String uri, String localName, String qualifiedName, Attributes attributes)
          throws SAXException
  {
    if (MarkersIndexXMLConstants.MARKER_TAG.equals(qualifiedName))
    {
      String markerIdStr=attributes.getValue(MarkersIndexXMLConstants.MARKER_ID_ATTR);
      int markerId=NumericTools.parseInt(markerIdStr,-1);
      _index.addMarker(markerId);
    }
  }
}
