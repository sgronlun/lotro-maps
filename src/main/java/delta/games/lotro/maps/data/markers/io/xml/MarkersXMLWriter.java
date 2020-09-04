package delta.games.lotro.maps.data.markers.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.Marker;

/**
 * Writes map markers to XML files.
 * @author DAM
 */
public class MarkersXMLWriter
{
  /**
   * Write the attributes of a point to the given XML stream.
   * @param hd XML output stream.
   * @param point Point to write.
   * @param attrs Attributes to use.
   * @throws Exception
   */
  public static void writeGeoPointAttrs(TransformerHandler hd, GeoPoint point, AttributesImpl attrs) throws Exception
  {
    // Longitude
    float longitude=point.getLongitude();
    attrs.addAttribute("","",MarkersXMLConstants.LONGITUDE_ATTR,XmlWriter.CDATA,String.valueOf(longitude));
    // Latitude
    float latitude=point.getLatitude();
    attrs.addAttribute("","",MarkersXMLConstants.LATITUDE_ATTR,XmlWriter.CDATA,String.valueOf(latitude));
  }

  /**
   * Write map markers to an XML file.
   * @param outFile Output file.
   * @param markers Markers to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeMarkersFile(File outFile, final List<Marker> markers)
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
    boolean ret=helper.write(outFile,EncodingNames.UTF_8,writer);
    return ret;
  }

  /**
   * Write a markers structure to the given XML stream.
   * @param hd XML output stream.
   * @param markers Markers to write.
   * @throws Exception
   */
  private static void writeMarkers(TransformerHandler hd, List<Marker> markers) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",MarkersXMLConstants.MARKERS_TAG,attrs);
    for(Marker marker : markers)
    {
      AttributesImpl markerAttrs=new AttributesImpl();
      // Identifier
      int id=marker.getId();
      markerAttrs.addAttribute("","",MarkersXMLConstants.ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
      // Label
      String label=marker.getLabel();
      markerAttrs.addAttribute("","",MarkersXMLConstants.LABEL_ATTR,XmlWriter.CDATA,label);
      // Category
      int category=marker.getCategoryCode();
      markerAttrs.addAttribute("","",MarkersXMLConstants.CATEGORY_ATTR,XmlWriter.CDATA,String.valueOf(category));
      // DID
      int did=marker.getDid();
      if (did!=0)
      {
        markerAttrs.addAttribute("","",MarkersXMLConstants.DID_ATTR,XmlWriter.CDATA,String.valueOf(did));
      }
      // Position
      GeoPoint position=marker.getPosition();
      if (position!=null)
      {
        writeGeoPointAttrs(hd,position,markerAttrs);
      }
      hd.startElement("","",MarkersXMLConstants.MARKER_TAG,markerAttrs);
      hd.endElement("","",MarkersXMLConstants.MARKER_TAG);
    }
    hd.endElement("","",MarkersXMLConstants.MARKERS_TAG);
  }
}
