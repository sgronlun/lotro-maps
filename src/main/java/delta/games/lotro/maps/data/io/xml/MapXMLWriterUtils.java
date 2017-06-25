package delta.games.lotro.maps.data.io.xml;

import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.games.lotro.maps.data.Labels;

/**
 * Utility methods to write maps XML files.
 * @author DAM
 */
public class MapXMLWriterUtils
{
  private static final String CDATA="CDATA";

  /**
   * Write a labels structure to the given XML stream.
   * @param hd XML output stream.
   * @param labels Labels to write.
   * @throws Exception
   */
  public static void write(TransformerHandler hd, Labels labels) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",MapXMLConstants.LABELS_TAG,attrs);
    List<String> locales=labels.getLocales();
    for(String locale : locales)
    {
      AttributesImpl labelAttrs=new AttributesImpl();
      // Locale
      labelAttrs.addAttribute("","",MapXMLConstants.LABEL_LOCALE_ATTR,CDATA,locale);
      // Value
      String value=labels.getLabel(locale);
      labelAttrs.addAttribute("","",MapXMLConstants.LABEL_VALUE_ATTR,CDATA,value);
      hd.startElement("","",MapXMLConstants.LABEL_TAG,labelAttrs);
      hd.endElement("","",MapXMLConstants.LABEL_TAG);
    }
    hd.endElement("","",MapXMLConstants.LABELS_TAG);
  }
}
