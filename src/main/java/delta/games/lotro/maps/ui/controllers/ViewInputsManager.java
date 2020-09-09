package delta.games.lotro.maps.ui.controllers;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager for the input controllers of a view.
 * @author DAM
 */
public class ViewInputsManager
{
  private Component _canvas;
  private MouseListener _listener;
  private List<InputController> _controllers;

  /**
   * Consructor.
   * @param canvas Canvas component.
   */
  public ViewInputsManager(Component canvas)
  {
    _canvas=canvas;
    _listener=new MouseInputListener();
    _controllers=new ArrayList<InputController>();
    _canvas.addMouseListener(_listener);
  }

  /**
   * Add an input controller.
   * @param inputController Input controller to add.
   */
  public void addInputController(InputController inputController)
  {
    _controllers.add(inputController);
  }

  private class MouseInputListener extends MouseAdapter
  {
    @Override
    public void mouseClicked(MouseEvent event)
    {
      for(InputController controller : _controllers)
      {
        if (controller instanceof MouseInputController)
        {
          MouseInputController mouseInputController=(MouseInputController)controller;
          mouseInputController.handleMouseClicked(event);
        }
      }
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    if (_listener!=null)
    {
      _canvas.removeMouseListener(_listener);
      _listener=null;
    }
    _canvas=null;
  }
}
