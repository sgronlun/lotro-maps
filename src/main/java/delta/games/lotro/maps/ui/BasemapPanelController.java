package delta.games.lotro.maps.ui;

import java.awt.Component;
import java.awt.Dimension;

import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemap;
import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemapsManager;
import delta.games.lotro.maps.ui.layers.BasemapLayer;
import delta.games.lotro.maps.ui.navigation.MapViewDefinition;

/**
 * Controller for a basemap panel.
 * It uses a map panel and adds a basemap layer on it.
 * @author DAM
 */
public class BasemapPanelController
{
  private static final Dimension MAX_SIZE=new Dimension(1024,768);

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
   * Get the managed map panel controller.
   * @return the managed map panel controller.
   */
  public MapPanelController getMapPanelController()
  {
    return _mapPanel;
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
    MapUiUtils.configureMapPanel(_mapPanel,basemap.getBoundingBox(),MAX_SIZE);
    _currentMap=basemap;
    _basemapLayer.setMap(basemap);
    // Set view reference
    GeoReference viewReference=mapViewDefinition.getViewReference();
    if (viewReference!=null)
    {
      MapCanvas canvas=_mapPanel.getCanvas();
      canvas.setViewReference(viewReference);
    }
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
