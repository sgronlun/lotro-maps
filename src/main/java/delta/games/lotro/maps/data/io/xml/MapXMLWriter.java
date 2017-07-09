package delta.games.lotro.maps.data.io.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.StreamTools;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.Labels;
import delta.games.lotro.maps.data.Map;
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
  private static final Logger _logger=Logger.getLogger(MapXMLWriter.class);

  private static final String CDATA="CDATA";

  /**
   * Write a map bundle to an XML file.
   * @param outFile Output file.
   * @param mapBundle Map bundle to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, MapBundle mapBundle, String encoding)
  {
    boolean ret;
    FileOutputStream fos=null;
    try
    {
      File parentFile=outFile.getParentFile();
      if (!parentFile.exists())
      {
        parentFile.mkdirs();
      }
      fos=new FileOutputStream(outFile);
      SAXTransformerFactory tf=(SAXTransformerFactory)TransformerFactory.newInstance();
      TransformerHandler hd=tf.newTransformerHandler();
      Transformer serializer=hd.getTransformer();
      serializer.setOutputProperty(OutputKeys.ENCODING,encoding);
      serializer.setOutputProperty(OutputKeys.INDENT,"yes");

      StreamResult streamResult=new StreamResult(fos);
      hd.setResult(streamResult);
      hd.startDocument();
      write(hd,mapBundle);
      hd.endDocument();
      ret=true;
    }
    catch (Exception exception)
    {
      _logger.error("",exception);
      ret=false;
    }
    finally
    {
      StreamTools.close(fos);
    }
    return ret;
  }

  /**
   * Write a map bundle to the given XML stream.
   * @param hd XML output stream.
   * @param mapBundle Map bundle to write.
   * @throws Exception
   */
  public void write(TransformerHandler hd, MapBundle mapBundle) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    Map map=mapBundle.getMap();
    // Key
    String key=map.getKey();
    attrs.addAttribute("","",MapXMLConstants.MAP_KEY_ATTR,CDATA,key);
    // Last update
    Date lastUpdate=map.getLastUpdate();
    if (lastUpdate!=null)
    {
      String dateStr=Long.toString(lastUpdate.getTime());
      attrs.addAttribute("","",MapXMLConstants.MAP_LAST_UPDATE_ATTR,CDATA,dateStr);
    }
    hd.startElement("","",MapXMLConstants.MAP_TAG,attrs);

    // Geo reference
    GeoReference geoRef=map.getGeoReference();
    if (geoRef!=null)
    {
      AttributesImpl geoAttrs=new AttributesImpl();
      float factor=geoRef.getGeo2PixelFactor();
      geoAttrs.addAttribute("","",MapXMLConstants.GEO_FACTOR_ATTR,CDATA,String.valueOf(factor));
      hd.startElement("","",MapXMLConstants.GEO_TAG,geoAttrs);
      GeoPoint start=geoRef.getStart();
      if (start!=null)
      {
        write(hd,start);
      }
      hd.endElement("","",MapXMLConstants.GEO_TAG);
    }
    // Labels
    Labels labels=map.getLabels();
    MapXMLWriterUtils.write(hd,labels);
    // Links
    List<MapLink> links=map.getAllLinks();
    for(MapLink link : links)
    {
      AttributesImpl linkAttrs=new AttributesImpl();
      String target=link.getTargetMapKey();
      linkAttrs.addAttribute("","",MapXMLConstants.LINK_TARGET_ATTR,CDATA,target);
      hd.startElement("","",MapXMLConstants.LINK_TAG,linkAttrs);
      GeoPoint hotPoint=link.getHotPoint();
      write(hd,hotPoint);
      hd.endElement("","",MapXMLConstants.LINK_TAG);
    }

    // Data
    MarkersManager markers=mapBundle.getData();
    write(hd,markers);
    hd.endElement("","",MapXMLConstants.MAP_TAG);
  }

  /**
   * Write a point to the given XML stream.
   * @param hd XML output stream.
   * @param point Point to write.
   * @throws Exception
   */
  private void write(TransformerHandler hd, GeoPoint point) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();

    // Longitude
    float longitude=point.getLongitude();
    attrs.addAttribute("","",MapXMLConstants.LONGITUDE_ATTR,CDATA,String.valueOf(longitude));
    // Latitude
    float latitude=point.getLatitude();
    attrs.addAttribute("","",MapXMLConstants.LATITUDE_ATTR,CDATA,String.valueOf(latitude));
    hd.startElement("","",MapXMLConstants.POINT_TAG,attrs);
    hd.endElement("","",MapXMLConstants.POINT_TAG);
  }

  /**
   * Write a markers structure to the given XML stream.
   * @param hd XML output stream.
   * @param markersManager Markers to write.
   * @throws Exception
   */
  private void write(TransformerHandler hd, MarkersManager markersManager) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",MapXMLConstants.MARKERS_TAG,attrs);
    List<Marker> markers=markersManager.getAllMarkers();
    for(Marker marker : markers)
    {
      AttributesImpl markerAttrs=new AttributesImpl();
      // Identifier
      int id=marker.getId();
      markerAttrs.addAttribute("","",MapXMLConstants.ID_ATTR,CDATA,String.valueOf(id));
      // Category
      int category=marker.getCategoryCode();
      markerAttrs.addAttribute("","",MapXMLConstants.CATEGORY_ATTR,CDATA,String.valueOf(category));
      // Comments
      String comment=marker.getComment();
      if (comment!=null)
      {
        markerAttrs.addAttribute("","",MapXMLConstants.COMMENT_ATTR,CDATA,comment);
      }
      hd.startElement("","",MapXMLConstants.MARKER_TAG,markerAttrs);
      // Position
      GeoPoint position=marker.getPosition();
      if (position!=null)
      {
        write(hd,position);
      }
      // Labels
      Labels labels=marker.getLabels();
      MapXMLWriterUtils.write(hd,labels);
      hd.endElement("","",MapXMLConstants.MARKER_TAG);
    }
    hd.endElement("","",MapXMLConstants.MARKERS_TAG);
  }
}
