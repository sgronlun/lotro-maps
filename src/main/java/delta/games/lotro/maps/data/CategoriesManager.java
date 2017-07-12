package delta.games.lotro.maps.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Marker categories manager.
 * @author DAM
 */
public class CategoriesManager
{
  private HashMap<Integer,Category> _mapByCode;

  /**
   * Constructor.
   */
  public CategoriesManager()
  {
    _mapByCode=new HashMap<Integer,Category>();
  }

  /**
   * Get a category using its code.
   * @param code A code.
   * @return A category or <code>null</code> if not found.
   */
  public Category getByCode(int code)
  {
    return _mapByCode.get(Integer.valueOf(code));
  }

  /**
   * Get all the category codes.
   * @return A set of integer codes.
   */
  public Set<Integer> getCodes()
  {
    return new HashSet<Integer>(_mapByCode.keySet());
  }

  /**
   * Get all categories.
   * @return a list of categories, sorted by code.
   */
  public List<Category> getAllSortedByCode()
  {
    List<Category> ret=new ArrayList<Category>();
    List<Integer> codes=new ArrayList<Integer>(_mapByCode.keySet());
    Collections.sort(codes);
    for(Integer code : codes)
    {
      Category category=_mapByCode.get(code);
      ret.add(category);
    }
    return ret;
  }

  /**
   * Add a new category.
   * @param category Category to add.
   */
  public void addCategory(Category category)
  {
    Integer key=Integer.valueOf(category.getCode());
    _mapByCode.put(key,category);
  }

  /**
   * Remove a category.
   * @param code Code of the targeted category.
   */
  public void removeCategory(int code)
  {
    _mapByCode.remove(Integer.valueOf(code));
  }
}
