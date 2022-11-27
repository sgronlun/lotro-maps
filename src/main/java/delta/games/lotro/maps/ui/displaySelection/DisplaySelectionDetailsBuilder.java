package delta.games.lotro.maps.ui.displaySelection;

import java.util.Collection;

import delta.games.lotro.maps.data.Marker;

/**
 * Builder for display selection details.
 * @author DAM
 */
public class DisplaySelectionDetailsBuilder
{
  /**
   * Build a display selection details from a collection of markers.
   * @param markers Markers to use.
   * @return A display selection details.
   */
  public static DisplaySelectionDetails build(Collection<Marker> markers)
  {
    DisplaySelectionDetails ret=new DisplaySelectionDetails();
    for(Marker marker : markers)
    {
      ret.addMarker(marker);
    }
    return ret;
  }
}
