package delta.games.lotro.maps.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import delta.common.ui.ImageUtils;
import delta.games.lotro.maps.data.Category;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;

/**
 * Default mark icons provider.
 * @author DAM
 */
public class DefaultMarkerIconsProvider implements MarkerIconProvider
{
  private MapsManager _mapsManager;
  private HashMap<String,BufferedImage> _markerIcons;

  /**
   * Constructor.
   * @param mapsManager Parent maps manager.
   */
  public DefaultMarkerIconsProvider(MapsManager mapsManager)
  {
    _mapsManager=mapsManager;
    _markerIcons=new HashMap<String,BufferedImage>();
  }

  public BufferedImage getImage(Marker marker)
  {
    BufferedImage image=null;
    Category category = marker.getCategory();
    if (category != null) {
      String icon = category.getIcon();
      image=_markerIcons.get(icon);
      if (image==null) {
        File iconFile=_mapsManager.getIconFile(icon);
        image=ImageUtils.loadImage(iconFile);
        _markerIcons.put(icon,image);
      }
    }
    return image;
  }
}
