package delta.games.lotro.maps.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.maps.data.io.MapsIO;

/**
 * Gathers all data for a single map.
 * @author DAM
 */
public class MapBundle
{
  private String _key;
  private File _rootDir;
  // Map description
  private GeoreferencedBasemap _map;
  // Map points
  private MarkersManager _markers;
  // Map links
  private List<MapLink> _links;

  /**
   * Constructor.
   * @param rootDir Root directory.
   * @param key Identifying key for the managed map.
   */
  public MapBundle(String key, File rootDir)
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
  public String getKey()
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
    File imageFile=new File(_rootDir,"map_en.png");
    _map.setImageFile(imageFile);
    return _map;
  }

  /**
   * Get the managed markers.
   * @return the managed markers.
   */
  public MarkersManager getData()
  {
    if (_markers==null)
    {
      _markers=MapsIO.loadMarkers(_rootDir);
    }
    if (_markers==null)
    {
      _markers=new MarkersManager();
    }
    return _markers;
  }

  /**
   * Get the managed map.
   * @return the managed map.
   */
  public List<MapLink> getLinks()
  {
    if (_links==null)
    {
      _links=MapsIO.loadLinks(_rootDir);
    }
    if (_links==null)
    {
      _links=new ArrayList<MapLink>();
    }
    return _links;
  }
}
