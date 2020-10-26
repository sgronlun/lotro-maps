package delta.games.lotro.maps.data.markers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.markers.comparators.MarkerIdentifierComparator;
import delta.games.lotro.maps.data.markers.io.xml.MarkersIO;
import delta.games.lotro.maps.data.markers.io.xml.MarkersXMLWriter;

/**
 * Markers manager for a single BIG block (16x16 landblocks).
 * @author DAM
 */
public class BlockMarkersManager
{
  private static final Logger LOGGER=Logger.getLogger(BlockMarkersManager.class);

  private Map<Integer,LandblockMarkersManager> _markers;
  private File _markersFile;
  private int _baseId;

  /**
   * Constructor.
   * @param from Markers file.
   * @param baseId Base markerId in this block.
   */
  public BlockMarkersManager(File from, int baseId)
  {
    _markersFile=from;
    _markers=new HashMap<Integer,LandblockMarkersManager>();
    _baseId=baseId;
  }

  /**
   * Get the landblock manager for the given landblock.
   * @param landblockX Landblock 'small' X.
   * @param landblockY Landblock 'small' Y.
   * @return A landblock manager or <code>null</code> if not found.
   */
  public LandblockMarkersManager getLandblockMarkersManager(int landblockX, int landblockY)
  {
    LandblockMarkersManager block=getBlock(landblockX,landblockY,false);
    return block;
  }

  /**
   * Get the markers in this block.
   * @return A list of markers, possibly empty but never <code>null</code>.
   */
  public List<Marker> getMarkers()
  {
    List<Marker> ret=new ArrayList<Marker>();
    for(LandblockMarkersManager block : _markers.values())
    {
      ret.addAll(block.getMarkers());
    }
    return ret;
  }

  /**
   * Get a marker using its identifier.
   * @param markerId Marker identifier.
   * @return A marker or <code>null</code> if not found.
   */
  public Marker getMarkerById(int markerId)
  {
    int smallBlockX=(markerId&0xF0000)>>16;
    int smallBlockY=(markerId&0x0F000)>>12;
    LandblockMarkersManager block=getBlock(smallBlockX,smallBlockY,false);
    if (block!=null)
    {
      return block.getMarkerById(markerId);
    }
    return null;
  }

  private LandblockMarkersManager getBlock(int smallBlockX, int smallBlockY, boolean createIfNeeded)
  {
    Integer key=Integer.valueOf((smallBlockX<<16)+smallBlockY);
    LandblockMarkersManager block=_markers.get(key);
    if ((block==null) && (createIfNeeded))
    {
      int firstId=_baseId+(smallBlockX<<16)+(smallBlockY<<12);
      block=new LandblockMarkersManager(firstId);
      _markers.put(key,block);
    }
    return block;
  }

  /**
   * Register a marker.
   * @param marker Marker to add.
   * @param smallBlockX Small block X.
   * @param smallBlockY Small block Y.
   */
  public void allocateMarker(Marker marker, int smallBlockX, int smallBlockY)
  {
    LandblockMarkersManager block=getBlock(smallBlockX,smallBlockY,true);
    block.registerMarker(marker);
  }

  /**
   * Register a marker.
   * @param marker Marker to add.
   */
  public void registerMarker(Marker marker)
  {
    int markerId=marker.getId();
    int smallBlockX=(markerId&0xF0000)>>16;
    int smallBlockY=(markerId&0x0F000)>>12;
    LandblockMarkersManager block=getBlock(smallBlockX,smallBlockY,true);
    block.registerMarker(marker);
  }

  /**
   * Load from disk.
   */
  public void load()
  {
    LOGGER.debug("Loading markers from file: "+_markersFile);
    clear();
    if (_markersFile.exists())
    {
      List<Marker> allMarkers=MarkersIO.loadMarkers(_markersFile);
      for(Marker marker : allMarkers)
      {
        registerMarker(marker);
      }
    }
    LOGGER.debug("Loaded "+_markers.size()+" markers.");
  }

  /**
   * Remove all markers.
   */
  public void clear()
  {
    _markers.clear();
  }

  /**
   * Write to disk.
   */
  public void write()
  {
    List<Marker> markers=getMarkers();
    Collections.sort(markers,new MarkerIdentifierComparator());
    MarkersXMLWriter.writeMarkersFile(_markersFile,markers);
  }
}
