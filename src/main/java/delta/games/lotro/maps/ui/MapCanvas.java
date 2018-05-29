package delta.games.lotro.maps.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import delta.common.ui.ImageUtils;
import delta.common.ui.swing.icons.IconsManager;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.LocaleNames;
import delta.games.lotro.maps.data.Map;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapLink;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.comparators.MarkerNameComparator;
import delta.games.lotro.maps.ui.layers.MarkersLayer;

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
  private BufferedImage _background;
  private BufferedImage _gotoIcon;
  private GeoReference _viewReference;
  // Layers
  private MarkersLayer _markersLayer;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   */
  public MapCanvas(MapsManager mapsManager)
  {
    _mapsManager=mapsManager;
    _currentMap=null;
    _background=null;
    setToolTipText("");
    _gotoIcon=IconsManager.getImage("/resources/icons/goto.png");
    _markersLayer=new MarkersLayer(mapsManager,this);
  }

  /**
   * Get the current map bundle.
   * @return the current map.
   */
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

  /**
   * Get the current view reference.
   * @return the current view reference.
   */
  public GeoReference getViewReference()
  {
    return _viewReference;
  }

  /**
   * Get the markers layer.
   * @return the markers layer.
   */
  public MarkersLayer getMarkersLayer()
  {
    return _markersLayer;
  }

  /**
   * Set the map to display.
   * @param key Map identifier.
   */
  public void setMap(String key)
  {
    // Load map data
    _currentMap=_mapsManager.getMapByKey(key);
    // Load map image
    File mapDir=_mapsManager.getMapDir(key);
    String mapFilename="map_"+LocaleNames.DEFAULT_LOCALE+".jpg";
    File mapImageFile=new File(mapDir,mapFilename);
    _background=ImageUtils.loadImage(mapImageFile);
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
    //System.out.println("Zoom: "+factor);
    Dimension centerPixels=new Dimension(_background.getWidth()/2,_background.getHeight()/2);
    GeoPoint centerGeo=_viewReference.pixel2geo(centerPixels);
    //System.out.println("Center geo: "+centerGeo);
    Dimension endPixels=new Dimension(_background.getWidth(),_background.getHeight());
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
    Dimension centerPixels=new Dimension(_background.getWidth()/2,_background.getHeight()/2);
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
    if (_background!=null)
    {
      int width=_background.getWidth();
      int height=_background.getHeight();
      return new Dimension(width,height);
    }
    return super.getPreferredSize();
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    if (_background!=null)
    {
      //System.out.println("Repaint!");
      int dx1=0;int dy1=0;int dx2=_background.getWidth();int dy2=_background.getHeight();
      GeoReference reference=_currentMap.getMap().getGeoReference();
      Dimension startPixels=reference.geo2pixel(_viewReference.getStart());
      //System.out.println("Start pixels: "+startPixels);
      Dimension map=new Dimension(_background.getWidth(),_background.getHeight());
      GeoPoint endGeo=_viewReference.pixel2geo(map);
      //System.out.println("End geo: "+endGeo);
      Dimension endPixels=reference.geo2pixel(endGeo);
      //System.out.println("End pixels: "+endPixels);
      int sx1=startPixels.width;int sy1=startPixels.height;
      int sx2=endPixels.width;int sy2=endPixels.height;
      g.drawImage(_background,dx1,dy1,dx2,dy2,sx1,sy1,sx2,sy2,null);
    }
    paintLinkPoints(g);
    _markersLayer.paintLayer(g);
  }

  private void paintLinkPoints(Graphics g)
  {
    if (_currentMap!=null)
    {
      List<MapLink> links=_currentMap.getMap().getAllLinks();
      if (links.size()>0)
      {
        for(MapLink link : links)
        {
          paintLink(link,g);
        }
      }
    }
  }

  private void paintLink(MapLink link, Graphics g)
  {
    Dimension pixelPosition=_viewReference.geo2pixel(link.getHotPoint());

    int x=pixelPosition.width;
    int y=pixelPosition.height;
    if (_gotoIcon!=null)
    {
      int width=_gotoIcon.getWidth();
      int height=_gotoIcon.getHeight();
      g.drawImage(_gotoIcon,x-width/2,y-height/2,null);
    }
    else
    {
      g.setColor(Color.RED);
      g.fillRect(x-10,y-10,20,20);
    }
  }

  @Override
  public String getToolTipText(MouseEvent event)
  {
    List<Marker> markers=_markersLayer.findMarkersAtLocation(event.getX(),event.getY(),SENSIBILITY);
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
