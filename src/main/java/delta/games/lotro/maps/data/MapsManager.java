package delta.games.lotro.maps.data;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.common.utils.files.FilesFinder;
import delta.common.utils.files.filter.FileTypePredicate;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.markers.GlobalMarkersManager;
import delta.games.lotro.maps.data.markers.MarkersFinder;

/**
 * Maps manager.
 * @author DAM
 */
public class MapsManager
{
  private File _rootDir;
  private HashMap<String,MapBundle> _maps;
  private CategoriesManager _categoriesManager;
  private GlobalMarkersManager _markersManager;
  private MarkersFinder _markersFinder;

  /**
   * Constructor.
   * @param rootDir Root directory for maps data.
   */
  public MapsManager(File rootDir)
  {
    _rootDir=rootDir;
    _maps=new HashMap<String,MapBundle>();
    File categoriesDir=new File(_rootDir,"categories");
    _categoriesManager=new CategoriesManager(categoriesDir);
    File markersDir=new File(_rootDir,"markers");
    _markersManager=new GlobalMarkersManager(markersDir);
    _markersFinder=new MarkersFinder(_rootDir,_markersManager);
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
      String key=mapDir.getName();
      MapBundle bundle=new MapBundle(key,mapDir);
      addMap(bundle);
    }
  }

  /**
   * Get a map using its identifying key.
   * @param key Key to use.
   * @return A map bundle or <code>null</code> if not found.
   */
  public MapBundle getMapByKey(String key)
  {
    return _maps.get(key);
  }

  /**
   * Add a map.
   * @param bundle Map to add.
   */
  private void addMap(MapBundle bundle)
  {
    String key=bundle.getKey();
    _maps.put(key,bundle);
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
