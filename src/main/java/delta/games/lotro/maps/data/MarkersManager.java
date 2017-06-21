package delta.games.lotro.maps.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
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

  public List<Marker> getByCategory(Category category)
  {
    List<Marker> ret=new ArrayList<Marker>();
    int code=category.getCode();
    for(Marker marker : _markers)
    {
      int currentCode=marker.getCategoryCode();
      if (currentCode==code)
      {
        ret.add(marker);
      }
    }
    return ret;
  }

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

  public void addMarker(Marker marker)
  {
    _markers.add(marker);
  }

  public List<Marker> getAllMarkers()
  {
    List<Marker> ret=new ArrayList<Marker>();
    ret.addAll(_markers);
    return ret;
  }
}
