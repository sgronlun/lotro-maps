package delta.games.lotro.maps.data;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.common.utils.NumericTools;
import delta.common.utils.files.FilesFinder;
import delta.common.utils.files.filter.FileTypePredicate;
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
  private HashMap<Integer,MapBundle> _maps;
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
    _maps=new HashMap<Integer,MapBundle>();
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

  /**
   * Load map data.
   */
  public void load()
  {
    // Load all maps
    FileFilter filter=new FileTypePredicate(FileTypePredicate.DIRECTORY);
    FilesFinder finder=new FilesFinder();
    File mapsDir=new File(_rootDir,"maps");
    List<File> mapDirs=finder.find(FilesFinder.ABSOLUTE_MODE,mapsDir,filter,false);
    for(File mapDir : mapDirs)
    {
      int mapId=NumericTools.parseInt(mapDir.getName(),0);
      MapBundle bundle=new MapBundle(mapId,mapDir);
      addMap(bundle);
    }
  }

  /**
   * Get a map using its identifying key.
   * @param key Key to use.
   * @return A map bundle or <code>null</code> if not found.
   */
  public MapBundle getMapByKey(int key)
  {
    return _maps.get(Integer.valueOf(key));
  }

  /**
   * Add a map.
   * @param bundle Map to add.
   */
  private void addMap(MapBundle bundle)
  {
    int key=bundle.getKey();
    _maps.put(Integer.valueOf(key),bundle);
  }

  /**
   * Get a list of available maps, sorted by name.
   * @return A list of map bundles.
   */
  public List<MapBundle> getMaps()
  {
    List<MapBundle> bundles=new ArrayList<MapBundle>(_maps.values());
    Collections.sort(bundles,new MapBundleNameComparator());
    return bundles;
  }
}
