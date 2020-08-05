package delta.games.lotro.maps.ui.layers;

import java.awt.Graphics;
import java.util.List;

import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.MarkersManager;
import delta.games.lotro.maps.ui.MapView;

/**
 * Layer for markers.
 * @author DAM
 */
public class MarkersLayer extends AbstractMarkersLayer
{
  /**
   * Constructor.
   * @param mapsManager Maps manager.
   * @param view Map view.
   */
  public MarkersLayer(MapsManager mapsManager, MapView view)
  {
    super(mapsManager,view);
  }

  @Override
  public List<Marker> getVisibleMarkers()
  {
    MapBundle map=_view.getCurrentMap();
    if (map!=null)
    {
      MarkersManager markersManager=map.getData();
      return markersManager.getMarkers(_filter);
    }
    return null;
  }

  /**
   * Paint the markers.
   * @param g Graphics.
   */
  @Override
  public void paintLayer(Graphics g)
  {
    MapBundle map=_view.getCurrentMap();
    if (map!=null)
    {
      MarkersManager markersManager=map.getData();
      List<Marker> markers=markersManager.getAllMarkers();
      paintMarkers(markers,g);
    }
  }
}
