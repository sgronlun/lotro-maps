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
import delta.games.lotro.maps.data.Map;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.comparators.MarkerNameComparator;
import delta.games.lotro.maps.ui.layers.BasemapLayer;
import delta.games.lotro.maps.ui.layers.Layer;
import delta.games.lotro.maps.ui.layers.LayerPriorityComparator;

/**
 * Map display.
 * @author DAM
 */
public class MapCanvas extends JPanel implements MapView
{
  /**
   * Sensibility of tooltips (pixels).
   */
  private static final int SENSIBILITY=10;

  private MapsManager _mapsManager;
  private MapBundle _currentMap;
  private GeoReference _viewReference;
  // Layers
  private BasemapLayer _basemapLayer;
  private List<Layer> _layers;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   */
  public MapCanvas(MapsManager mapsManager)
  {
    _mapsManager=mapsManager;
    _currentMap=null;
    setToolTipText("");
    _layers=new ArrayList<Layer>();
    _basemapLayer=new BasemapLayer(this);
    addLayer(_basemapLayer);
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

  @Override
  public MapBundle getCurrentMap()
  {
    return _currentMap;
  }

  /**
   * Get the current map.
   * @return the current map.
   */
  public Map getMap()
  {
    return (_currentMap!=null)?_currentMap.getMap():null;
  }

  @Override
  public GeoReference getViewReference()
  {
    return _viewReference;
  }

  @Override
  public MapsManager getMapsManager()
  {
    return _mapsManager;
  }

  /**
   * Set the map to display.
   * @param key Map identifier.
   */
  public void setMap(String key)
  {
    // Load map data
    _currentMap=_mapsManager.getMapByKey(key);
    // Set base map
    _basemapLayer.load(key);
    // Set reference
    GeoReference reference=_currentMap.getMap().getGeoReference();
    _viewReference=new GeoReference(reference.getStart(),reference.getGeo2PixelFactor());
    repaint();
  }

  /**
   * Zoom.
   * @param factor Zoom factor (>1 to zoom in, <1 to zoom out).
   */
  public void zoom(float factor)
  {
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
    //System.out.println("New start geo: "+newStart);
    _viewReference=new GeoReference(newStart,_viewReference.getGeo2PixelFactor()*factor);
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
    _viewReference=new GeoReference(newStart,_viewReference.getGeo2PixelFactor());
    //System.out.println("New view reference: "+_viewReference);
    repaint();
  }

  @Override
  public Dimension getPreferredSize()
  {
    Dimension baseMapPreferredSize=_basemapLayer.getPreferredSize();
    if (baseMapPreferredSize!=null)
    {
      return baseMapPreferredSize;
    }
    return super.getPreferredSize();
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    for(Layer layer : _layers)
    {
      layer.paintLayer(g);
    }
  }

  private List<Marker> findMarkersAtLocation(int x, int y, int sensibility)
  {
    List<Marker> ret=new ArrayList<Marker>();
    GeoReference viewReference=getViewReference();
    GeoPoint topLeft=viewReference.pixel2geo(new Dimension(x-sensibility,y-sensibility));
    GeoPoint bottomRight=viewReference.pixel2geo(new Dimension(x+sensibility,y+sensibility));
    GeoBox box=new GeoBox(topLeft,bottomRight);
    
    for(Layer layer : _layers)
    {
      List<Marker> visibleMarkers=layer.getVisibleMarkers();
      if (visibleMarkers!=null)
      {
        for(Marker visibleMarker : visibleMarkers)
        {
          boolean ok=box.isInBox(visibleMarker.getPosition());
          if (ok)
          {
            ret.add(visibleMarker);
          }
        }
      }
    }
    return ret;
  }

  @Override
  public String getToolTipText(MouseEvent event)
  {
    List<Marker> markers=findMarkersAtLocation(event.getX(),event.getY(),SENSIBILITY);
    if (markers.size()>0)
    {
      Collections.sort(markers,new MarkerNameComparator());
      StringBuilder sb=new StringBuilder();
      sb.append("<html>");
      int count=0;
      for(Marker marker : markers)
      {
        if (count>0) sb.append("<br>");
        sb.append(marker.getLabel());
        count++;
      }
      sb.append("</html>");
      return sb.toString();
    }
    return null;
  }
}
