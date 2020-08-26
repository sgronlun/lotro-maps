package delta.games.lotro.maps.ui.layers;

import java.util.List;

import delta.games.lotro.maps.data.Marker;

/**
 * Markers provider.
 * @author DAM
 */
public interface MarkersProvider
{
  /**
   * Get the markers for a map.
   * @return a list of markers.
   */
  List<Marker> getMarkers();
}
