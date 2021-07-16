package delta.games.lotro.maps.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import delta.games.lotro.maps.data.GeoBox;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.MapPoint;
import delta.games.lotro.maps.data.view.ZoomFilter;
import delta.games.lotro.maps.ui.constraints.MapConstraint;
import delta.games.lotro.maps.ui.controllers.ViewInputsManager;
import delta.games.lotro.maps.ui.layers.Layer;
import delta.games.lotro.maps.ui.layers.LayerPriorityComparator;
import delta.games.lotro.maps.ui.layers.VectorLayer;

/**
 * Map view panel.
 * It manages:
 * <ul>
 * <li>a view reference (geographic point of top/left corner, and zoom factor)
 * <li>a zoom filter (optional),
 * <li>map position constraints (optional),
 * <li>an input controller,
 * <li>a collection of layers to paint.
 * </ul>
 * @author DAM
 */
public class MapCanvas extends JPanel implements MapView
{
  /**
   * Sensibility of tooltips (pixels).
   */
  private static final int SENSIBILITY=10;

  // View definition
  private GeoReference _viewReference;
  // Zoom filter
  private ZoomFilter _zoomFilter;
  // Map position constraints
  private MapConstraint _constraint;
  // Input controller
  private ViewInputsManager _inputsMgr;
  // Layers
  private List<Layer> _layers;

  /**
   * Constructor.
   */
  public MapCanvas()
  {
    setToolTipText("");
    // Inputs manager
    _inputsMgr=new ViewInputsManager(this);
    _layers=new ArrayList<Layer>();
  }

  /**
   * Get the inputs manager.
   * @return the inputs manager.
   */
  public ViewInputsManager getInputsManager()
  {
    return _inputsMgr;
  }

  /**
   * Set the map constraint.
   * @param constraint Constraint to set.
   */
  public void setMapConstraint(MapConstraint constraint)
  {
    _constraint=constraint;
  }

  /**
   * Get geographic bounds for this view.
   * @return some geographic bounds.
   */
  public GeoBox getGeoBounds()
  {
    GeoPoint topLeft=_viewReference.pixel2geo(new Dimension(0,0));
    GeoPoint bottomRight=_viewReference.pixel2geo(new Dimension(getWidth(),getHeight()));
    GeoBox box=new GeoBox(topLeft,bottomRight);
    return box;
  }

  @Override
  public Dimension getViewSize()
  {
    return getSize();
  }

  /**
   * Add a layer.
   * @param layer Layer to add.
   */
  public void addLayer(Layer layer)
  {
    if (!_layers.contains(layer))
    {
      _layers.add(layer);
      Collections.sort(_layers,new LayerPriorityComparator());
      boolean isVisible=isVisible();
      if (isVisible)
      {
        repaint();
      }
    }
  }

  /**
   * Remove a layer.
   * @param layer Layer to remove.
   */
  public void removeLayer(Layer layer)
  {
    boolean removed=_layers.remove(layer);
    if (removed)
    {
      boolean isVisible=isVisible();
      if (isVisible)
      {
        repaint();
      }
    }
  }

  /**
   * Get a list of all managed layers.
   * @return A list of layers.
   */
  public List<Layer> getLayers()
  {
    return _layers;
  }

  @Override
  public GeoReference getViewReference()
  {
    return _viewReference;
  }

  /**
   * Set the view georeference.
   * @param viewReference Georeference to set.
   */
  public void setViewReference(GeoReference viewReference)
  {
    _viewReference=viewReference;
  }

  /**
   * Set the zoom filter.
   * @param zoomFilter Filter to set.
   */
  public void setZoomFilter(ZoomFilter zoomFilter)
  {
    _zoomFilter=zoomFilter;
  }

