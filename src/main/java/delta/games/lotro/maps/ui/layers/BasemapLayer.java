package delta.games.lotro.maps.ui.layers;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import delta.common.ui.ImageUtils;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.LocaleNames;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.ui.MapView;

/**
 * Base map layer.
 * @author DAM
 */
public class BasemapLayer
{
  private MapView _view;
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

  /**
   * Load a base map.
   * @param key Map key.
   */
  public void load(String key)
  {
    // Load map image
    MapsManager mapsManager=_view.getMapsManager();
    File mapDir=mapsManager.getMapDir(key);
    String mapFilename="map_"+LocaleNames.DEFAULT_LOCALE+".jpg";
    File mapImageFile=new File(mapDir,mapFilename);
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
   * Paint the links.
   * @param g Graphics.
   */
  public void paintLayer(Graphics g)
  {
    if (_background!=null)
    {
      MapBundle currentMap=_view.getCurrentMap();
      GeoReference viewReference=_view.getViewReference();
      //System.out.println("Repaint!");
      int dx1=0;int dy1=0;int dx2=_background.getWidth();int dy2=_background.getHeight();
      GeoReference reference=currentMap.getMap().getGeoReference();
      Dimension startPixels=reference.geo2pixel(viewReference.getStart());
      //System.out.println("Start pixels: "+startPixels);
      Dimension map=new Dimension(_background.getWidth(),_background.getHeight());
      GeoPoint endGeo=viewReference.pixel2geo(map);
      //System.out.println("End geo: "+endGeo);
      Dimension endPixels=reference.geo2pixel(endGeo);
      //System.out.println("End pixels: "+endPixels);
      int sx1=startPixels.width;int sy1=startPixels.height;
      int sx2=endPixels.width;int sy2=endPixels.height;
      g.drawImage(_background,dx1,dy1,dx2,dy2,sx1,sy1,sx2,sy2,null);
    }
  }
}
