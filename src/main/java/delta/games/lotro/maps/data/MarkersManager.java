package delta.games.lotro.maps.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Manager for a collection of markers.
 * @author DAM
 */
public class MarkersManager
{
  private List<Marker> _markers;

  /**
   * Constructor.
   */
  public MarkersManager()
  {
    _markers=new ArrayList<Marker>();
  }

  /**
   * Get all the markers for a given category.
   * @param category Category to use.
   * @return A possibly empty but not <code>null</code> list of markers.
   */
  public List<Marker> getByCategory(Category category)
  {
    List<Marker> ret=new ArrayList<Marker>();
    if (category!=null)
    {
      int code=category.getCode();
      for(Marker marker : _markers)
      {
        int currentCode=marker.getCategoryCode();
        if (currentCode==code)
        {
          ret.add(marker);
        }
      }
    }
    return ret;
  }

  /**
   * Get the names of all the managed markers.
   * @return A sorted list of marker names.
   */
  public List<String> getMarkerNames()
  {
    Set<String> names=new HashSet<String>();
    for(Marker marker : _markers)
    {
      String currentName=marker.getLabel();
      names.add(currentName);
    }
    List<String> ret=new ArrayList<String>(names);
    Collections.sort(ret);
    return ret;
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
   * Get a list of all managed markers.
   * @return A possibly empty but not <code>null</code> list of markers.
   */
  public List<Marker> getAllMarkers()
  {
    List<Marker> ret=new ArrayList<Marker>();
    ret.addAll(_markers);
    return ret;
  }
}
