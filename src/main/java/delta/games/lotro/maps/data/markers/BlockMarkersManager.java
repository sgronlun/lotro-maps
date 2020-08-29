package delta.games.lotro.maps.data.markers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.MarkersManager;
import delta.games.lotro.maps.data.io.MapsIO;
import delta.games.lotro.maps.data.io.xml.MapXMLWriter;
import delta.games.lotro.maps.data.markers.comparators.MarkerIdentifierComparator;

/**
 * Markers manager for a single block.
 * @author DAM
 */
public class BlockMarkersManager
{
  private static final Logger LOGGER=Logger.getLogger(BlockMarkersManager.class);

  private Map<Integer,Marker> _markers;
  private File _markersFile;
  private int _firstId;
  private int _nextId;

  /**
   * Constructor.
   * @param from Markers file.
   * @param firstId First markerId in this block.
   */
  public BlockMarkersManager(File from, int firstId)
  {
    _markersFile=from;
    _markers=new HashMap<Integer,Marker>();
    _firstId=firstId;
    _nextId=firstId;
  }

  /**
   * Get a marker using its identifier.
   * @param markerId Marker identifier.
   * @return A marker or <code>null</code> if not found.
   */
  public Marker getMarkerById(int markerId)
  {
    return _markers.get(Integer.valueOf(markerId));
  }

  /**
   * Register a marker.
   * @param marker Marker to add.
   */
  public void registerMarker(Marker marker)
  {
    int markerId=marker.getId();
    if (markerId==0)
    {
      markerId=_nextId;
      _nextId++;
      marker.setId(markerId);
    }
    else
    {
      if (markerId>=_nextId)
      {
        _nextId=markerId+1;
      }
    }
    Integer key=Integer.valueOf(markerId);
    _markers.put(key,marker);
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
      MarkersManager allMarkers=MapsIO.loadMarkers(_markersFile);
      for(Marker marker : allMarkers.getAllMarkers())
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
    _nextId=_firstId;
    _markers.clear();
  }

  /**
   * Write to disk.
   */
  public void write()
  {
    List<Marker> markers=new ArrayList<Marker>(_markers.values());
    Collections.sort(markers,new MarkerIdentifierComparator());
    MapXMLWriter.writeMarkersFile(_markersFile,markers,EncodingNames.UTF_8);
  }
}
