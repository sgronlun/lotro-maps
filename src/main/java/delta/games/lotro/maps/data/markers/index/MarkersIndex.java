package delta.games.lotro.maps.data.markers.index;

import java.util.Set;

import delta.common.utils.collections.ThriftyIntSet;

/**
 * Index for markers associated to a single integer value (parent zone ID, content layer, ...).
 * @author DAM
 */
public class MarkersIndex
{
  private int _key;
  private ThriftyIntSet _markers;

  /**
   * Constructor.
   * @param key Managed key.
   * @param markerIDs Marker IDs.
   */
  public MarkersIndex(int key, Set<Integer> markerIDs)
  {
    _key=key;
    _markers=new ThriftyIntSet(markerIDs);
  }

  /**
   * Get the managed key.
   * @return a key.
   */
  public int getKey()
  {
    return _key;
  }

  /**
   * Get the markers.
   * @return a set of markers.
   */
  public ThriftyIntSet getMarkers()
  {
    return _markers;
  }

  @Override
  public String toString()
  {
    return "Key: "+_key+", markers: "+_markers;
  }
}
