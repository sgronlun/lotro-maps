package delta.games.lotro.maps.data.markers.index;

import java.util.Set;
import java.util.TreeSet;

/**
 * Index for markers associated to a single integer value (parent zone ID, content layer, ...).
 * @author DAM
 */
public class MarkersIndex
{
  private int _key;
  private Set<Integer> _markers;

  /**
   * Constructor.
   * @param key Managed key.
   */
  public MarkersIndex(int key)
  {
    _key=key;
    _markers=new TreeSet<Integer>();
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
  public Set<Integer> getMarkers()
  {
    return _markers;
  }

  /**
   * Add a marker.
   * @param markerId Marker to add.
   */
  public void addMarker(int markerId)
  {
    _markers.add(Integer.valueOf(markerId));
  }

  /**
   * Remove all markers.
   */
  public void clear()
  {
    _markers.clear();
  }
}
