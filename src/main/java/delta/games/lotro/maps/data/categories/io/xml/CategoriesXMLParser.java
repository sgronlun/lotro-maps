package delta.games.lotro.maps.data.categories.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.maps.data.categories.Category;

/**
 * Parser for the categories registry stored in XML.
 * @author DAM
 */
public class CategoriesXMLParser
{
  /**
   * Load categories from a file.
   * @param categoriesFile Input file.
   * @return the loaded categories.
   */
  public static List<Category> load(File categoriesFile)
  {
    List<Category> ret=new ArrayList<Category>();
    if (categoriesFile.exists())
    {
      Element root=DOMParsingTools.parse(categoriesFile);
      if (root!=null)
      {
        List<Element> categoryTags=DOMParsingTools.getChildTagsByName(root,CategoryXMLConstants.CATEGORY_TAG,false);
        for(Element categoryTag : categoryTags)
        {
          Category category=parseCategory(categoryTag);
          ret.add(category);
        }
      }
    }
    return ret;
  }

  /**
   * Build a category from an XML tag.
   * @param categoryTag Category tag.
   * @return A category.
   */
  private static Category parseCategory(Element categoryTag)
  {
    NamedNodeMap attrs=categoryTag.getAttributes();

    // Code
    int code=DOMParsingTools.getIntAttribute(attrs,CategoryXMLConstants.CATEGORY_CODE_ATTR,-1);
    if (code<0)
    {
      return null;
    }
    Category ret=new Category(code);
    // Name 
    String name=DOMParsingTools.getStringAttribute(attrs,CategoryXMLConstants.CATEGORY_NAME_ATTR,"");
    ret.setName(name);
    // Icon
    String icon=DOMParsingTools.getStringAttribute(attrs,CategoryXMLConstants.CATEGORY_ICON_ATTR,null);
    ret.setIcon(icon);
    return ret;
  }
}
