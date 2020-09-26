package delta.games.lotro.maps.data.basemaps;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.id.IdentifiableComparator;
import delta.common.utils.text.EncodingNames;
import delta.games.lotro.maps.data.basemaps.io.xml.GeoreferencedBasemapXMLParser;
import delta.games.lotro.maps.data.basemaps.io.xml.GeoreferencedBasemapXMLWriter;

/**
 * Manager for all georeferenced basemaps.
 * @author DAM
 */
public class GeoreferencedBasemapsManager
{
  private File _rootDir;
  private Map<Integer,GeoreferencedBasemap> _maps;

  /**
   * Constructor.
   * @param rootDir Root directory for basemaps data.
   */
  public GeoreferencedBasemapsManager(File rootDir)
  {
    _rootDir=rootDir;
    _maps=new HashMap<Integer,GeoreferencedBasemap>();
    load();
  }

  private void load()
  {
    File from=getMapsFile();
    if (from.exists())
    {
      List<GeoreferencedBasemap> basemaps=GeoreferencedBasemapXMLParser.parseXML(from);
      for(GeoreferencedBasemap basemap : basemaps)
      {
        addBasemap(basemap);
      }
    }
  }

  /**
   * Write basemaps to the dedicated XML file.
   */
  public void write()
  {
    File to=getMapsFile();
    List<GeoreferencedBasemap> basemaps=getBasemaps();
    GeoreferencedBasemapXMLWriter.writeBasemapsFile(to,basemaps,EncodingNames.UTF_8);
  }

  private File getMapsFile()
  {
    return new File(_rootDir,"maps.xml");
  }

  /**
   * Add a basemap.
   * @param basemap Basemap to add.
   */
  public void addBasemap(GeoreferencedBasemap basemap)
  {
    File imageFile=getBasemapImageFile(basemap.getIdentifier());
    basemap.setImageFile(imageFile);
    Integer key=Integer.valueOf(basemap.getIdentifier());
    _maps.put(key,basemap);
  }

  /**
   * Get a basemap using its identifier.
   * @param id Basemap identifier.
   * @return A basemap or <code>null</code> if not found.
   */
  public GeoreferencedBasemap getMapById(int id)
  {
    return _maps.get(Integer.valueOf(id));
  }

  /**
   * Get all basemaps.
   * @return a list of basemaps.
   */
  public List<GeoreferencedBasemap> getBasemaps()
  {
    List<GeoreferencedBasemap> ret=new ArrayList<GeoreferencedBasemap>();
    ret.addAll(_maps.values());
    Collections.sort(ret,new IdentifiableComparator<GeoreferencedBasemap>());
    return ret;
  }

  /**
   * Get all basemaps, sorted by name.
   * @return A list of basemaps.
   */
  public List<GeoreferencedBasemap> getMapsByName()
  {
    List<GeoreferencedBasemap> maps=new ArrayList<GeoreferencedBasemap>(_maps.values());
    Collections.sort(maps,new GeoreferencedBasemapNameComparator());
    return maps;
  }

  /**
   * Get the file for the image of a basemap.
   * @param basemapId Basemap identifier.
   * @return An image file.
   */
  public File getBasemapImageFile(int basemapId)
  {
    return new File(_rootDir,basemapId+".png");
  }

  /**
   * Dump the contents of this manager.
   */
  public void dump()
  {
    List<GeoreferencedBasemap> maps=getBasemaps();
    System.out.println("Basemaps: ("+maps.size()+")");
    for(GeoreferencedBasemap map : maps)
    {
      System.out.println("* "+map);
    }
  }
}
