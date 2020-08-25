package delta.games.lotro.maps.data.markers;

import java.util.List;

import delta.games.lotro.maps.data.Marker;

/**
 * Simple test class for the markers finder.
 * @author DAM
 */
public class MainTestMarkersFinder
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    MarkersFinder finder=MarkersFinder.getInstance();
    //int zoneId=1879232928; // Stangard
    //int contentLayer=0;
    int zoneId=1879198510; // The Twenty-first Hall
    int contentLayer=268435638;

    List<Marker> markers=finder.findMarkers(zoneId,contentLayer);
    System.out.println("Found "+markers.size()+" markers");
    for(Marker marker : markers)
    {
      System.out.println(marker);
    }
  }
}
