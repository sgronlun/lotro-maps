package delta.games.lotro.maps.ui.controllers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;

import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.MapPoint;
import delta.games.lotro.maps.data.MapPointNameComparator;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.links.MapLink;
import delta.games.lotro.maps.data.markers.comparators.MarkerDidComparator;
import delta.games.lotro.maps.ui.MapCanvas;
import delta.games.lotro.maps.ui.layers.Layer;
import delta.games.lotro.maps.ui.layers.VectorLayer;

/**
 * Input controller for markers selection: on left click with no modifier:
 * <ul>
 * <li>on a single marker: call a callback with that marker.
 * <li>on several markers: display a popup menu to choose a marker,
 * then call a callback on the selected marker, if any.
 * </ul>
 * @author DAM
 */
public class SelectionController implements MouseInputController
{
  private static final Logger LOGGER=Logger.getLogger(SelectionController.class);
  /**
   * Sensibility of link hot points.
   */
  private static final int SENSIBILITY=12;

  private MapCanvas _canvas;
  private SelectionListener _listener;

  /**
   * Constructor.
   * @param canvas Map canvas.
   * @param listener Selection listener.
   */
  public SelectionController(MapCanvas canvas, SelectionListener listener)
  {
    _canvas=canvas;
    _listener=listener;
  }

  @Override
  public void handleMouseClicked(MouseEvent event)
  {
    int button=event.getButton();
    int modifiers=event.getModifiers();
    int x=event.getX();
    int y=event.getY();

    if ((button==MouseEvent.BUTTON1) && ((modifiers&InputEvent.SHIFT_MASK)==0))
    {
      handleLeftClick(x,y);
    }
  }

  private void handleLeftClick(int x, int y)
  {
    List<MapPoint> points=getPointsAt(x,y);
    points=filterPoints(points);
    if (points.isEmpty())
    {
      return;
    }
    if (points.size()>1)
    {
      showMenu(points,x,y);
    }
    else
    {
      MapPoint point=points.get(0);
      handleMapPointSelection(point);
    }
  }

  private List<MapPoint> filterPoints(List<MapPoint> points)
  {
    List<MapLink> links=new ArrayList<MapLink>();
    List<Marker> markers=new ArrayList<Marker>();
    for(MapPoint point : points)
    {
      if (point instanceof MapLink)
      {
        links.add((MapLink)point);
      }
      if (point instanceof Marker)
      {
        markers.add((Marker)point);
      }
    }
    Collections.sort(links,new MapPointNameComparator());
    List<Marker> filteredMarkers=filterMarkers(markers);
    List<MapPoint> ret=new ArrayList<MapPoint>();
    ret.addAll(links);
    ret.addAll(filteredMarkers);
    return ret;
  }

  private List<Marker> filterMarkers(List<Marker> markers)
  {
    Collections.sort(markers,new MarkerDidComparator());
    List<Marker> ret=new ArrayList<Marker>();
    int did=0;
    for(Marker marker : markers)
    {
      if (marker.getDid()!=did)
      {
        ret.add(marker);
        did=marker.getDid();
      }
    }
    Collections.sort(ret,new MapPointNameComparator());
    return ret;
  }

  private List<MapPoint> getPointsAt(int x, int y)
  {
    List<MapPoint> ret=new ArrayList<MapPoint>();
    GeoReference viewReference=_canvas.getViewReference();
    for(Layer layer : _canvas.getLayers())
    {
      if (layer instanceof VectorLayer)
      {
        VectorLayer vectorLayer=(VectorLayer)layer;
        for(MapPoint point : vectorLayer.getVisiblePoints())
        {
          Dimension hotPoint=viewReference.geo2pixel(point.getPosition());
          if ((Math.abs(hotPoint.width-x) < SENSIBILITY) && (Math.abs(hotPoint.height-y) < SENSIBILITY))
          {
            ret.add(point);
          }
        }
      }
    }
    return ret;
  }

  private void showMenu(List<MapPoint> points, int x, int y)
  {
    JPopupMenu menu=buildMenu(points);
    menu.show(_canvas,x,y);
  }

  private JPopupMenu buildMenu(List<MapPoint> points)
  {
    JPopupMenu popup=new JPopupMenu();
    for(final MapPoint point : points)
    {
      String label=point.getLabel();
      label=label.replace("\n"," - ");
      JMenuItem item=new JMenuItem(label);
      ActionListener l=new ActionListener()
      {
        @Override
        public void actionPerformed(ActionEvent e)
        {
          handleMapPointSelection(point);
        }
      };
      item.addActionListener(l);
      popup.add(item);
    }
    return popup;
  }

  private void handleMapPointSelection(MapPoint point)
  {
    LOGGER.debug("Selected map point: "+point);
    if (_listener!=null)
    {
      _listener.handleSelection(point);
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _canvas=null;
  }
}
