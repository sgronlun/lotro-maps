package delta.games.lotro.maps.data.basemaps.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.maps.data.GeoBox;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemap;
import delta.games.lotro.maps.data.markers.io.xml.MarkersXMLWriter;

/**
 * Writes georeferenced basemaps to XML files.
 * @author DAM
 */
public class GeoreferencedBasemapXMLWriter
{
  /**
   * Write some georeferenced basemaps to an XML file.
   * @param outFile Output file.
   * @param maps Maps to use.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public static boolean writeBasemapsFile(File outFile, final List<GeoreferencedBasemap> maps, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeMaps(hd,maps);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  private static void writeMaps(TransformerHandler hd, List<GeoreferencedBasemap> maps) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",GeoreferencedBasemapsXMLConstants.MAPS_TAG,attrs);
    for(GeoreferencedBasemap map : maps)
    {
      writeMap(hd,map);
    }
    hd.endElement("","",GeoreferencedBasemapsXMLConstants.MAPS_TAG);
  }

  /**
   * Write a basemap definition to the given XML stream.
   * @param hd XML output stream.
   * @param map Map to use.
   * @throws SAXException If an error occurs.
   */
  private static void writeMap(TransformerHandler hd, GeoreferencedBasemap map) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Identifier
    int id=map.getIdentifier();
    attrs.addAttribute("","",GeoreferencedBasemapsXMLConstants.MAP_ID_ATTR,XmlWriter.CDATA,String.valueOf(id));
    // Name
    String name=map.getName();
    attrs.addAttribute("","",GeoreferencedBasemapsXMLConstants.MAP_NAME_ATTR,XmlWriter.CDATA,name);
    // Image ID
    int imageId=map.getImageId();
    attrs.addAttribute("","",GeoreferencedBasemapsXMLConstants.MAP_IMAGE_ID_ATTR,XmlWriter.CDATA,String.valueOf(imageId));
    hd.startElement("","",GeoreferencedBasemapsXMLConstants.MAP_TAG,attrs);

    // Geo reference
    GeoReference geoRef=map.getGeoReference();
    if (geoRef!=null)
    {
      AttributesImpl geoAttrs=new AttributesImpl();
      float factor=geoRef.getGeo2PixelFactor();
      geoAttrs.addAttribute("","",GeoreferencedBasemapsXMLConstants.GEO_FACTOR_ATTR,XmlWriter.CDATA,String.valueOf(factor));
      hd.startElement("","",GeoreferencedBasemapsXMLConstants.GEO_TAG,geoAttrs);
      GeoPoint start=geoRef.getStart();
      if (start!=null)
      {
        writeGeoPoint(hd,start);
      }
      hd.endElement("","",GeoreferencedBasemapsXMLConstants.GEO_TAG);
    }
    // Bounding box
    GeoBox box=map.getBoundingBox();
    if (box!=null)
    {
      writeGeoBox(hd,box);
    }
    hd.endElement("","",GeoreferencedBasemapsXMLConstants.MAP_TAG);
  }

  /**
   * Write a point to the given XML stream.
   * @param hd XML output stream.
   * @param point Point to write.
   * @throws SAXException
   */
  private static void writeGeoPoint(TransformerHandler hd, GeoPoint point) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    MarkersXMLWriter.writeGeoPointAttrs(point,attrs);
    hd.startElement("","",GeoreferencedBasemapsXMLConstants.POINT_TAG,attrs);
    hd.endElement("","",GeoreferencedBasemapsXMLConstants.POINT_TAG);
  }


  /**
   * Write a box to the given XML stream.
   * @param hd XML output stream.
   * @param box Box to write.
   * @throws SAXException
   */
  private static void writeGeoBox(TransformerHandler hd, GeoBox box) throws SAXException
  {
    // Min
    AttributesImpl minAttrs=new AttributesImpl();
    MarkersXMLWriter.writeGeoPointAttrs(box.getMin(),minAttrs);
    hd.startElement("","",GeoreferencedBasemapsXMLConstants.MIN_TAG,minAttrs);
    hd.endElement("","",GeoreferencedBasemapsXMLConstants.MIN_TAG);
    // Max
    AttributesImpl maxAttrs=new AttributesImpl();
    MarkersXMLWriter.writeGeoPointAttrs(box.getMax(),maxAttrs);
    hd.startElement("","",GeoreferencedBasemapsXMLConstants.MAX_TAG,maxAttrs);
    hd.endElement("","",GeoreferencedBasemapsXMLConstants.MAX_TAG);
  }
}
