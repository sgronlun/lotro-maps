package delta.games.lotro.maps.ui.layers.radar;

import java.awt.image.BufferedImage;

/**
 * Provider for radar images.
 * @author DAM
 */
public interface RadarImageProvider
{
  /**
   * Get the radar image for the given landblock.
   * @param region Region.
   * @param blockX Landblock X.
   * @param blockY Landblock Y.
   * @return a buffered image or <code>null</code> if not found.
   */
  public BufferedImage getImage(int region, int blockX, int blockY);
}
