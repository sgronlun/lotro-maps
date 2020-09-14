package delta.games.lotro.maps.data.io;

import java.io.File;

import org.w3c.dom.Element;

import delta.common.utils.xml.DOMParsingTools;
import delta.games.lotro.maps.data.GeoreferencedBasemap;
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
}
