package delta.games.lotro.maps.data.markers.filters;

import delta.common.utils.collections.filters.Filter;
import delta.common.utils.text.MatchType;
import delta.common.utils.text.StringFilter;
import delta.games.lotro.maps.data.Marker;

/**
 * Filter markers using their name.
 * @author DAM
 */
public class MarkerNameFilter implements Filter<Marker>
{
  private StringFilter _filter;
  private String _pattern;

  /**
   * Constructor.
   */
  public MarkerNameFilter()
  {
    this("");
  }

  /**
   * Constructor.
   * @param pattern String filter for name.
   */
  public MarkerNameFilter(String pattern)
  {
    _filter=new StringFilter("",MatchType.CONTAINS,true);
    _pattern=pattern;
  }

  /**
   * Get the pattern to use to filter name.
   * @return A pattern string.
   */
  public String getPattern()
  {
    return _pattern;
  }

  /**
   * Set the string pattern.
   * @param pattern Pattern to set.
   */
  public void setPattern(String pattern)
  {
    if (pattern==null)
    {
      pattern="";
    }
    _pattern=pattern;
    _filter=new StringFilter(pattern,MatchType.CONTAINS,true);
  }

  public boolean accept(Marker item)
  {
    String name=item.getLabel();
    if (name!=null)
    {
      return _filter.accept(name);
    }
    return false;
  }
}
