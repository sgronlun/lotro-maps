package delta.games.lotro.maps.data;

/**
 * Marker.
 * @author DAM
 */
public class Marker implements MapPoint
{
  private int _id;
  private GeoPoint _position;
  private String _label;
  private int _did;
  private int _categoryCode;
  private int _parentZoneId;

  /**
   * Constructor.
   */
  public Marker()
  {
    _label="";
  }

  /**
   * Get the identifier of this marker.
   * @return a map-local identifier.
   */
  public int getId()
  {
    return _id;
  }

  /**
   * Set the identifier of this marker.
   * @param id Identifier to set.
   */
  public void setId(int id)
  {
    _id=id;
  }

  /**
   * Get the position of this marker.
   * @return a geographic position or <code>null</code> if not set.
   */
  public GeoPoint getPosition()
  {
    return _position;
  }

  /**
   * Set the position of this marker.
   * @param position the position to set (may be <code>null</code>).
   */
  public void setPosition(GeoPoint position)
  {
    _position=position;
  }

  /**
   * Get the label for this marker.
   * @return a label.
   */
  public String getLabel()
  {
    return _label;
  }

  /**
   * Set the label for this marker.
   * @param label Label to set.
   */
  public void setLabel(String label)
  {
    _label=label;
  }

  /**
   * Get the associated DID.
   * @return a DID or 0.
   */
  public int getDid()
  {
    return _did;
  }

  /**
   * Set the associated DID.
   * @param did DID to set.
   */
  public void setDid(int did)
  {
    _did=did;
  }

  /**
   * Get the category code for this marker.
   * @return a category code (<code>0</code> as default).
   */
  public int getCategoryCode()
  {
    return _categoryCode;
  }

  /**
   * Set the category for this marker.
   * @param categoryCode Cateory code.
   */
  public void setCategoryCode(int categoryCode)
  {
    _categoryCode=categoryCode;
  }

  /**
   * Get the parent zone ID.
   * @return an ID for area/dungeon.
   */
  public int getParentZoneId()
  {
    return _parentZoneId;
  }

  /**
   * Set the parent zone ID.
   * @param parentZoneId ID to set.
   */
  public void setParentZoneId(int parentZoneId)
  {
    _parentZoneId=parentZoneId;
  }

  @Override
  public String toString()
  {
    return _id+":"+_label+" @"+_position+", category="+_categoryCode+", parent="+_parentZoneId;
  }
}
