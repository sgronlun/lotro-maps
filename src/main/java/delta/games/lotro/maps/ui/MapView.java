package delta.games.lotro.maps.ui;

import delta.games.lotro.maps.data.GeoBox;
import delta.games.lotro.maps.data.GeoReference;

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
}
