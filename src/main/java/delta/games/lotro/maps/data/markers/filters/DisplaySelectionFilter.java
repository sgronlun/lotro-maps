package delta.games.lotro.maps.data.markers.filters;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.displaySelection.DisplaySelection;

/**
 * Filter for markers, using a display selection.
 * @author DAM
 */
public class DisplaySelectionFilter implements Filter<Marker>
{
  private DisplaySelection _displaySelection;

  /**
   * Constructor.
   * @param displaySelection Display selection to use.
   */
  public DisplaySelectionFilter(DisplaySelection displaySelection)
  {
    _displaySelection=displaySelection;
  }

  @Override
  public boolean accept(Marker item)
  {
    int categoryCode=item.getCategoryCode();
    int did=item.getDid();
    return _displaySelection.isVisible(categoryCode,did);
  }
}
