package delta.games.lotro.maps.data;

/**
 * @author dm
 */
public class MapBundle
{
  private String _key;
  private Map _map;
  private MarkersManager _markers;

  public MapBundle(String key)
  {
    _key=key;
    _map=new Map(key);
    _markers=new MarkersManager();
  }

  public String getKey()
  {
    return _key;
  }

  public Map getMap()
  {
    return _map;
  }

  public MarkersManager getData()
  {
    return _markers;
  }
}
