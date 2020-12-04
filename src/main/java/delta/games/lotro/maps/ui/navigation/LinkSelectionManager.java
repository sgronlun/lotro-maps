package delta.games.lotro.maps.ui.navigation;

import delta.games.lotro.maps.data.MapPoint;
import delta.games.lotro.maps.data.links.MapLink;
import delta.games.lotro.maps.ui.controllers.SelectionListener;
import delta.games.lotro.maps.ui.navigation.NavigationManager;

/**
 * Map point selection manager.
 * @author DAM
 */
public class LinkSelectionManager implements SelectionListener
{
  private NavigationManager _navigationManager;

  /**
   * Constructor.
   * @param navigationManager Navigation manager.
   */
  public LinkSelectionManager(NavigationManager navigationManager)
  {
    _navigationManager=navigationManager;
  }

  @Override
  public boolean handleSelection(MapPoint point)
  {
    if (point instanceof MapLink)
    {
      handleMapLink((MapLink)point);
      return true;
    }
    return false;
  }

  private void handleMapLink(MapLink link)
  {
    int targetMapKey=link.getTargetMapKey();
    _navigationManager.requestMap(targetMapKey);
  }
}
