package delta.games.lotro.maps.data.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.GeoreferencedBasemap;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapLink;
import delta.games.lotro.maps.data.markers.io.xml.MarkersXMLWriter;

/**
 * Writes a map bundle to an XML file.
 * @author DAM
 */
public class MapXMLWriter
{
  /**
   * Write link file for a bundle.
   * @param bundle Map bundle.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeLinkFiles(MapBundle bundle)
  {
    File rootDir=bundle.getRootDir();
    // Links
    File linksFile=new File(rootDir,"links.xml");
    boolean linksOk=writeLinksFile(linksFile,bundle.getLinks());
    return linksOk;
  }

  /**
   * Write map markers to an XML file.
   * @param outFile Output file.
   * @param map Map to use.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeMapFile(File outFile, final GeoreferencedBasemap map, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeMap(hd,map);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write map markers from a map bundle to the given XML stream.
   * @param hd XML output stream.
   * @param map Map to use.
   * @throws Exception If an error occurs.
   */
  private static void writeMap(TransformerHandler hd, GeoreferencedBasemap map) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    // Key
    String key=map.getKey();
    attrs.addAttribute("","",MapXMLConstants.MAP_KEY_ATTR,XmlWriter.CDATA,key);
    // Name
    String name=map.getName();
    attrs.addAttribute("","",MapXMLConstants.MAP_LABEL_ATTR,XmlWriter.CDATA,name);
    hd.startElement("","",MapXMLConstants.MAP_TAG,attrs);

    // Geo reference
    GeoReference geoRef=map.getGeoReference();
    if (geoRef!=null)
    {
      AttributesImpl geoAttrs=new AttributesImpl();
      float factor=geoRef.getGeo2PixelFactor();
      geoAttrs.addAttribute("","",MapXMLConstants.GEO_FACTOR_ATTR,XmlWriter.CDATA,String.valueOf(factor));
      hd.startElement("","",MapXMLConstants.GEO_TAG,geoAttrs);
      GeoPoint start=geoRef.getStart();
      if (start!=null)
      {
        writeGeoPoint(hd,start);
      }
      hd.endElement("","",MapXMLConstants.GEO_TAG);
    }
    hd.endElement("","",MapXMLConstants.MAP_TAG);
  }

  /**
   * Write map markers to an XML file.
   * @param outFile Output file.
   * @param links Links to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeLinksFile(File outFile, final List<MapLink> links)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeLinks(hd,links);
      }
    };
    boolean ret=helper.write(outFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  private static void writeLinks(TransformerHandler hd, List<MapLink> links) throws Exception
  {
    hd.startElement("","",MapXMLConstants.LINKS_TAG,new AttributesImpl());
    // Links
    for(MapLink link : links)
    {
      AttributesImpl linkAttrs=new AttributesImpl();
      String target=link.getTargetMapKey();
      linkAttrs.addAttribute("","",MapXMLConstants.LINK_TARGET_ATTR,XmlWriter.CDATA,target);
      hd.startElement("","",MapXMLConstants.LINK_TAG,linkAttrs);
      GeoPoint hotPoint=link.getPosition();
      writeGeoPoint(hd,hotPoint);
      hd.endElement("","",MapXMLConstants.LINK_TAG);
    }
    hd.endElement("","",MapXMLConstants.LINKS_TAG);
  }

  /**
   * Write a point to the given XML stream.
   * @param hd XML output stream.
   * @param point Point to write.
   * @throws Exception
   */
  private static void writeGeoPoint(TransformerHandler hd, GeoPoint point) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    MarkersXMLWriter.writeGeoPointAttrs(hd,point,attrs);
    hd.startElement("","",MapXMLConstants.POINT_TAG,attrs);
    hd.endElement("","",MapXMLConstants.POINT_TAG);
  }
}
