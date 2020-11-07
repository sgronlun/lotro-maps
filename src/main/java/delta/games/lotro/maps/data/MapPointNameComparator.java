package delta.games.lotro.maps.data;

import java.util.Comparator;

/**
 * Comparator for map points using their names.
 * @author DAM
 */
public class MapPointNameComparator implements Comparator<MapPoint>
{
  @Override
  public int compare(MapPoint o1, MapPoint o2)
  {
    String label1=o1.getLabel();
    String label2=o2.getLabel();
    if (label1==null)
    {
      return (label2!=null)?1:0;
    }
    return (label2!=null)?label1.compareTo(label2):-1;
  }
}
