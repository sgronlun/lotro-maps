package delta.games.lotro.maps.ui.layers.basemap;

import java.awt.image.BufferedImage;
import java.io.File;

import delta.common.ui.ImageUtils;
import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemap;

/**
 * Default basemap image provider.
 * @author DAM
 */
public class DefaultBasemapImageProvider implements BasemapImageProvider
{
  @Override
  public BufferedImage getImage(GeoreferencedBasemap basemap)
  {
    File mapImageFile=basemap.getImageFile();
    return ImageUtils.loadImage(mapImageFile);
  }
}
