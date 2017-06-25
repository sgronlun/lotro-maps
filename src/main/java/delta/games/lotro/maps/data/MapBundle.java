package delta.games.lotro.maps.data;

/**
 * Gathers all data for a single map.
 * @author DAM
 */
public class MapBundle
{
  private String _key;
  private Map _map;
  private MarkersManager _markers;

  /**
   * Constructor.
   * @param key Identifying key for the managed map.
   */
  public MapBundle(String key)
  {
    _key=key;
    _map=new Map(key);
    _markers=new MarkersManager();
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
   * Get the managed map.
   * @return the managed map.
   */
  public Map getMap()
  {
    return _map;
  }

  /**
   * Get the managed markers.
   * @return the managed markers.
   */
  public MarkersManager getData()
  {
    return _markers;
  }
}
