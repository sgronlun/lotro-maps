package delta.games.lotro.maps.data;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import delta.common.utils.files.FilesFinder;
import delta.common.utils.files.filter.FileTypePredicate;
import delta.games.lotro.maps.data.io.xml.CategoriesXMLParser;
import delta.games.lotro.maps.data.io.xml.MapXMLParser;

/**
 * Maps manager.
 * @author DAM
 */
public class MapsManager
{
  private File _rootDir;
  private HashMap<String,MapBundle> _maps;
  private CategoriesManager _categoriesManager;

  /**
   * Constructor.
   * @param rootDir Root directory for maps data.
   */
  public MapsManager(File rootDir)
  {
    _rootDir=rootDir;
    _maps=new HashMap<String,MapBundle>();
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
      File mapFile=new File(mapDir,"markers.xml");
      MapXMLParser mapParser=new MapXMLParser(_categoriesManager);
      if (mapFile.exists())
      {
        MapBundle bundle=mapParser.parseXML(mapFile);
        addMap(bundle);
      }
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
    String pathName=iconName+".gif";
    return new File(iconsDir,pathName);
  }

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
