package delta.games.lotro.maps.data.markers;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.maps.data.Marker;

/**
 * Utility methods related to markers.
 * @author DAM
 */
public class MarkersUtils
{
  /**
   * Get a list of all markers that pass the given filter.
   * @param filter A filter or <code>null</code> to accept all.
   * @param markers Markers to use.
   * @return A possibly empty but never <code>null</code> list of markers.
   */
  public static List<Marker> getFilteredMarkers(Filter<Marker> filter, List<Marker> markers)
  {
    List<Marker> ret=new ArrayList<Marker>();
    if (markers!=null)
    {
      for(Marker marker : markers)
      {
        if ((filter==null) || (filter.accept(marker)))
        {
          ret.add(marker);
        }
      }
    }
    return ret;
  }
}
