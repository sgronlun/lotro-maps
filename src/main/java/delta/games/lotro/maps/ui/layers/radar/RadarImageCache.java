package delta.games.lotro.maps.ui.layers.radar;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Cache for radar images.
 * @author DAM
 */
public class RadarImageCache
{
  private Map<String,BufferedImage> _cache;
  private RadarImageProvider _provider;

  /**
   * Constructor.
   * @param provider Radar images provider.
   */
  public RadarImageCache(RadarImageProvider provider)
  {
    _provider=provider;
    _cache=new HashMap<String,BufferedImage>();
  }

  /**
   * Get the radar image for the given landblock.
   * @param region Region.
   * @param blockX Landblock X.
   * @param blockY Landblock Y.
   * @return a buffered image or <code>null</code> if not found.
   */
  public BufferedImage getImage(int region, int blockX, int blockY)
  {
    String key=getKey(region,blockX,blockY);
    BufferedImage image=null;
    if (_cache.containsKey(key))
    {
      image=_cache.get(key);
    }
    else
    {
      image=_provider.getImage(region,blockX,blockY);
      // Image may be <code>null</code>
      _cache.put(key,image);
    }
    return image;
  }

  private String getKey(int region, int blockX, int blockY)
  {
    return region+"-"+blockX+"-"+blockY;
  }
}
