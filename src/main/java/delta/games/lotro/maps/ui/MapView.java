package delta.games.lotro.maps.ui;

import delta.games.lotro.maps.data.GeoBox;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapsManager;

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
   * Get the current map bundle.
   * @return the current map.
   */
  MapBundle getCurrentMap();

  /**
   * Get the maps manager.
   * @return the maps manager.
   */
  MapsManager getMapsManager();
}
