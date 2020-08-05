package delta.games.lotro.maps.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.collections.filters.Filter;

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
   * Get a list of all markers that pass the given filter.
   * @param filter A filter or <code>null</code> to accept all.
   * @return A possibly empty but not null list of markers.
   */
  public List<Marker> getMarkers(Filter<Marker> filter)
  {
    return getFilteredMarkers(filter,_markers);
  }

  /**
   * Get a list of all markers that pass the given filter.
   * @param filter A filter or <code>null</code> to accept all.
   * @param markers Markers to use.
   * @return A possibly empty but never <code>null</code> list of markers.
   */
  public static List<Marker> getFilteredMarkers(Filter<Marker> filter, List<Marker> markers)
  {
    List<Marker> ret=new ArrayList<Marker>();
    for(Marker marker : markers)
    {
      if ((filter==null) || (filter.accept(marker)))
      {
        ret.add(marker);
      }
    }
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

  /**
   * Get all the markers that fir a given filter and are within a box.
   * @param filter Filter to use (or <code>null</code> to accept all).
   * @param box Box to use.
   * @return A possibly empty but not <code>null</code> list of markers.
   */
  public List<Marker> getAllMarkers(Filter<Marker> filter, GeoBox box)
  {
    List<Marker> ret=new ArrayList<Marker>();
    for(Marker marker : _markers)
    {
      if ((filter==null) || (filter.accept(marker)))
      {
        boolean isInBox=box.isInBox(marker.getPosition());
        if (isInBox)
        {
          ret.add(marker);
        }
      }
    }
    return ret;
  }

  /**
   * Get a marker using its unique identifier.
   * @param pointID Identifier to search.
   * @return A marker or <code>null</code> if not found.
   */
  public Marker getPoint(int pointID)
  {
    Marker ret=null;
    for(Marker marker : _markers)
    {
      if (marker.getId()==pointID)
      {
        ret=marker;
        break;
      }
    }
    return ret;
  }

  /**
   * Remove all markers.
   */
  public void clear()
  {
    _markers.clear();
  }
}
