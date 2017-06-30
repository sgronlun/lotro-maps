package delta.games.lotro.maps.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.maps.data.Category;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.LocaleNames;
import delta.games.lotro.maps.data.Map;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapLink;
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
  private HashMap<String,BufferedImage> _markerIcons;
  private boolean _useLabels;
  private Filter<Marker> _filter;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   */
  public MapCanvas(MapsManager mapsManager)
  {
    _mapsManager=mapsManager;
    _markerIcons=new HashMap<String,BufferedImage>();
    _useLabels=false;
    _filter=null;
  }

  /**
   * Get the current map.
   * @return the current map.
   */
  public MapBundle getCurrentMap()
  {
    return _currentMap;
  }

  /**
   * Set a filter.
   * @param filter Filter to set or <code>null</code> to remove it.
   */
  public void setFilter(Filter<Marker> filter)
  {
    _filter=filter;
  }

  /**
   * Set the flag to use labels or not.
   * @param useLabels <code>true</code> to display labels, <code>false</code> to hide them.
   */
  public void useLabels(boolean useLabels)
  {
    _useLabels=useLabels;
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
    _background=loadImage(mapImageFile);
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
      g.drawImage(_background,0,0,null);
    }
    paintLinkPoints(g);
    paintMarkers(g);
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
    Map map=_currentMap.getMap();
    GeoReference geoReference=map.getGeoReference();
    Dimension pixelPosition=geoReference.geo2pixel(link.getHotPoint());

    g.setColor(Color.RED);
    int x=pixelPosition.width;
    int y=pixelPosition.height;
    g.fillRect(x-10,y-10,20,20);
  }

  private void paintMarkers(Graphics g)
  {
    if (_currentMap!=null)
    {
      MarkersManager markersManager=_currentMap.getData();
      List<Marker> markers=markersManager.getAllMarkers();
      for(Marker marker : markers)
      {
        boolean ok=((_filter==null)||(_filter.accept(marker)));
        if (ok)
        {
          paintMarker(marker,g);
        }
      }
    }
  }

  private void paintMarker(Marker marker, Graphics g)
  {
    Map map=_currentMap.getMap();
    GeoReference geoReference=map.getGeoReference();
    Dimension pixelPosition=geoReference.geo2pixel(marker.getPosition());

    // Grab icon
    BufferedImage image=null;
    Category category = marker.getCategory();
    if (category != null) {
      String icon = category.getIcon();
      image=getIcon(icon);
    }
    int x=pixelPosition.width;
    int y=pixelPosition.height;
    if (image!=null)
    {
      int width=image.getWidth();
      int height=image.getHeight();
      g.drawImage(image,x-width/2,y-height/2,null);
    }
    else
    {
      g.setColor(Color.RED);
      g.drawRect(x,y,4,4);
    }
    // Label
    if (_useLabels)
    {
      String label=marker.getLabel();
      if ((label!=null) && (label.length()>0))
      {
        drawStringWithHalo(g,x+10,y,label,Color.WHITE,Color.BLACK);
      }
    }
  }

  private void drawStringWithHalo(Graphics g, int x, int y, String text, Color foreground, Color halo)
  {
    g.setColor(halo);
    for(int i=x-1;i<=x+1;i++)
    {
      for(int j=y-1;j<=y+1;j++)
      {
        if ((i!=x) || (j!=y))
        {
          g.drawString(text, i, j);
        }
      }
    }
    g.setColor(foreground);
    g.drawString(text, x, y);
  }

  private BufferedImage getIcon(String name)
  {
    BufferedImage image=_markerIcons.get(name);
    if (image==null) {
      File iconFile=_mapsManager.getIconFile(name);
      image=loadImage(iconFile);
      _markerIcons.put(name,image);
    }
    return image;
  }

  private BufferedImage loadImage(File inputFile)
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
