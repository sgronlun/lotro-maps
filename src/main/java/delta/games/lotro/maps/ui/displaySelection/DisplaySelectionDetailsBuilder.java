package delta.games.lotro.maps.ui.displaySelection;

import java.util.Collection;

import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.displaySelection.DisplaySelection;

/**
 * Builder for display selection details.
 * @author DAM
 */
public class DisplaySelectionDetailsBuilder
{
  /**
   * Build a display selection details from a collection of markers.
   * @param displaySelection Display selection.
   * @param markers Markers to use.
   * @return A display selection details.
   */
  public static DisplaySelectionDetails build(DisplaySelection displaySelection, Collection<Marker> markers)
  {
    DisplaySelectionDetails ret=new DisplaySelectionDetails(displaySelection);
    for(Marker marker : markers)
    {
      ret.addMarker(marker);
    }
    return ret;
  }
}
