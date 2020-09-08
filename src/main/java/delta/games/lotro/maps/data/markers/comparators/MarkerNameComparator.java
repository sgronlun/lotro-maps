package delta.games.lotro.maps.data.markers.comparators;

import java.util.Comparator;

import delta.games.lotro.maps.data.Marker;

/**
 * Comparator for markers using their names.
 * @author DAM
 */
public class MarkerNameComparator implements Comparator<Marker>
{
  public int compare(Marker o1, Marker o2)
  {
    String label1=o1.getLabel();
    String label2=o2.getLabel();
    return label1.compareTo(label2);
  }
}
