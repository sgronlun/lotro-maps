package delta.games.lotro.maps.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.LocaleNames;
import delta.games.lotro.maps.data.Map;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.MarkersManager;

/**
 * Map display.
 * @author DAM
 */
public class MapCanvas extends JPanel
{
  private static final Logger _logger=Logger.getLogger(MapCanvas.class);

  private MapsManager _mapsManager;
  private MapBundle _currentMap;
  private BufferedImage _background;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   */
  public MapCanvas(MapsManager mapsManager)
  {
    _mapsManager=mapsManager;
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
    _background=loadMap(mapImageFile);
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
      g.drawImage(_background,0,0,null);
    }
    paintMarkers(g);
  }

  private void paintMarkers(Graphics g)
  {
    if (_currentMap!=null)
    {
      MarkersManager markersManager=_currentMap.getData();
      List<Marker> markers=markersManager.getAllMarkers();
      for(Marker marker : markers)
      {
        paintMarker(marker,g);
      }
    }
  }

  private void paintMarker(Marker marker, Graphics g)
  {
    Map map=_currentMap.getMap();
    GeoReference geoReference=map.getGeoReference();
    Dimension pixelPosition=geoReference.geo2pixel(marker.getPosition());
    g.setColor(Color.RED);
    g.drawRect(pixelPosition.width,pixelPosition.height,4,4);
  }

  private BufferedImage loadMap(File inputFile)
  {
    BufferedImage image=null;
    try
    {
      image=ImageIO.read(inputFile);
    }
    catch(IOException ioe)
    {
      _logger.error("Cannot load image file: "+inputFile,ioe);
    }
    return image;
  }
}
