package delta.games.lotro.maps.data.io.xml;

import java.io.File;
import java.io.FileOutputStream;
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
import delta.games.lotro.maps.data.CategoriesManager;
import delta.games.lotro.maps.data.Category;
import delta.games.lotro.maps.data.Labels;

/**
 * Writes a categories registry to an XML file.
 * @author DAM
 */
public class CategoriesXMLWriter
{
  private static final Logger _logger=Logger.getLogger(CategoriesXMLWriter.class);

  private static final String CDATA="CDATA";

  /**
   * Write a map bundle to an XML file.
   * @param outFile Output file.
   * @param categoriesManager Categories to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, CategoriesManager categoriesManager, String encoding)
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
      write(hd,categoriesManager);
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
   * @param categoriesManager Categories to write.
   * @throws Exception
   */
  public void write(TransformerHandler hd, CategoriesManager categoriesManager) throws Exception
  {
    AttributesImpl attrs=new AttributesImpl();
    hd.startElement("","",CategoryXMLConstants.CATEGORIES_TAG,attrs);

    List<Category> categories=categoriesManager.getAllSortedByCode();
    for(Category category : categories)
    {
      AttributesImpl categoryAttrs=new AttributesImpl();
      // Code
      int code=category.getCode();
      categoryAttrs.addAttribute("","",CategoryXMLConstants.CATEGORY_CODE_ATTR,CDATA,String.valueOf(code));
      // Icon
      String icon=category.getIcon();
      categoryAttrs.addAttribute("","",CategoryXMLConstants.CATEGORY_ICON_ATTR,CDATA,icon);
      hd.startElement("","",CategoryXMLConstants.CATEGORY_TAG,categoryAttrs);
      // Labels
      Labels labels=category.getLabels();
      MapXMLWriterUtils.write(hd,labels);
      hd.endElement("","",CategoryXMLConstants.CATEGORY_TAG);
    }
    hd.endElement("","",CategoryXMLConstants.CATEGORIES_TAG);
  }
}
