package delta.games.lotro.maps.ui;

import delta.games.lotro.maps.data.GeoBox;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.ui.navigation.MapViewDefinition;

/**
 * Interface of a map view.
 * @author DAM
 */
public interface MapView
{
  /**
   * Get the current view reference.
   * @return the current view reference.
   */
  GeoReference getViewReference();

  /**
   * Get geographic bounds for this view.
   * @return some geographic bounds.
   */
  GeoBox getGeoBounds();

  /**
   * Get the view definition for this map view.
   * @return a view definition or <code>null</code> if no basemap.
   */
  MapViewDefinition getMapViewDefinition();
}
