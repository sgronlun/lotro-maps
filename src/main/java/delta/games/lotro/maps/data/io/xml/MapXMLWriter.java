package delta.games.lotro.maps.data.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.GeoreferencedBasemap;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapLink;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.MarkersManager;

/**
 * Writes a map bundle to an XML file.
 * @author DAM
 */
public class MapXMLWriter
{
  /**
   * Write map files.
   * @param bundle Map bundle.
   * @param encoding Encoding.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeMapFiles(MapBundle bundle, String encoding)
  {
    File rootDir=bundle.getRootDir();
    // Map description
    File mapFile=new File(rootDir,"map.xml");
    boolean mapOk=writeMapFile(mapFile,bundle.getMap(),encoding);
    // Markers
    File markersFile=new File(rootDir,"markers.xml");
    boolean markersOk=writeMarkersFile(markersFile,bundle.getData(),encoding);
    // Links
    File linksFile=new File(rootDir,"links.xml");
    boolean linksOk=writeLinksFile(linksFile,bundle.getLinks(),encoding);
    return mapOk & markersOk & linksOk;
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
    attrs.addAttribute("","",MapXMLConstants.LABEL_ATTR,XmlWriter.CDATA,name);
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
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeLinksFile(File outFile, final List<MapLink> links, String encoding)
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
    boolean ret=helper.write(outFile,encoding,writer);
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
      GeoPoint hotPoint=link.getHotPoint();
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
    writeGeoPointAttrs(hd,point,attrs);
    hd.startElement("","",MapXMLConstants.POINT_TAG,attrs);
    hd.endElement("","",MapXMLConstants.POINT_TAG);
  }

  /**
   * Write the attributes of a point to the given XML stream.
   * @param hd XML output stream.
   * @param point Point to write.
   * @param attrs Attributes to use.
   * @throws Exception
   */
  private static void writeGeoPointAttrs(TransformerHandler hd, GeoPoint point, AttributesImpl attrs) throws Exception
  {
    // Longitude
    float longitude=point.getLongitude();
    attrs.addAttribute("","",MapXMLConstants.LONGITUDE_ATTR,XmlWriter.CDATA,String.valueOf(longitude));
    // Latitude
    float latitude=point.getLatitude();
    attrs.addAttribute("","",MapXMLConstants.LATITUDE_ATTR,XmlWriter.CDATA,String.valueOf(latitude));
  }

  /**
   * Write map markers to an XML file.
   * @param outFile Output file.
   * @param markers Markers to use.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeMarkersFile(File outFile, final MarkersManager markers, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeMarkers(hd,markers);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a markers structure to the given XML stream.
   * @param hd XML output stream.
   * @param markersManager Markers to write.
   * @throws Exception
   */
  private static void writeMarkers(TransformerHandler hd, MarkersManager markersManager) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",MapXMLConstants.MARKERS_TAG,attrs);
    List<Marker> markers=markersManager.getAllMarkers();
    for(Marker marker : markers)
    {
      AttributesImpl markerAttrs=new AttributesImpl();
      // Identifier
      int id=marker.getId();
      markerAttrs.addAttribute("","",MapXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Label
      String label=marker.getLabel();
      markerAttrs.addAttribute("","",MapXMLConstants.LABEL_ATTR,XmlWriter.CDATA,label);
      // Category
      int category=marker.getCategoryCode();
      markerAttrs.addAttribute("","",MapXMLConstants.CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(category));
      // DID
      int did=marker.getDid();
      if (did!=0)
      {
        markerAttrs.addAttribute("","",MapXMLConstants.DID_ATTR,XmlWriter.CDATA,String.valueOf(did));
      }
      // Position
      GeoPoint position=marker.getPosition();
      if (position!=null)
      {
        writeGeoPointAttrs(hd,position,markerAttrs);
      }
      hd.startElement("","",MapXMLConstants.MARKER_TAG,markerAttrs);
      hd.endElement("","",MapXMLConstants.MARKER_TAG);
    }
    hd.endElement("","",MapXMLConstants.MARKERS_TAG);
  }
}
