package delta.games.lotro.maps.data.io.xml;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.maps.data.CategoriesManager;
import delta.games.lotro.maps.data.Category;

/**
 * Parser for the categories registry stored in XML.
 * @author DAM
 */
public class CategoriesXMLParser
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return Parsed categories or <code>null</code>.
   */
  public CategoriesManager parseXML(File source)
  {
    CategoriesManager registry=null;
    Element root=DOMParsingTools.parse(source);
    if (root!=null)
    {
      registry=parseCategories(root);
    }
    return registry;
  }

  private CategoriesManager parseCategories(Element root)
  {
    CategoriesManager categories=new CategoriesManager();
    List<Element> categoryTags=DOMParsingTools.getChildTagsByName(root,CategoryXMLConstants.CATEGORY_TAG,false);
    for(Element categoryTag : categoryTags)
    {
      Category category=parseCategory(categoryTag);
      categories.addCategory(category);
    }
    return categories;
  }

  /**
   * Build a category from an XML tag.
   * @param categoryTag Category tag.
   * @return A category.
   */
  public Category parseCategory(Element categoryTag)
  {
    Category ret=null;
    NamedNodeMap attrs=categoryTag.getAttributes();

    // Code
    int code=DOMParsingTools.getIntAttribute(attrs,CategoryXMLConstants.CATEGORY_CODE_ATTR,0);
    if (code!=0)
    {
      ret=new Category(code);
      // Icon
      String icon=DOMParsingTools.getStringAttribute(attrs,CategoryXMLConstants.CATEGORY_ICON_ATTR,null);
      ret.setIcon(icon);
    }
    Element labelsTag=DOMParsingTools.getChildTagByName(categoryTag,MapXMLConstants.LABELS_TAG);
    if (labelsTag!=null)
    {
      MapXMLParser.parseLabels(labelsTag,ret.getLabels());
    }
    return ret;
  }
}
