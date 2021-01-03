package delta.games.lotro.maps.ui.layers.basemap;

import java.awt.image.BufferedImage;

import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemap;

/**
 * Provider for basemap images.
 * @author DAM
 */
public interface BasemapImageProvider
{
  /**
   * Get the basemap image for the given basemap.
   * @param basemap Basemap to use.
   * @return a buffered image or <code>null</code> if not found.
   */
  public BufferedImage getImage(GeoreferencedBasemap basemap);
}
