package delta.games.lotro.maps.data.markers.comparators;

import java.util.Comparator;

import delta.games.lotro.maps.data.Marker;

/**
 * Comparator for markers using their Data ID.
 * @author DAM
 */
public class MarkerDidComparator implements Comparator<Marker>
{
  @Override
  public int compare(Marker o1, Marker o2)
  {
    return Integer.compare(o1.getDid(),o2.getDid());
  }
}
