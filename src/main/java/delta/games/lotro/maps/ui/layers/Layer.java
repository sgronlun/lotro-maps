package delta.games.lotro.maps.ui.layers;

import java.awt.Graphics;

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
   * Paint the layer.
   * @param g Graphics.
   */
  void paintLayer(Graphics g);
}
