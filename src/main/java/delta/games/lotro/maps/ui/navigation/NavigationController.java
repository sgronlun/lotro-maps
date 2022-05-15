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
    if (button==MouseEvent.BUTTON3)
    {
      handleRightClick();
    }
  }

  private void handleRightClick()
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
