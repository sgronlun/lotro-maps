package delta.games.lotro.maps.data.markers.index;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.maps.data.Marker;

/**
 * Index for markers associated to a single DID.
 * @author DAM
 */
public class DIDMarkersIndex
{
  private int _did;
  private List<Marker> _markers;

  /**
   * Constructor.
   * @param did Managed DID.
   */
  public DIDMarkersIndex(int did)
  {
    _markers=new ArrayList<Marker>();
  }

  /**
   * Get the managed DID.
   * @return a DID.
   */
  public int getDid()
  {
    return _did;
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