  /**
   * Zoom.
   * @param factor Zoom factor (>1 to zoom in, <1 to zoom out).
   */
  public void zoom(float factor)
  {
    float newGeo2PixelFactor=_viewReference.getGeo2PixelFactor()*factor;
    if ((_zoomFilter!=null) && (!_zoomFilter.acceptZoomLevel(newGeo2PixelFactor)))
    {
      return;
    }
    int width=getWidth();
    int height=getHeight();
    //System.out.println("Zoom: "+factor);
    Dimension centerPixels=new Dimension(width/2,height/2);
    GeoPoint centerGeo=_viewReference.pixel2geo(centerPixels);
    //System.out.println("Center geo: "+centerGeo);
    Dimension endPixels=new Dimension(width,height);
    GeoPoint endGeo=_viewReference.pixel2geo(endPixels);
    //System.out.println("End geo: "+endGeo);
    float deltaLon=endGeo.getLongitude()-_viewReference.getStart().getLongitude();
    //System.out.println("Delta lon="+deltaLon);
    float newDeltaLon=deltaLon/factor;
    //System.out.println("New delta lon="+newDeltaLon);
    float deltaLat=_viewReference.getStart().getLatitude()-endGeo.getLatitude();
    //System.out.println("Delta lat="+deltaLat);
    float newDeltaLat=deltaLat/factor;
    //System.out.println("New delta lat="+newDeltaLat);
    float latCenter=centerGeo.getLatitude();
    float lonCenter=centerGeo.getLongitude();
    GeoPoint newStart=new GeoPoint(lonCenter-newDeltaLon/2,latCenter+newDeltaLat/2);
    if (_constraint!=null)
    {
      newStart=_constraint.checkNewStart(newStart,newGeo2PixelFactor);
    }
    //System.out.println("New start geo: "+newStart);
    _viewReference=new GeoReference(newStart,newGeo2PixelFactor);
    //System.out.println("New view reference: "+_viewReference);
    repaint();
  }

  /**
   * Pan to place the given pixels at the center of the view.
   * @param x Current horizontal position of desired center.
   * @param y Current vertical position of desired center.
   */
  public void pan(int x, int y)
  {
    int width=getWidth();
    int height=getHeight();
    Dimension centerPixels=new Dimension(width/2,height/2);
    GeoPoint centerGeo=_viewReference.pixel2geo(centerPixels);
    GeoPoint newCenter=_viewReference.pixel2geo(new Dimension(x,y));
    float deltaLat=newCenter.getLatitude()-centerGeo.getLatitude();
    float deltaLon=newCenter.getLongitude()-centerGeo.getLongitude();
    GeoPoint currentStart=_viewReference.getStart();
    float newStartLat=currentStart.getLatitude()+deltaLat;
    float newStartLon=currentStart.getLongitude()+deltaLon;
    GeoPoint newStart=new GeoPoint(newStartLon,newStartLat);
    float geo2pixel=_viewReference.getGeo2PixelFactor();
    if (_constraint!=null)
    {
      newStart=_constraint.checkNewStart(newStart,geo2pixel);
    }
    _viewReference=new GeoReference(newStart,geo2pixel);
    //System.out.println("New view reference: "+_viewReference);
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    for(Layer layer : _layers)
    {
      if (layer.isVisible())
      {
        layer.paintLayer(this,g);
      }
    }
  }

  private List<MapPoint> findPointsAtLocation(int x, int y, int sensibility)
  {
    List<MapPoint> ret=new ArrayList<MapPoint>();
    GeoReference viewReference=getViewReference();
    GeoPoint topLeft=viewReference.pixel2geo(new Dimension(x-sensibility,y-sensibility));
    GeoPoint bottomRight=viewReference.pixel2geo(new Dimension(x+sensibility,y+sensibility));
    GeoBox box=new GeoBox(topLeft,bottomRight);

    for(Layer layer : _layers)
    {
      if (!(layer instanceof VectorLayer))
      {
        continue;
      }
      VectorLayer vectorLayer=(VectorLayer)layer;
      List<? extends MapPoint> visiblePoints=vectorLayer.getVisiblePoints();
      if (visiblePoints!=null)
      {
        for(MapPoint visiblePoint : visiblePoints)
        {
          boolean ok=box.isInBox(visiblePoint.getPosition());
          if (ok)
          {
            ret.add(visiblePoint);
          }
        }
      }
    }
    return ret;
  }

  @Override
  public String getToolTipText(MouseEvent event)
  {
    List<MapPoint> points=findPointsAtLocation(event.getX(),event.getY(),SENSIBILITY);
    return TooltipHelper.buildTooltip(points);
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _viewReference=null;
    _zoomFilter=null;
    _constraint=null;
    if (_inputsMgr!=null)
    {
      _inputsMgr.dispose();
      _inputsMgr=null;
    }
    if (_layers!=null)
    {
      _layers.clear();
      _layers=null;
    }
  }
}
