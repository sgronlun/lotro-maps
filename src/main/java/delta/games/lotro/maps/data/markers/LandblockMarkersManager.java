package delta.games.lotro.maps.data.markers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.maps.data.Marker;

/**
 * Markers manager for a single landblock.
 * @author DAM
 */
public class LandblockMarkersManager
{
  private static final Logger LOGGER=Logger.getLogger(LandblockMarkersManager.class);
  private Map<Integer,Marker> _markers;
  private int _firstId;
  private int _nextId;

  /**
   * Constructor.
   * @param firstId First markerId in this block.
   */
  public LandblockMarkersManager(int firstId)
  {
    _markers=new HashMap<Integer,Marker>();
    _firstId=firstId;
    _nextId=firstId;
  }

  /**
   * Get the markers in this block.
   * @return A list of markers, possibly empty but never <code>null</code>.
   */
  public List<Marker> getMarkers()
  {
    return new ArrayList<Marker>(_markers.values());
  }

  /**
   * Get a marker using its identifier.
   * @param markerId Marker identifier.
   * @return A marker or <code>null</code> if not found.
   */
  public Marker getMarkerById(int markerId)
  {
    return _markers.get(Integer.valueOf(markerId));
  }

  /**
   * Register a marker.
   * @param marker Marker to add.
   */
  public void registerMarker(Marker marker)
  {
    int markerId=marker.getId();
    if (markerId==0)
    {
      markerId=_nextId;
      _nextId++;
      marker.setId(markerId);
    }
    else
    {
      if (markerId>=_nextId)
      {
        _nextId=markerId+1;
      }
    }
    Integer key=Integer.valueOf(markerId);
    _markers.put(key,marker);
    if (_markers.size()==256*16)
    {
      LOGGER.warn("Markers block overwhelmed: "+this);
    }
  }

  /**
   * Remove a marker.
   * @param marker Marker to remove.
   */
  public void removeMarker(Marker marker)
  {
    _markers.remove(Integer.valueOf(marker.getId()));
  }

  /**
   * Remove all markers.
   */
  public void clear()
  {
    _nextId=_firstId;
    _markers.clear();
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder("Markers block: firstId=");
    sb.append(String.format("%08X",Integer.valueOf(_firstId)));
    sb.append(", nextId=");
    sb.append(String.format("%08X",Integer.valueOf(_nextId)));
    sb.append(", nbMarkers=").append(_markers.size());
    return sb.toString();
  }
}
