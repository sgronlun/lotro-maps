package delta.games.lotro.maps.data;

import java.util.Comparator;

/**
 * Comparator for map bundles, using their name.
 * @author DAM
 */
public class MapBundleNameComparator implements Comparator<MapBundle>
{
  public int compare(MapBundle o1, MapBundle o2)
  {
    String label1=o1.getLabel();
    String label2=o2.getLabel();
    return label1.compareTo(label2);
  }
}
