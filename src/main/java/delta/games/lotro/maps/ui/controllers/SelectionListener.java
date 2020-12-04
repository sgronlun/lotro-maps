package delta.games.lotro.maps.ui.controllers;

import delta.games.lotro.maps.data.MapPoint;

/**
 * Listener for map point selection.
 * @author DAM
 */
public interface SelectionListener
{
  /**
   * Handle the selection of a map point.
   * @param point Map point to use.
   * @return <code>true</code> if point was handled, <code>false</code> otherwise.
   */
  boolean handleSelection(MapPoint point);
}
