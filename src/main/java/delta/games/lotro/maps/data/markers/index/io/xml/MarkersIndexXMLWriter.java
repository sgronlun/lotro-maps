package delta.games.lotro.maps.data.markers.index.io.xml;

import java.io.File;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.maps.data.markers.index.MarkersIndex;

/**
 * Writes a markers index to an XML file.
 * @author DAM
 */
public class MarkersIndexXMLWriter
{
  /**
   * Write a markers index to an XML file.
   * @param outFile Output file.
   * @param index index to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final MarkersIndex index, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,index);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a markers index to the given XML stream.
   * @param hd XML output stream.
   * @param index Index to write.
   * @throws Exception
   */
  public void write(TransformerHandler hd, MarkersIndex index) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    int key=index.getKey();
    attrs.addAttribute("","",MarkersIndexXMLConstants.INDEX_KEY_ATTR,XmlWriter.CDATA,String.valueOf(key));
    hd.startElement("","",MarkersIndexXMLConstants.INDEX_TAG,attrs);

    int[] markerIds=index.getMarkers().getValues();
    for(int markerId : markerIds)
    {
      AttributesImpl markerAttrs=new AttributesImpl();
      // Identifier
      markerAttrs.addAttribute("","",MarkersIndexXMLConstants.MARKER_ID_ATTR,XmlWriter.CDATA,String.valueOf(markerId));
      hd.startElement("","",MarkersIndexXMLConstants.MARKER_TAG,markerAttrs);
      hd.endElement("","",MarkersIndexXMLConstants.MARKER_TAG);
    }
    hd.endElement("","",MarkersIndexXMLConstants.INDEX_TAG);
  }
}
