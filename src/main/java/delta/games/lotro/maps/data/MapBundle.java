package delta.games.lotro.maps.data;

import java.io.File;

import delta.games.lotro.maps.data.io.MapsIO;

/**
 * Gathers all data for a single map.
 * @author DAM
 */
public class MapBundle
{
  private int _key;
  private File _rootDir;
  // Map description
  private GeoreferencedBasemap _map;

  /**
   * Constructor.
   * @param rootDir Root directory.
   * @param key Identifying key for the managed map.
   */
  public MapBundle(int key, File rootDir)
  {
    _key=key;
    _rootDir=rootDir;
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
   * Get the identifying key for the managed map.
   * @return an identifying key.
   */
  public int getKey()
  {
    return _key;
  }

  /**
   * Get the name of this map.
   * @return a map name.
   */
  public String getName()
  {
    return getMap().getName();
  }

  /**
   * Get the managed map.
   * @return the managed map.
   */
  public GeoreferencedBasemap getMap()
  {
    if (_map==null)
    {
      _map=MapsIO.loadMap(_rootDir);
    }
    if (_map==null)
    {
      _map=new GeoreferencedBasemap(_key);
    }
    File imageFile=new File(_rootDir,"map.png");
    _map.setImageFile(imageFile);
    return _map;
  }
}
