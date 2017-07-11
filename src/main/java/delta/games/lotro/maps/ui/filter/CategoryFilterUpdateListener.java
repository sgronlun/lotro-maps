package delta.games.lotro.maps.ui.filter;

/**
 * Listener for updates in the category filter.
 * @author DAM
 */
public interface CategoryFilterUpdateListener
{
  /**
   * Called when the category filter managed by this given controller was updated.
   * @param controller Source controller.
   */
  void categoryFilterUpdated(CategoryChooserController controller);
}
