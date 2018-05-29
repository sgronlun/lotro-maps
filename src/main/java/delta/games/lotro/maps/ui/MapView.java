package delta.games.lotro.maps.ui;

import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.MapBundle;

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
   * Get the current map bundle.
   * @return the current map.
   */
  MapBundle getCurrentMap();
}
