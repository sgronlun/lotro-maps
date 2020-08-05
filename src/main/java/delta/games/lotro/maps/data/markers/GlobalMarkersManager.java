package delta.games.lotro.maps.data.markers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.maps.data.GeoBox;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.MarkersManager;
import delta.games.lotro.maps.data.io.MapsIO;

/**
 * Facade to access to all markers.
 * @author DAM
 */
public class GlobalMarkersManager
{
  private MarkersManager _allMarkers;

  private File _rootDir;

  /**
   * Constructor.
   * @param rootDir Root directory.
   */
  public GlobalMarkersManager(File rootDir)
  {
    _rootDir=rootDir;
  }

  /**
   * Get the root directory for markers.
   * @return a directory.
   */
  public File getRootDir()
  {
    return _rootDir;
  }

  private MarkersManager getMarkers()
  {
    if (_allMarkers==null)
    {
      File markersFile=new File(_rootDir,"markers.xml");
      _allMarkers=MapsIO.loadMarkers(markersFile);
    }
    return _allMarkers;
  }

  /**
   * Get all the markers found in the given geographic box.
   * @param box Box to use.
   * @return A possibly empty but never <code>null</code> list of markers.
   */
  public List<Marker> getMarkers(GeoBox box)
  {
    List<Marker> ret=new ArrayList<Marker>();
    for(Marker marker : getMarkers().getAllMarkers())
    {
      if (box.isInBox(marker.getPosition()))
      {
        ret.add(marker);
      }
    }
    return ret;
  }
}
