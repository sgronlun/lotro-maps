package delta.games.lotro.maps.data.markers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.maps.data.GeoBox;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.MarkersManager;
import delta.games.lotro.maps.data.io.MapsIO;
import delta.games.lotro.maps.data.io.xml.MapXMLWriter;
import delta.games.lotro.maps.data.markers.comparators.MarkerIdentifierComparator;

/**
 * Facade to access to all markers.
 * @author DAM
 */
public class GlobalMarkersManager
{
  private Map<Integer,Marker> _markers;
  private boolean _loaded;

  private File _rootDir;

  /**
   * Constructor.
   * @param rootDir Root directory.
   */
  public GlobalMarkersManager(File rootDir)
  {
    _rootDir=rootDir;
    _markers=new HashMap<Integer,Marker>();
    _loaded=false;
  }

  /**
   * Get a marker using its identifier.
   * @param markerId Marker identifier.
   * @return A marker or <code>null</code> if not found.
   */
  public Marker getMarkerById(int markerId)
  {
    ensureLoaded();
    return _markers.get(Integer.valueOf(markerId));
  }

  /**
   * Register a marker.
   * @param marker Marker to add.
   */
  public void registerMarker(Marker marker)
  {
    Integer key=Integer.valueOf(marker.getId());
    _markers.put(key,marker);
  }

  /**
   * Get all the markers found in the given geographic box.
   * @param box Box to use.
   * @return A possibly empty but never <code>null</code> list of markers.
   */
  public List<Marker> getMarkers(GeoBox box)
  {
    ensureLoaded();
    List<Marker> ret=new ArrayList<Marker>();
    for(Marker marker : _markers.values())
    {
      if (box.isInBox(marker.getPosition()))
      {
        ret.add(marker);
      }
    }
    return ret;
  }

  private void ensureLoaded()
  {
    if (_loaded)
    {
      return;
    }
    File markersFile=new File(_rootDir,"markers.xml");
    MarkersManager allMarkers=MapsIO.loadMarkers(markersFile);
    for(Marker marker : allMarkers.getAllMarkers())
    {
      registerMarker(marker);
    }
    _loaded=true;
  }

  /**
   * Write the markers file.
   */
  public void write()
  {
    File markersFile=new File(_rootDir,"markers.xml");
    List<Marker> markers=new ArrayList<Marker>(_markers.values());
    Collections.sort(markers,new MarkerIdentifierComparator());
    MapXMLWriter.writeMarkersFile(markersFile,markers,EncodingNames.UTF_8);
  }
}
