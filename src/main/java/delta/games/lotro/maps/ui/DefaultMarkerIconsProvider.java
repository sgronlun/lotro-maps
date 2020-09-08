package delta.games.lotro.maps.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import delta.common.ui.ImageUtils;
import delta.games.lotro.maps.data.CategoriesManager;
import delta.games.lotro.maps.data.Category;
import delta.games.lotro.maps.data.Marker;

/**
 * Default mark icons provider.
 * @author DAM
 */
public class DefaultMarkerIconsProvider implements MarkerIconProvider
{
  private CategoriesManager _categoriesManager;
  private HashMap<String,BufferedImage> _markerIcons;

  /**
   * Constructor.
   * @param categoriesManager Categories manager.
   */
  public DefaultMarkerIconsProvider(CategoriesManager categoriesManager)
  {
    _categoriesManager=categoriesManager;
    _markerIcons=new HashMap<String,BufferedImage>();
  }

  public BufferedImage getImage(Marker marker)
  {
    BufferedImage image=null;
    int categoryCode = marker.getCategoryCode();
    if (categoryCode!=0) {
      Category category=_categoriesManager.getByCode(categoryCode);
      String icon = category.getIcon();
      image=_markerIcons.get(icon);
      if (image==null) {
        File iconFile=_categoriesManager.getIconFile(category);
        if (iconFile.exists())
        {
          image=ImageUtils.loadImage(iconFile);
          _markerIcons.put(icon,image);
        }
      }
    }
    return image;
  }
}
