package delta.games.lotro.maps.ui.selection;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.maps.data.MapPoint;
import delta.games.lotro.maps.ui.controllers.SelectionListener;

/**
 * Map point selection manager.
 * @author DAM
 */
public class SelectionManager implements SelectionListener
{
  private List<SelectionListener> _listeners;

  /**
   * Constructor.
   */
  public SelectionManager()
  {
    _listeners=new ArrayList<SelectionListener>();
  }

  /**
   * Add a selection listener.
   * @param selectionListener Listener to add.
   */
  public void addListener(SelectionListener selectionListener)
  {
    _listeners.add(selectionListener);
  }

  @Override
  public boolean handleSelection(MapPoint point)
  {
    for(SelectionListener listener : _listeners)
    {
      boolean result=listener.handleSelection(point);
      if (result)
      {
        return true;
      }
    }
    return false;
  }
}
