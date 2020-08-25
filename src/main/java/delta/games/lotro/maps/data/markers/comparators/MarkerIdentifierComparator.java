package delta.games.lotro.maps.data.markers.comparators;

import java.util.Comparator;

import delta.games.lotro.maps.data.Marker;

/**
 * Comparator for markers using their identifier.
 * @author DAM
 */
public class MarkerIdentifierComparator implements Comparator<Marker>
{
  @Override
  public int compare(Marker o1, Marker o2)
  {
    return Integer.compare(o1.getId(),o2.getId());
  }
}
