package delta.games.lotro.maps.data.markers.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import delta.games.lotro.maps.data.Marker;

/**
 * Map markers I/O.
 * @author DAM
 */
public class MarkersIO
{
  private static final Logger LOGGER=Logger.getLogger(MarkersIO.class);

  /**
   * Load map markers from the given file.
   * @param markersFile Markers file.
   * @return A markers manager.
   */
  public static List<Marker> loadMarkers(File markersFile)
  {
    List<Marker> markers=null;
    if (markersFile.exists())
    {
      markers=parseMarkers(markersFile);
    }
    if (markers==null)
    {
      markers=new ArrayList<Marker>();
    }
    return markers;
  }

  private static List<Marker> parseMarkers(File markersFile)
  {
    long now1=System.currentTimeMillis();
    List<Marker> markers=MarkersSaxParser.parseMarkersFile(markersFile);
    long now2=System.currentTimeMillis();
    LOGGER.info("Loaded "+markers.size()+" markers from file "+markersFile.getName()+" in "+(now2-now1)+"ms");
    return markers;
  }
}
