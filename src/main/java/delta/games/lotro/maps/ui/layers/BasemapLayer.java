package delta.games.lotro.maps.ui.layers;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;

import delta.games.lotro.maps.data.GeoBox;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemap;
import delta.games.lotro.maps.ui.MapView;
import delta.games.lotro.maps.ui.layers.basemap.BasemapImageProvider;
import delta.games.lotro.maps.ui.layers.basemap.DefaultBasemapImageProvider;

/**
 * Base map layer.
 * @author DAM
 */
public class BasemapLayer implements RasterLayer
{
  private static final Logger LOGGER=Logger.getLogger(BasemapLayer.class);

  private GeoreferencedBasemap _currentMap;
  private BasemapImageProvider _imageProvider;
  private BufferedImage _background;

  /**
   * Constructor.
   */
  public BasemapLayer()
  {
    _background=null;
    _imageProvider=new DefaultBasemapImageProvider();
  }

  @Override
  public int getPriority()
  {
    return 0;
  }

  /**
   * Set the base map.
   * @param basemap Map to use.
   */
  public void setMap(GeoreferencedBasemap basemap)
  {
    LOGGER.debug("Set map: "+basemap);
    _currentMap=basemap;
    _background=_imageProvider.getImage(basemap);
  }

  /**
   * Set the image provider to use.
   * @param imageProvider Image provider to use.
   */
  public void setBasemapImageProvider(BasemapImageProvider imageProvider)
  {
    LOGGER.debug("Set image provider: "+imageProvider);
    _imageProvider=imageProvider;
  }

  /**
   * Get the basemap dimension.
   * @return A dimension or <code>null</code> if no map.
   */
  public Dimension getBasemapDimension()
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
  public void paintLayer(MapView view, Graphics g)
  {
    if (_background!=null)
    {
      GeoReference viewReference=view.getViewReference();
      //System.out.println("Repaint!");
      Dimension viewSize=view.getViewSize();
      int dx1=0;int dy1=0;int dx2=viewSize.width;int dy2=viewSize.height;
      GeoReference reference=_currentMap.getGeoReference();
      Dimension startPixels=reference.geo2pixel(viewReference.getStart());
      //System.out.println("Start pixels: "+startPixels);
      GeoPoint endGeo=viewReference.pixel2geo(viewSize);
      //System.out.println("End geo: "+endGeo);
      Dimension endPixels=reference.geo2pixel(endGeo);
      //System.out.println("End pixels: "+endPixels);
      int sx1=startPixels.width;int sy1=startPixels.height;
      int sx2=endPixels.width;int sy2=endPixels.height;
      Graphics2D g2d=(Graphics2D)g;
      //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g.drawImage(_background,dx1,dy1,dx2,dy2,sx1,sy1,sx2,sy2,null);
    }
  }
}
