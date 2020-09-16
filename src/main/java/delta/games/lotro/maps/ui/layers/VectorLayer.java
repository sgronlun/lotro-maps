package delta.games.lotro.maps.ui.layers;

import java.util.List;

import delta.games.lotro.maps.data.MapPoint;

/**
 * Vector layer.
 * @author DAM
 */
public interface VectorLayer extends Layer
{
  /**
   * Get the list of visible points for this layer.
   * @return A possibly empty list of points, or <code>null</code>.
   */
  List<? extends MapPoint> getVisiblePoints();
}
