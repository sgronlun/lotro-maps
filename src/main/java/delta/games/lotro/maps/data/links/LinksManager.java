package delta.games.lotro.maps.data.links;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.maps.data.links.io.xml.LinksXMLParser;
import delta.games.lotro.maps.data.links.io.xml.LinksXMLWriter;

/**
 * Global links manager.
 * @author DAM
 */
public class LinksManager
{
  private File _rootDir;
  private List<MapLink> _links;

  /**
   * Constructor.
   * @param rootDir Root directory.
   */
  public LinksManager(File rootDir)
  {
    _rootDir=rootDir;
    _links=new ArrayList<MapLink>();
    load();
  }

  /**
   * Add a new link.
   * @param link Link to add.
   */
  public void addLink(MapLink link)
  {
    _links.add(link);
  }

  /**
   * Get the links for the given parent ID and content layer ID.
   * @param parentId Parent ID.
   * @param contentLayerId Content layer ID.
   * @return A possibly empty but never <code>null</code> list of links.
   */
  public List<MapLink> getLinks(int parentId, int contentLayerId)
  {
    List<MapLink> ret=new ArrayList<MapLink>();
    for(MapLink link : _links)
    {
      if ((link.getParentId()==parentId) && (link.getContentLayerId()==contentLayerId))
      {
        ret.add(link);
      }
    }
    return ret;
  }

  private void load()
  {
    _links.clear();
    File fromFile=getLinksFile();
    if (fromFile.exists())
    {
      _links.addAll(LinksXMLParser.loadLinks(fromFile));
    }
  }

  /**
   * Write the links file.
   */
  public void write()
  {
    File toFile=getLinksFile();
    LinksXMLWriter.writeLinksFile(toFile,_links);
  }

  private File getLinksFile()
  {
    return new File(_rootDir,"links.xml");
  }
}
