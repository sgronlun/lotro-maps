package delta.games.lotro.maps.data.categories.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.categories.Category;

/**
 * Writes a categories registry to an XML file.
 * @author DAM
 */
public class CategoriesXMLWriter
{
  /**
   * Write a map bundle to an XML file.
   * @param outFile Output file.
   * @param categoriesManager Categories to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final CategoriesManager categoriesManager, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        write(hd,categoriesManager);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
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
      categoryAttrs.addAttribute("","",CategoryXMLConstants.CATEGORY_CODE_ATTR,XmlWriter.CDATA,String.valueOf(code));
      // Name
      String name=category.getName();
      categoryAttrs.addAttribute("","",CategoryXMLConstants.CATEGORY_NAME_ATTR,XmlWriter.CDATA,name);
      // Icon
      String icon=category.getIcon();
      categoryAttrs.addAttribute("","",CategoryXMLConstants.CATEGORY_ICON_ATTR,XmlWriter.CDATA,icon);
      hd.startElement("","",CategoryXMLConstants.CATEGORY_TAG,categoryAttrs);
      hd.endElement("","",CategoryXMLConstants.CATEGORY_TAG);
    }
    hd.endElement("","",CategoryXMLConstants.CATEGORIES_TAG);
  }
}
