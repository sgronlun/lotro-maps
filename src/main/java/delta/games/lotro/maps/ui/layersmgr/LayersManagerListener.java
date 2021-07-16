package delta.games.lotro.maps.ui.layersmgr;

/**
 * Listener for updates in the category filter.
 * @author DAM
 */
public interface LayersManagerListener
{
  /**
   * Called when the layers manager configuration was updated.
   * @param controller Source controller.
   */
  void layersManagerUpdated(LayersDisplayController controller);
}
