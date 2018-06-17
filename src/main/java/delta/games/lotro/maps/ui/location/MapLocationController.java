package delta.games.lotro.maps.ui.location;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.Map;
import delta.games.lotro.maps.ui.MapCanvas;

/**
 * Controller to maintain the location of cursor on a map.
 * @author DAM
 */
public class MapLocationController
{
  private MapCanvas _canvas;
  private MapMouseListener _listener;
  private Map _currentMap;
  private List<MapLocationListener> _listeners;

  /**
   * Constructor.
   * @param canvas Managed canvas.
   */
  public MapLocationController(MapCanvas canvas)
  {
    _canvas=canvas;
    _listener=new MapMouseListener();
    _canvas.addMouseMotionListener(_listener);
    _listeners=new ArrayList<MapLocationListener>();
  }

  /**
   * Add a listener for the managed location.
   * @param listener Listener to add.
   */
  public void addListener(MapLocationListener listener)
  {
    _listeners.add(listener);
  }

  /**
   * Remove a listener for the managed location.
   * @param listener Listener to remove.
   */
  public void removeListener(MapLocationListener listener)
  {
    _listeners.remove(listener);
  }

  private class MapMouseListener extends MouseAdapter
  {
    @Override
    public void mouseMoved(MouseEvent event)
    {
      if (_currentMap!=null)
      {
        if (_listeners.size()>0)
        {
          GeoReference reference=_canvas.getViewReference();
          Dimension pixels=new Dimension(event.getX(),event.getY());
          GeoPoint location=reference.pixel2geo(pixels);
          for(MapLocationListener listener : _listeners)
          {
            listener.mapLocationUpdated(location);
          }
        }
      }
    }
  }

  /**
   * Set the current map.
   * @param map Map to set.
   */
  public void setMap(Map map)
  {
    _currentMap=map;
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
    _currentMap=null;
  }
}
