package delta.games.lotro.maps.ui.navigation;

import java.awt.event.MouseEvent;

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
  private NavigationManager _navigationMgr;

  /**
   * Constructor.
   * @param navigationMgr Navigation manager.
   */
  public NavigationController(NavigationManager navigationMgr)
  {
    _navigationMgr=navigationMgr;
  }

  @Override
  public void handleMouseClicked(MouseEvent event)
  {
    int button=event.getButton();
    int x=event.getX();
    int y=event.getY();

    if (button==MouseEvent.BUTTON3)
    {
      handleRightClick(x,y);
    }
  }

  private void handleRightClick(int x, int y)
  {
    _navigationMgr.back();
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _navigationMgr=null;
  }
}
