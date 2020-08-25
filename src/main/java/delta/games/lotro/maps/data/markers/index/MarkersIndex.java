package delta.games.lotro.maps.data.markers.index;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.maps.data.Marker;

/**
 * Index for markers associated to a single integer value (parent zone ID, content layer, ...).
 * @author DAM
 */
public class MarkersIndex
{
  private int _key;
  private List<Marker> _markers;

  /**
   * Constructor.
   * @param key Managed key.
   */
  public MarkersIndex(int key)
  {
    _key=key;
    _markers=new ArrayList<Marker>();
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
   * @return a list of markers.
   */
  public List<Marker> getMarkers()
  {
    return _markers;
  }

  /**
   * Add a marker.
   * @param marker Marker to add.
   */
  public void addMarker(Marker marker)
  {
    _markers.add(marker);
  }

  /**
   * Remove all markers.
   */
  public void clear()
  {
    _markers.clear();
  }
}
