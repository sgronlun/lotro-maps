package delta.games.lotro.maps.data.markers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.ThriftyIntSet;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.markers.index.MarkersIndex;
import delta.games.lotro.maps.data.markers.index.MarkersIndexesManager;

/**
 * Finder for markers.
 * @author DAM
 */
public class MarkersFinder
{
  private GlobalMarkersManager _markersMgr;
  private MarkersIndexesManager _indexsMgr;

  /**
   * Constructor.
   * @param rootDir Root maps directory.
   * @param markersMgr Markers manager.
   */
  public MarkersFinder(File rootDir, GlobalMarkersManager markersMgr)
  {
    _markersMgr=markersMgr;
    _indexsMgr=new MarkersIndexesManager(rootDir);
  }

  /**
   * Get all the markers in the given landblock, using an option content layer filter.
   * @param region Region.
   * @param blockX Landblock X.
   * @param blockY Landblock Y.
   * @param contentLayer Optional content layer filter.
   * @return A possibly empty, but never <code>null</code> list of markers.
   */
  public List<Marker> findMarkersForBlock(int region, int blockX, int blockY, Integer contentLayer)
  {
    List<Marker> ret=new ArrayList<Marker>();
    LandblockMarkersManager landblockMarkersManager=_markersMgr.getLandblockMarkersManager(region,blockX,blockY);
    if (landblockMarkersManager!=null)
    {
      List<Marker> landblockMarkers=landblockMarkersManager.getMarkers();
      if (contentLayer!=null)
      {
        MarkersIndex markersIndex=_indexsMgr.getContentLayerIndex(contentLayer.intValue());
        ThriftyIntSet markerIds=markersIndex.getMarkers();
        for(Marker marker : landblockMarkers)
        {
          if (markerIds.contains(marker.getId()))
          {
            ret.add(marker);
          }
        }
      }
      else
      {
        ret.addAll(landblockMarkers);
      }
    }
    return ret;
  }

  /**
   * Find the markers for the given zone and DID.
   * @param did Data identifier.
   * @param zoneId Zone identifier (area or dungeon).
   * @return A possibly empty but never <code>null</code> list of markers.
   */
  public List<Marker> findMarkersForDid(int did, int zoneId)
  {
    MarkersIndex zoneIndex=_indexsMgr.getDidIndex(zoneId);
    ThriftyIntSet zoneMarkers=zoneIndex.getMarkers();
    MarkersIndex didIndex=_indexsMgr.getDidIndex(did);
    ThriftyIntSet didMarkers=didIndex.getMarkers();
    ThriftyIntSet markersToGet=new ThriftyIntSet(zoneMarkers);
    markersToGet.retainAll(didMarkers);
    return getMarkers(markersToGet);
  }

  /**
   * Find the markers for the given zone and content layer.
   * @param zoneId Zone identifier (area or dungeon).
   * @param contentLayer Content layer (0 for world).
   * @return A possibly empty but never <code>null</code> list of markers.
   */
  public List<Marker> findMarkers(int zoneId, int contentLayer)
  {
    MarkersIndex zoneIndex=_indexsMgr.getDidIndex(zoneId);
    ThriftyIntSet zoneMarkers=zoneIndex.getMarkers();
    MarkersIndex contentLayerIndex=_indexsMgr.getContentLayerIndex(contentLayer);
    ThriftyIntSet contentLayerMarkers=contentLayerIndex.getMarkers();
    ThriftyIntSet markersToGet=new ThriftyIntSet(zoneMarkers);
    markersToGet.retainAll(contentLayerMarkers);
    return getMarkers(markersToGet);
  }

  /**
   * Find the markers for the given content layer.
   * @param contentLayer Content layer (0 for world).
   * @return A possibly empty but never <code>null</code> list of markers.
   */
  public List<Marker> findMarkersForContentLayer(int contentLayer)
  {
    MarkersIndex contentLayerIndex=_indexsMgr.getContentLayerIndex(contentLayer);
    ThriftyIntSet contentLayerMarkers=contentLayerIndex.getMarkers();
    return getMarkers(contentLayerMarkers);
  }

  private List<Marker> getMarkers(ThriftyIntSet markerIds)
  {
    List<Marker> ret=new ArrayList<Marker>();
    for(int markerId : markerIds.getValues())
    {
      Marker marker=_markersMgr.getMarkerById(markerId);
      if (marker!=null)
      {
        ret.add(marker);
      }
    }
    return ret;
  }
}
