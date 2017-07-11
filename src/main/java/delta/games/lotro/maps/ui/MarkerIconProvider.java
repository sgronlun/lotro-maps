package delta.games.lotro.maps.ui;

import java.awt.image.BufferedImage;

import delta.games.lotro.maps.data.Marker;

/**
 * Interface of a provider for marker icons.
 * @author DAM
 */
public interface MarkerIconProvider
{
  /**
   * Get the image for a marker.
   * @param marker Marker to use.
   * @return An image or <code>null</code> if none.
   */
  public BufferedImage getImage(Marker marker);
}
