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
    String name1=o1.getName();
    String name2=o2.getName();
    return name1.compareTo(name2);
  }
}
