package delta.games.lotro.maps.ui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import delta.common.utils.ListenersManager;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapLink;
import delta.games.lotro.maps.ui.layers.LinksLayer;

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
  // Listeners
  private ListenersManager<NavigationListener> _navigationListeners;

  private MapBundle _currentMap;
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
    _navigationListeners=new ListenersManager<NavigationListener>();
    _navigationHistory=new Stack<String>();
    _hotPoints=new ArrayList<Dimension>();
    _listener=new NavigationMouseListener();
    _canvas.addMouseListener(_listener);
    LinksLayer linksLayer=new LinksLayer(_canvas);
    _canvas.addLayer(linksLayer);
  }

  /**
   * Get the navigation listeners.
   * @return the navigation listeners.
   */
  public ListenersManager<NavigationListener> getNavigationListeners()
  {
    return _navigationListeners;
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
    _navigationListeners=null;
    _currentMap=null;
    _canvas=null;
  }

  private void updateHotPoints()
  {
    _hotPoints.clear();
    if (_currentMap!=null)
    {
      List<MapLink> links=_currentMap.getLinks();
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
    for(NavigationListener navigationListener : _navigationListeners)
    {
      navigationListener.mapChangeRequest(key);
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
        List<MapLink> links=_currentMap.getLinks();
        return links.get(i);
      }
    }
    return null;
  }

  /**
   * Set the current map.
   * @param map Map to set as current.
   */
  public void setMap(MapBundle map)
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
