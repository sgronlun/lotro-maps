package delta.games.lotro.maps.data;

import java.util.Date;

/**
 * Map description.
 * @author DAM
 */
public class Map
{
  private String _key;
  private GeoReference _geoRef;
  private String _name;
  private Date _lastUpdate;

  /**
   * Constructor.
   * @param key Identifying key for this map.
   */
  public Map(String key)
  {
    _key=key;
    _name="";
  }

  /**
   * Get the identifying key for this map.
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
    return _name;
  }

  /**
   * Set the name for this map.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the geographic reference.
   * @return the geographic reference.
   */
  public GeoReference getGeoReference()
  {
    return _geoRef;
  }

  /**
   * Set the geographic reference.
   * @param geoReference Geographic reference to set.
   */
  public void setGeoReference(GeoReference geoReference)
  {
    _geoRef=geoReference;
  }

  /**
   * Get the last update date for this map.
   * @return a date.
   */
  public Date getLastUpdate()
  {
    return _lastUpdate;
  }

  /**
   * Set the last update date for this map.
   * @param lastUpdate Date to set.
   */
  public void setLastUpdate(Date lastUpdate)
  {
    _lastUpdate=lastUpdate;
  }

  @Override
  public String toString()
  {
    return "Map: key="+_key+", name="+_name+", reference=" + _geoRef + ", last updated="+_lastUpdate;
  }
}
