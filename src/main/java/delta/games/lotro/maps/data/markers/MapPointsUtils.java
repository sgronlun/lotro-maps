package delta.games.lotro.maps.data.markers;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.maps.data.MapPoint;

/**
 * Utility methods related to map points.
 * @author DAM
 */
public class MapPointsUtils
{
  /**
   * Get a list of all points that pass the given filter.
   * @param filter A filter or <code>null</code> to accept all.
   * @param points Points to use.
   * @return A possibly empty but never <code>null</code> list of points.
   */
  public static <T extends MapPoint,U extends T> List<T> getFilteredMarkers(Filter<U> filter, List<U> points)
  {
    List<T> ret=new ArrayList<T>();
    if (points!=null)
    {
      for(U point : points)
      {
        if ((filter==null) || (filter.accept(point)))
        {
          ret.add(point);
        }
      }
    }
    return ret;
  }
}
