package delta.games.lotro.maps.data.markers.comparators;

import java.util.Comparator;

import delta.games.lotro.maps.data.GeoPointComparator;
import delta.games.lotro.maps.data.Marker;

/**
 * Comparator for markers using their position.
 * @author DAM
 */
public class MarkerPositionComparator implements Comparator<Marker>
{
  private GeoPointComparator _comparator=new GeoPointComparator();

  @Override
  public int compare(Marker o1, Marker o2)
  {
    return _comparator.compare(o1.getPosition(),o2.getPosition());
  }
}
