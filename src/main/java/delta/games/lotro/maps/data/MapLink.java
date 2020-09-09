package delta.games.lotro.maps.data;

/**
 * Map link: a point on a map that links to another map.
 * @author DAM
 */
public class MapLink implements MapPoint
{
  private GeoPoint _hotPoint;
  private String _targetMapKey;

  /**
   * Constructor.
   * @param targetMapKey Key of the targeted map.
   * @param hotPoint Geographic point ("hot point") of the link.
   */
  public MapLink(String targetMapKey, GeoPoint hotPoint)
  {
    _hotPoint = hotPoint;
    _targetMapKey = targetMapKey;
  }

  /**
   * Get the label for this point.
   * @return a label or <code>null</code> if none.
   */
  public String getLabel()
  {
    return null;
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
  public String getTargetMapKey()
  {
    return _targetMapKey;
  }

  @Override
  public String toString()
  {
    return "Link to " + _targetMapKey + "@" + _hotPoint;
  }
}
