package delta.games.lotro.maps.ui.layers;

import java.awt.Graphics;

import delta.games.lotro.maps.ui.MapView;

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
   * @param view Parent view.
   * @param g Graphics.
   */
  void paintLayer(MapView view, Graphics g);

  /**
   * Indicates if this layer is visible or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  boolean isVisible();

  /**
   * Set the visibility of this layer.
   * @param visible <code>true</code> to show, <code>false</code> to hide.
   */
  void setVisible(boolean visible);

  /**
   * Get the name of this layer.
   * @return A name.
   */
  String getName();
}
