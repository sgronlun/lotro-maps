package delta.games.lotro.maps.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
  private List<MapLink> _links;

  /**
   * Constructor.
   * @param key Identifying key for this map.
   */
  public Map(String key)
  {
    _key=key;
    _labels=new Labels();
    _links=new ArrayList<MapLink>();
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
   * Get the name of this map in the default locale.
   * @return a map name.
   */
  public String getLabel()
  {
    return _labels.getLabel();
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

  /**
   * Add a new link.
   * @param link Link to add.
   */
  public void addLink(MapLink link)
  {
    _links.add(link);
  }

  /**
   * Get a list of all links.
   * @return a possibly empty but not <code>null</code> list of links.
   */
  public List<MapLink> getAllLinks()
  {
    return new ArrayList<MapLink>(_links);
  }

  @Override
  public String toString()
  {
    return "Map: key="+_key+", labels="+_labels+", reference=" + _geoRef + ", last updated="+_lastUpdate;
  }
}
