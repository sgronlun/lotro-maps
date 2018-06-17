package delta.games.lotro.maps.ui.layers;

import java.awt.Graphics;
import java.util.List;

import delta.games.lotro.maps.data.Marker;

/**
 * Interface of layers.
 * @author DAM
 */
public interface Layer
{
  /**
   * Get the priority of this layer.
   * @return A priority value (0 is bottom, 100 is top).
   */
  int getPriority();

  /**
   * Get the list of markers for this layer.
   * @return A possibly empty list of markers, or <code>null</code>.
   */
  List<Marker> getVisibleMarkers();

  /**
   * Paint the layer.
   * @param g Graphics.
   */
  void paintLayer(Graphics g);
}
