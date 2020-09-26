package delta.games.lotro.maps.data;

import java.io.File;

import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemapsManager;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.links.LinksManager;
import delta.games.lotro.maps.data.markers.GlobalMarkersManager;
import delta.games.lotro.maps.data.markers.MarkersFinder;

/**
 * Maps manager.
 * @author DAM
 */
public class MapsManager
{
  private File _rootDir;
  // Basemaps
  private GeoreferencedBasemapsManager _basemapsManager;
  // Categories
  private CategoriesManager _categoriesManager;
  // Markers
  private GlobalMarkersManager _markersManager;
  private MarkersFinder _markersFinder;
  // Links
  private LinksManager _linksManager;

  /**
   * Constructor.
   * @param rootDir Root directory for maps data.
   */
  public MapsManager(File rootDir)
  {
    _rootDir=rootDir;
    // Basemaps
    File mapsDir=new File(_rootDir,"maps");
    _basemapsManager=new GeoreferencedBasemapsManager(mapsDir);
    // Categories
    File categoriesDir=new File(_rootDir,"categories");
    _categoriesManager=new CategoriesManager(categoriesDir);
    // Markers
    File markersDir=new File(_rootDir,"markers");
    _markersManager=new GlobalMarkersManager(markersDir);
    _markersFinder=new MarkersFinder(_rootDir,_markersManager);
    // Links
    _linksManager=new LinksManager(rootDir);
  }

  /**
   * Get the basemaps manager.
   * @return the basemaps manager.
   */
  public GeoreferencedBasemapsManager getBasemapsManager()
  {
    return _basemapsManager;
  }

  /**
   * Get the categories manager.
   * @return the categories manager.
   */
  public CategoriesManager getCategories()
  {
    return _categoriesManager;
  }

  /**
   * Get the markers manager.
   * @return the markers manager.
   */
  public GlobalMarkersManager getMarkersManager()
  {
    return _markersManager;
  }

  /**
   * Get the markers finder.
   * @return the markers finder.
   */
  public MarkersFinder getMarkersFinder()
  {
    return _markersFinder;
  }

  /**
   * Get the links manager.
   * @return the links manager.
   */
  public LinksManager getLinksManager()
  {
    return _linksManager;
  }
}
