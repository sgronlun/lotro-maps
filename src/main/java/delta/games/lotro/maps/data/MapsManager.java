package delta.games.lotro.maps.data;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.common.utils.files.FilesFinder;
import delta.common.utils.files.filter.FileTypePredicate;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.maps.data.io.xml.CategoriesXMLParser;
import delta.games.lotro.maps.data.io.xml.CategoriesXMLWriter;
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
    _categoriesManager=new CategoriesManager();
    File markersDir=new File(_rootDir,"markers");
    _markersManager=new GlobalMarkersManager(markersDir);
    _markersFinder=new MarkersFinder(_rootDir,_markersManager);
  }

  /**
   * Get the root directory for this map.
   * @return a directory.
   */
  public File getRootDir()
  {
    return _rootDir;
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
    // Load categories
    CategoriesXMLParser parser=new CategoriesXMLParser();
    File categoriesFile=new File(_rootDir,"categories.xml");
    _categoriesManager=parser.parseXML(categoriesFile);
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
   * Get the links file for a map.
   * @param mapKey Key of the targeted map.
   * @return A file.
   */
  public File getLinksFile(String mapKey)
  {
    File mapsDir=new File(_rootDir,"maps");
    File mapDir=new File(mapsDir,mapKey);
    File linksFile=new File(mapDir,"links.xml");
    return linksFile;
  }

  /**
   * Save categories.
   */
  public void saveCategories()
  {
    CategoriesXMLWriter writer=new CategoriesXMLWriter();
    File toFile=new File(_rootDir,"categories.xml");
    writer.write(toFile,_categoriesManager,EncodingNames.UTF_8);
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
   * Get the directory for a single map.
   * @param key Map identifier.
   * @return A directory.
   */
  public File getMapDir(String key)
  {
    File mapsDir=new File(_rootDir,"maps");
    File mapDir=new File(mapsDir,key);
    return mapDir;
  }

  /**
   * Get the file for a marker icon.
   * @param iconName Icon name.
   * @return A file.
   */
  public File getIconFile(String iconName)
  {
    File iconsDir=new File(_rootDir,"images");
    String pathName=iconName+".png";
    return new File(iconsDir,pathName);
  }

  /**
   * Add a map.
   * @param bundle Map to add.
   */
  public void addMap(MapBundle bundle)
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

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    File rootDir=new File("../lotro-maps-db");
    MapsManager mapsManager=new MapsManager(rootDir);
    mapsManager.load();
  }
}
