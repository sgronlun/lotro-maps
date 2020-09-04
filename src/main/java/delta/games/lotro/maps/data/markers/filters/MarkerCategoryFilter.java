package delta.games.lotro.maps.data.markers.filters;

import java.util.HashSet;
import java.util.Set;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.maps.data.Marker;

/**
 * Filter for markers using categories.
 * @author DAM
 */
public class MarkerCategoryFilter implements Filter<Marker>
{
  private Set<Integer> _acceptedCategoryCodes;

  /**
   * Constructor.
   */
  public MarkerCategoryFilter()
  {
    _acceptedCategoryCodes=new HashSet<Integer>();
  }

  /**
   * Get the categories to accept.
   * @return a set of category codes.
   */
  public Set<Integer> getCategories()
  {
    return new HashSet<Integer>(_acceptedCategoryCodes);
  }

  /**
   * Set the categories to accept.
   * @param categories A set of category codes.
   */
  public void setCategories(Set<Integer> categories)
  {
    _acceptedCategoryCodes.clear();
    _acceptedCategoryCodes.addAll(categories);
  }

  public boolean accept(Marker item)
  {
    int code=item.getCategoryCode();
    return (_acceptedCategoryCodes.contains(Integer.valueOf(code)));
  }
}
