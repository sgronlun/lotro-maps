package delta.games.lotro.maps.ui.navigation;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.MapLink;
import delta.games.lotro.maps.ui.MapView;
import delta.games.lotro.maps.ui.controllers.MouseInputController;

/**
 * Input controller for maps navigation:
 * <ul>
 * <li>right click to go back,
 * <li>left click with no modifier on a link to change map.
 * </ul>
 * @author DAM
 */
public class NavigationController implements MouseInputController
{
  /**
   * Sensibility of link hot points.
   */
  private static final int SENSIBILITY=24;

  private MapView _view;
  private NavigationManager _navigationMgr;
  private List<MapLink> _links;

  /**
   * Constructor.
   * @param view Map view.
   * @param navigationMgr Navigation manager.
   */
  public NavigationController(MapView view, NavigationManager navigationMgr)
  {
    _view=view;
    _navigationMgr=navigationMgr;
    _links=new ArrayList<MapLink>();
  }

  /**
   * Set the links to use.
   * @param links Links to use.
   */
  public void setLinks(List<MapLink> links)
  {
    _links.clear();
    _links.addAll(links);
  }

  @Override
  public void handleMouseClicked(MouseEvent event)
  {
    int button=event.getButton();
    int modifiers=event.getModifiers();
    int x=event.getX();
    int y=event.getY();

    if (button==MouseEvent.BUTTON3)
    {
      handleRightClick(x,y);
    }
    else if ((button==MouseEvent.BUTTON1) && ((modifiers&MouseEvent.SHIFT_MASK)==0))
    {
      handleLeftClick(x,y);
    }
  }

  private void handleRightClick(int x, int y)
  {
    _navigationMgr.back();
  }

  private void handleLeftClick(int x, int y)
  {
    MapLink link=testForHotPoint(x,y);
    if (link!=null)
    {
      String targetMapKey=link.getTargetMapKey();
      _navigationMgr.requestMap(targetMapKey);
    }
  }

  private MapLink testForHotPoint(int x, int y)
  {
    GeoReference viewReference=_view.getViewReference();
    for(MapLink link : _links)
    {
      Dimension hotPoint=viewReference.geo2pixel(link.getPosition());
      if ((Math.abs(hotPoint.width-x) < SENSIBILITY) && (Math.abs(hotPoint.height-y) < SENSIBILITY))
      {
        // Found a hot point
        return link;
      }
    }
    return null;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _view=null;
    _navigationMgr=null;
    _links.clear();
  }
}
