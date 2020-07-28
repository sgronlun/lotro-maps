package delta.games.lotro.maps.ui.layers;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import delta.common.ui.ImageUtils;
import delta.games.lotro.maps.data.GeoBox;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.GeoreferencedBasemap;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.ui.MapView;

/**
 * Base map layer.
 * @author DAM
 */
public class BasemapLayer implements Layer
{
  private MapView _view;
  private GeoreferencedBasemap _currentMap;
  private BufferedImage _background;

  /**
   * Constructor.
   * @param view Map view.
   */
  public BasemapLayer(MapView view)
  {
    _view=view;
    _background=null;
  }

  @Override
  public int getPriority()
  {
    return 0;
  }

  @Override
  public List<Marker> getVisibleMarkers()
  {
    return null;
  }

  /**
   * Set the base map.
   * @param basemap Map to use.
   */
  public void setMap(GeoreferencedBasemap basemap)
  {
    _currentMap=basemap;
    File mapImageFile=_currentMap.getImageFile();
    _background=ImageUtils.loadImage(mapImageFile);
  }

  /**
   * Get the preferred size for map display.
   * @return A size or <code>null</code> if no map.
   */
  public Dimension getPreferredSize()
  {
    if (_background!=null)
    {
      int width=_background.getWidth();
      int height=_background.getHeight();
      return new Dimension(width,height);
    }
    return null;
  }

  /**
   * Get the geographic bounds of this basemap.
   * @return a geographic box.
   */
  public GeoBox getMapBounds()
  {
    GeoReference geoReference=_currentMap.getGeoReference();
    GeoPoint start=geoReference.getStart();
    int width=_background.getWidth();
    int height=_background.getHeight();
    GeoPoint end=geoReference.pixel2geo(new Dimension(width,height));
    GeoBox box=new GeoBox(start,end);
    return box;
  }

  /**
   * Paint the basemap.
   * @param g Graphics.
   */
  @Override
  public void paintLayer(Graphics g)
  {
    if (_background!=null)
    {
      GeoReference viewReference=_view.getViewReference();
      //System.out.println("Repaint!");
      int dx1=0;int dy1=0;int dx2=_background.getWidth();int dy2=_background.getHeight();
      GeoReference reference=_currentMap.getGeoReference();
      Dimension startPixels=reference.geo2pixel(viewReference.getStart());
      //System.out.println("Start pixels: "+startPixels);
      Dimension map=new Dimension(_background.getWidth(),_background.getHeight());
      GeoPoint endGeo=viewReference.pixel2geo(map);
      //System.out.println("End geo: "+endGeo);
      Dimension endPixels=reference.geo2pixel(endGeo);
      //System.out.println("End pixels: "+endPixels);
      int sx1=startPixels.width;int sy1=startPixels.height;
      int sx2=endPixels.width;int sy2=endPixels.height;
      Graphics2D g2d = (Graphics2D) g;
      //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g.drawImage(_background,dx1,dy1,dx2,dy2,sx1,sy1,sx2,sy2,null);
    }
  }
}
