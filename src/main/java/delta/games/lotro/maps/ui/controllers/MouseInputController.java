package delta.games.lotro.maps.ui.controllers;

import java.awt.event.MouseEvent;

/**
 * Base interface for mouse input controllers.
 * @author DAM
 */
public interface MouseInputController extends InputController
{
  /**
   * Handle a mouse click.
   * @param event Event.
   */
  public void handleMouseClicked(MouseEvent event);
}
