package delta.games.lotro.maps.data.markers.io.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.maps.data.Marker;

/**
 * Map markers I/O.
 * @author DAM
 */
public class MarkersIO
{
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
    List<Marker> markers=null;
    Element root=DOMParsingTools.parse(markersFile);
    if (root!=null)
    {
      markers=MarkersXMLParser.parseMarkers(root);
    }
    return markers;
  }
}
