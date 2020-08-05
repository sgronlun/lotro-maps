package delta.games.lotro.maps.ui.layers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.MarkersManager;
import delta.games.lotro.maps.ui.MapView;

/**
 * Layer for markers.
 * @author DAM
 */
public class AreaMarkersLayer extends AbstractMarkersLayer
{
  private List<Marker> _markers;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   * @param view Map view.
   */
  public AreaMarkersLayer(MapsManager mapsManager, MapView view)
  {
    super(mapsManager,view);
    _markers=new ArrayList<Marker>();
  }

  /**
   * Set the markers to use.
   * @param markers Markers to use.
   */
  public void setMarkers(List<Marker> markers)
  {
    _markers.clear();
    _markers.addAll(markers);
  }

  @Override
  public List<Marker> getVisibleMarkers()
  {
    return MarkersManager.getFilteredMarkers(_filter,_markers);
  }

  /**
   * Paint the markers.
   * @param g Graphics.
   */
  @Override
  public void paintLayer(Graphics g)
  {
    paintMarkers(_markers,g);
  }
}
