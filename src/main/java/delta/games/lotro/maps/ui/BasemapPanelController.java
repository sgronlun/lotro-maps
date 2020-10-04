package delta.games.lotro.maps.ui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.SwingUtilities;

import delta.games.lotro.maps.data.GeoBox;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemap;
import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemapsManager;
import delta.games.lotro.maps.data.view.BoundedZoomFilter;
import delta.games.lotro.maps.data.view.ZoomFilter;
import delta.games.lotro.maps.ui.constraints.MapBoundsConstraint;
import delta.games.lotro.maps.ui.layers.BasemapLayer;
import delta.games.lotro.maps.ui.navigation.MapViewDefinition;

/**
 * Controller for a basemap panel.
 * It uses a map panel and adds a basemap layer on it.
 * @author DAM
 */
public class BasemapPanelController
{
  private MapPanelController _mapPanel;
  private GeoreferencedBasemapsManager _basemapsManager;
  private GeoreferencedBasemap _currentMap;
  private BasemapLayer _basemapLayer;

  /**
   * Constructor.
   * @param basemapsManager Basemaps manager.
   */
  public BasemapPanelController(GeoreferencedBasemapsManager basemapsManager)
  {
    _mapPanel=new MapPanelController();
    _basemapsManager=basemapsManager;
    _basemapLayer=new BasemapLayer();
    MapCanvas canvas=_mapPanel.getCanvas();
    canvas.addLayer(_basemapLayer);
  }

  /**
   * Get the current basemap.
   * @return the current basemap.
   */
  public GeoreferencedBasemap getCurrentBasemap()
  {
    return _currentMap;
  }

  /**
   * Get the managed map canvas.
   * @return a map canvas.
   */
  public MapCanvas getCanvas()
  {
    return _mapPanel.getCanvas();
  }

  /**
   * Get the managed component.
   * @return the managed component.
   */
  public Component getComponent()
  {
    return _mapPanel.getLayers();
  }

  /**
   * Get the view definition for this map view.
   * @return a view definition or <code>null</code> if no basemap.
   */
  public MapViewDefinition getMapViewDefinition()
  {
    if (_currentMap==null)
    {
      return null;
    }
    int basemapId=_currentMap.getIdentifier();
    MapCanvas canvas=_mapPanel.getCanvas();
    Dimension size=canvas.getSize();
    MapViewDefinition mapViewDefinition=new MapViewDefinition(basemapId,canvas.getViewReference(),size);
    return mapViewDefinition;
  }

  /**
   * Set the basemap to display.
   * @param basemap Basemap.
   */
  private void setMapOnCanvas(GeoreferencedBasemap basemap)
  {
    MapCanvas canvas=_mapPanel.getCanvas();
    // Set base map
    _currentMap=basemap;
    _basemapLayer.setMap(basemap);
    Dimension baseMapDimension=_basemapLayer.getBasemapDimension();
    canvas.setPreferredSize(baseMapDimension);
    GeoBox mapBounds=_basemapLayer.getMapBounds();
    canvas.setMapConstraint(new MapBoundsConstraint(canvas,mapBounds));
    // Set reference
    GeoReference mapReference=basemap.getGeoReference();
    canvas.setViewReference(mapReference);
    // Set zoom filter
    float geo2pixel=mapReference.getGeo2PixelFactor();
    ZoomFilter zoomFilter=new BoundedZoomFilter(Float.valueOf(geo2pixel),Float.valueOf(geo2pixel*16));
    canvas.setZoomFilter(zoomFilter);
  }

  /**
   * Set the map to display.
   * @param mapKey Map identifier.
   */
  public void setMap(int mapKey)
  {
    MapViewDefinition mapViewDefinition=new MapViewDefinition(mapKey,null,null);
    setMap(mapViewDefinition);
  }

  /**
   * Set the map to display.
   * @param mapViewDefinition Map view definition.
   */
  public void setMap(MapViewDefinition mapViewDefinition)
  {
    int mapKey=mapViewDefinition.getMapKey();
    GeoreferencedBasemap basemap=_basemapsManager.getMapById(mapKey);
    setMapOnCanvas(basemap);
    // Set map view size
    Dimension viewSize=mapViewDefinition.getDimension();
    if (viewSize==null)
    {
      viewSize=fitInSize(new Dimension(1024,768));
    }
    _mapPanel.setViewSize(viewSize);
    // Set view reference
    GeoReference viewReference=mapViewDefinition.getViewReference();
    if (viewReference!=null)
    {
      MapCanvas canvas=_mapPanel.getCanvas();
      canvas.setViewReference(viewReference);
    }
  }

  private Dimension fitInSize(Dimension maxSize)
  {
    final MapCanvas canvas=_mapPanel.getCanvas();
    // Compute adequate zoom factor
    final Dimension mapSize=canvas.getPreferredSize();
    //System.out.println("Map size: "+mapSize);
    //System.out.println("Size: "+maxSize);
    if ((mapSize.width<=maxSize.width) && (mapSize.height<=maxSize.height))
    {
      return mapSize;
    }
    float xFactor=(float)mapSize.width/maxSize.width;
    float yFactor=(float)mapSize.height/maxSize.height;
    final float factor=Math.max(xFactor,yFactor);
    //System.out.println("Factor: "+factor);
    Runnable r=new Runnable()
    {
      public void run()
      {
        canvas.pan(mapSize.width/2,mapSize.height/2);
        canvas.zoom(1/factor);
      }
    };
    SwingUtilities.invokeLater(r);
    Dimension size=new Dimension((int)(mapSize.width/factor),(int)(mapSize.height/factor));
    return size;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    if (_mapPanel!=null)
    {
      _mapPanel.dispose();
      _mapPanel=null;
    }
    _basemapsManager=null;
    _currentMap=null;
    _basemapLayer=null;
  }
}
