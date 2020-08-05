package delta.games.lotro.maps.data.io;

import java.io.File;
import java.util.List;

import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.maps.data.GeoreferencedBasemap;
import delta.games.lotro.maps.data.MapLink;
import delta.games.lotro.maps.data.MarkersManager;
import delta.games.lotro.maps.data.io.xml.MapXMLParser;

/**
 * Maps I/O.
 * @author DAM
 */
public class MapsIO
{
  /**
   * Load a map from the given directory.
   * @param rootDirectory Directory of map.
   * @return A map or <code>null</code> if map description not found.
   */
  public static GeoreferencedBasemap loadMap(File rootDirectory)
  {
    GeoreferencedBasemap map=null;
    File mapFile=new File(rootDirectory,"map.xml");
    if (mapFile.exists())
    {
      map=parseMapFile(mapFile);
    }
    return map;
  }

  private static GeoreferencedBasemap parseMapFile(File mapFile)
  {
    GeoreferencedBasemap map=null;
    Element root=DOMParsingTools.parse(mapFile);
    if (root!=null)
    {
      map=MapXMLParser.parseMap(root);
    }
    return map;
  }

  /**
   * Load map markers from the given file.
   * @param markersFile Markers file.
   * @return A markers manager.
   */
  public static MarkersManager loadMarkers(File markersFile)
  {
    MarkersManager markers=null;
    if (markersFile.exists())
    {
      markers=parseMarkers(markersFile);
    }
    if (markers==null)
    {
      markers=new MarkersManager();
    }
    return markers;
  }

  private static MarkersManager parseMarkers(File markersFile)
  {
    MarkersManager markers=null;
    Element root=DOMParsingTools.parse(markersFile);
    if (root!=null)
    {
      markers=MapXMLParser.parseMarkers(root);
    }
    return markers;
  }

  /**
   * Load map links.
   * @param rootDirectory Root map directory.
   * @return a list of map links, or <code>null</code>.
   */
  public static List<MapLink> loadLinks(File rootDirectory)
  {
    List<MapLink> ret=null;
    File linksFile=new File(rootDirectory,"links.xml");
    if (linksFile.exists())
    {
      Element root=DOMParsingTools.parse(linksFile);
      if (root!=null)
      {
        ret=MapXMLParser.parseLinks(root);
      }
  }
    return ret;
  }
}
