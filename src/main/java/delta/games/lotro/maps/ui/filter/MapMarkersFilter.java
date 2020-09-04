package delta.games.lotro.maps.ui.filter;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.CompoundFilter;
import delta.common.utils.collections.filters.Filter;
import delta.common.utils.collections.filters.Operator;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.markers.filters.MarkerCategoryFilter;
import delta.games.lotro.maps.data.markers.filters.MarkerNameFilter;

/**
 * Filter for map markers using both name and categories.
 * @author DAM
 */
public class MapMarkersFilter implements Filter<Marker>
{
  private MarkerCategoryFilter _categoryFilter;
  private MarkerNameFilter _nameFilter;
  private Filter<Marker> _filter;

  /**
   * Constructor.
   */
  public MapMarkersFilter()
  {
    _categoryFilter=new MarkerCategoryFilter();
    _nameFilter=new MarkerNameFilter();
    List<Filter<Marker>> filters=new ArrayList<Filter<Marker>>();
    filters.add(_categoryFilter);
    filters.add(_nameFilter);
    _filter=new CompoundFilter<Marker>(Operator.AND,filters);
  }

  /**
   * Get the managed marker name filter.
   * @return a filter.
   */
  public MarkerNameFilter getNameFilter()
  {
    return _nameFilter;
  }

  /**
   * Get the managed marker category filter.
   * @return a filter.
   */
  public MarkerCategoryFilter getCategoryFilter()
  {
    return _categoryFilter;
  }

  public boolean accept(Marker item)
  {
    return _filter.accept(item);
  }
}
