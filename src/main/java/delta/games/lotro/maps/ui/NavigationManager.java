package delta.games.lotro.maps.ui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import delta.games.lotro.maps.data.Map;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapLink;
import delta.games.lotro.maps.data.MapsManager;

/**
 * Manages navigation on a map canvas.
 * @author DAM
 */
public class NavigationManager
{
  private MapsManager _manager;
  private MapCanvas _canvas;

  private MapBundle _currentMap;
  private Stack<String> _navigationHistory;
  private List<Dimension> _hotPoints;
  private MouseListener _listener;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   * @param canvas Decorated canvas.
   */
  public NavigationManager(MapsManager mapsManager, MapCanvas canvas)
  {
    _manager=mapsManager;
    _canvas=canvas;
    _navigationHistory=new Stack<String>();
    _hotPoints=new ArrayList<Dimension>();
    _listener=new NavigationMouseListener();
    _canvas.addMouseListener(_listener);
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _manager=null;
    _navigationHistory.clear();
    _hotPoints.clear();
    if (_listener!=null)
    {
      _canvas.removeMouseListener(_listener);
      _listener=null;
    }
    _canvas=null;
  }

  private void updateHotPoints()
  {
    _hotPoints.clear();
    if (_currentMap!=null)
    {
      Map map=_currentMap.getMap();
      List<MapLink> links=map.getAllLinks();
      for(MapLink link : links)
      {
        Dimension hotPoint=map.getGeoReference().geo2pixel(link.getHotPoint());
        _hotPoints.add(hotPoint);
      }
    }
  }

  private void handleRightClick(int x, int y)
  {
    if (!_navigationHistory.isEmpty())
    {
      String old=_navigationHistory.pop();
      setMap(old);
    }
  }

  private void handleLeftClick(int x, int y)
  {
    MapLink link=testForHotPoint(x,y);
    if (link!=null)
    {
      _navigationHistory.push(_currentMap.getKey());
      String targetMapKey=link.getTargetMapKey();
      setMap(targetMapKey);
    }
  }

  private MapLink testForHotPoint(int x, int y)
  {
    updateHotPoints();
    int nbHotPoints=_hotPoints.size();
    for(int i=0;i<nbHotPoints;i++)
    {
      Dimension hotPoint=_hotPoints.get(i);
      if ((Math.abs(hotPoint.width-x) < 10) && (Math.abs(hotPoint.height-y) < 10))
      {
        // Found a hot point
        List<MapLink> links=_currentMap.getMap().getAllLinks();
        return links.get(i);
      }
    }
    return null;
  }

  /**
   * Set the current map.
   * @param key Key of the map to set as current.
   */
  public void setMap(String key)
  {
    MapBundle map=_manager.getMapByKey(key);
    if (map!=null)
    {
      _canvas.setMap(key);
      _currentMap=map;
      updateHotPoints();
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

    @Override
    public void mouseMoved(MouseEvent event)
    {
      // TODO
    }
  }
}
