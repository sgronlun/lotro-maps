package delta.games.lotro.maps.ui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import delta.games.lotro.maps.data.Map;
import delta.games.lotro.maps.data.MapLink;

/**
 * Manages navigation on a map canvas.
 * @author DAM
 */
public class NavigationManager
{
  /**
   * Sensibility of link hot points.
   */
  private static final int SENSIBILITY=24;

  private MapCanvas _canvas;
  private NavigationListener _navigationListener;

  private Map _currentMap;
  private Stack<String> _navigationHistory;
  private List<Dimension> _hotPoints;
  private MouseListener _listener;

  /**
   * Constructor.
   * @param canvas Decorated canvas.
   */
  public NavigationManager(MapCanvas canvas)
  {
    _canvas=canvas;
    _navigationListener=null;
    _navigationHistory=new Stack<String>();
    _hotPoints=new ArrayList<Dimension>();
    _listener=new NavigationMouseListener();
    _canvas.addMouseListener(_listener);
  }

  /**
   * Set the navigation listener.
   * @param navigationListener Listener to call on navigation events.
   */
  public void setNavigationListener(NavigationListener navigationListener)
  {
    _navigationListener=navigationListener;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _navigationHistory.clear();
    _hotPoints.clear();
    if (_listener!=null)
    {
      _canvas.removeMouseListener(_listener);
      _listener=null;
    }
    _navigationListener=null;
    _currentMap=null;
    _canvas=null;
  }

  private void updateHotPoints()
  {
    _hotPoints.clear();
    if (_currentMap!=null)
    {
      List<MapLink> links=_currentMap.getAllLinks();
      for(MapLink link : links)
      {
        Dimension hotPoint=_canvas.getViewReference().geo2pixel(link.getHotPoint());
        _hotPoints.add(hotPoint);
      }
    }
  }

  private void handleRightClick(int x, int y)
  {
    if (!_navigationHistory.isEmpty())
    {
      String old=_navigationHistory.pop();
      requestMap(old);
    }
  }

  private void handleLeftClick(int x, int y)
  {
    MapLink link=testForHotPoint(x,y);
    if (link!=null)
    {
      _navigationHistory.push(_currentMap.getKey());
      String targetMapKey=link.getTargetMapKey();
      requestMap(targetMapKey);
    }
  }

  private void requestMap(String key)
  {
    if (_navigationListener!=null)
    {
      _navigationListener.mapChangeRequest(key);
    }
  }

  private MapLink testForHotPoint(int x, int y)
  {
    updateHotPoints();
    int nbHotPoints=_hotPoints.size();
    for(int i=0;i<nbHotPoints;i++)
    {
      Dimension hotPoint=_hotPoints.get(i);
      if ((Math.abs(hotPoint.width-x) < SENSIBILITY) && (Math.abs(hotPoint.height-y) < SENSIBILITY))
      {
        // Found a hot point
        List<MapLink> links=_currentMap.getAllLinks();
        return links.get(i);
      }
    }
    return null;
  }

  /**
   * Set the current map.
   * @param map Map to set as current.
   */
  public void setMap(Map map)
  {
    if (map!=null)
    {
      _canvas.setMap(map.getKey());
      _currentMap=map;
    }
  }

  private class NavigationMouseListener extends MouseAdapter
  {
    @Override
    public void mouseClicked(MouseEvent event)
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
  }
}
