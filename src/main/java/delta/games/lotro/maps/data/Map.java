package delta.games.lotro.maps.data;

import java.util.Date;

/**
 * @author dm
 */
public class Map
{
  private String _key;
  private GeoReference _geoRef;
  private Labels _labels;
  private Date _lastUpdate;

  public Map(String key)
  {
    _key=key;
  }

  public String getKey()
  {
    return _key;
  }

  public GeoReference getGeoReference()
  {
    return _geoRef;
  }

  public void setGeoReference(GeoReference geoReference)
  {
    _geoRef=geoReference;
  }

  public Labels getLabels()
  {
    return _labels;
  }

  public Date getLastUpdate()
  {
    return _lastUpdate;
  }

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
