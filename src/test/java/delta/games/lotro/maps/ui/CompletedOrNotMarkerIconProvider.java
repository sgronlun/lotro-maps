package delta.games.lotro.maps.ui;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import delta.common.ui.swing.icons.IconsManager;
import delta.games.lotro.maps.data.Marker;

/**
 * Default mark icons provider.
 * @author DAM
 */
public class CompletedOrNotMarkerIconProvider implements MarkerIconProvider
{
  private HashMap<Boolean,BufferedImage> _markerIcons;

  /**
   * Constructor.
   */
  public CompletedOrNotMarkerIconProvider()
  {
    _markerIcons=new HashMap<Boolean,BufferedImage>();
    BufferedImage completed=IconsManager.getImage("/resources/gui/icons/check32.png");
    _markerIcons.put(Boolean.TRUE,completed);
    BufferedImage notCompleted=IconsManager.getImage("/resources/gui/icons/delete32.png");
    _markerIcons.put(Boolean.FALSE,notCompleted);
  }

  public BufferedImage getImage(Marker marker)
  {
    boolean completed=isCompleted(marker);
    return _markerIcons.get(Boolean.valueOf(completed));
  }

  private boolean isCompleted(Marker marker)
  {
    int id=marker.getId();
    if (id%2==0) return true;
    return false;
  }
}
