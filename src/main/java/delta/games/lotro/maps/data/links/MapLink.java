package delta.games.lotro.maps.data.links;

import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.MapPoint;

/**
 * Map link: a point on a map that links to another map.
 * @author DAM
 */
public class MapLink implements MapPoint
{
  private int _parentId; // Parchment map ID, area ID or dungeon ID
  private int _contentLayerId;
  private MapLinkType _type;
  private GeoPoint _hotPoint;
  private int _targetMapKey;
  private String _text;

  /**
   * Constructor.
   * @param parentId Parent identifier.
   * @param contentLayerId Content layer identifier.
   * @param targetMapKey Key of the targeted map.
   * @param hotPoint Geographic point ("hot point") of the link.
   */
  public MapLink(int parentId, int contentLayerId, int targetMapKey, GeoPoint hotPoint)
  {
    _parentId=parentId;
    _contentLayerId=contentLayerId;
    _hotPoint=hotPoint;
    _targetMapKey=targetMapKey;
    _type=MapLinkType.TO_PARCHMENT_MAP;
  }

  /**
   * Get the map link type.
   * @return the map link type.
   */
  public MapLinkType getType()
  {
    return _type;
  }

  /**
   * Set the map link type.
   * @param type Type to set.
   */
  public void setType(MapLinkType type)
  {
    _type=type;
  }

  /**
   * Get the label for this point.
   * @return a label or <code>null</code> if none.
   */
  public String getLabel()
  {
    return _text;
  }

  /**
   * Set the label for this link.
   * @param text Text to set.
   */
  public void setLabel(String text)
  {
    _text=text;
  }

  /**
   * Get the geographic hot point.
   * @return the hot point.
   */
  public GeoPoint getPosition()
  {
    return _hotPoint;
  }

  /**
   * Get the key of the targeted map.
   * @return a map key.
   */
  public int getTargetMapKey()
  {
    return _targetMapKey;
  }

  /**
   * Get the parent identifier.
   * @return the parent identifier.
   */
  public int getParentId()
  {
    return _parentId;
  }

  /**
   * Get the content layer identifier.
   * @return the content layer identifier.
   */
  public int getContentLayerId()
  {
    return _contentLayerId;
  }

  @Override
  public String toString()
  {
    return "Link to " + _targetMapKey + "@" + _hotPoint + ", from="+_parentId+", layer="+_contentLayerId+", label="+_text+", type="+_type;
  }
}
