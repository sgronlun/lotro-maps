package delta.games.lotro.maps.data.basemaps;

import java.util.Comparator;

/**
 * Comparator for georeferenced base maps, using their name.
 * @author DAM
 */
public class GeoreferencedBasemapNameComparator implements Comparator<GeoreferencedBasemap>
{
  public int compare(GeoreferencedBasemap o1, GeoreferencedBasemap o2)
  {
    String name1=o1.getName();
    String name2=o2.getName();
    return name1.compareTo(name2);
  }
}
