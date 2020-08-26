package delta.games.lotro.maps.ui.layers;

import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.maps.data.Marker;

/**
 * Simple markers provider.
 * @author DAM
 */
public class SimpleMarkersProvider implements MarkersProvider
{
  private List<Marker> _markers;

  /**
   * Constructor.
   */
  public SimpleMarkersProvider()
  {
    _markers=new ArrayList<Marker>();
  }

  /**
   * Set the markers to show.
   * @param markers Markers to show.
   */
  public void setMarkers(List<Marker> markers)
  {
    _markers.clear();
    _markers.addAll(markers);
  }

  @Override
  public List<Marker> getMarkers()
  {
    return _markers;
  }
}
