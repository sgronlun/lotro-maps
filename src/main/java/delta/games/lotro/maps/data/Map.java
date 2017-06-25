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
  private Labels _labels;
  private Date _lastUpdate;

  /**
   * Constructor.
   * @param key Identifying key for this map.
   */
  public Map(String key)
  {
    _key=key;
    _labels=new Labels();
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
   * Get the labels for this map.
   * @return a labels manager.
   */
  public Labels getLabels()
  {
    return _labels;
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
    return "Map: key="+_key+", labels="+_labels+", reference=" + _geoRef + ", last updated="+_lastUpdate;
  }
}
