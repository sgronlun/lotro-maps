package delta.games.lotro.maps.ui.displaySelection;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.id.Identifiable;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.displaySelection.DisplaySelectionForCategory;

/**
 * Element of the display selection UI.
 * @author DAM
 */
public class DisplaySelectionUIItem implements Identifiable
{
  DisplaySelectionForCategory _displaySelection;
  private Marker _reference;
  private List<Marker> _markers;

  /**
   * Constructor.
   * @param displaySelection Display selection.
   * @param marker Template marker.
   */
  public DisplaySelectionUIItem(DisplaySelectionForCategory displaySelection, Marker marker)
  {
    _displaySelection=displaySelection;
    _reference=marker;
    _markers=new ArrayList<Marker>();
    _markers.add(marker);
  }

  @Override
  public int getIdentifier()
  {
    return getDID();
  }

  /**
   * Add a marker.
   * @param marker Marker to add.
   */
  public void addMarker(Marker marker)
  {
    _markers.add(marker);
  }

  /**
   * Get the DID of the managed markers.
   * @return A DID.
   */
  public int getDID()
  {
    return _reference.getDid();
  }

  /**
   * Get the category code of the managed markers.
   * @return A category code.
   */
  public int getCategoryCode()
  {
    return _reference.getCategoryCode();
  }

  /**
   * Get the label of the managed markers.
   * @return A label.
   */
  public String getLabel()
  {
    return _reference.getLabel();
  }

  /**
   * Get the markers count.
   * @return A markers count.
   */
  public int getMarkersCount()
  {
    return _markers.size();
  }

  /**
   * Indicates if the managed markers are visible or not.
   * @return <code>true</code> if visible, <code>false</code> otherwise.
   */
  public boolean isVisible()
  {
    return _displaySelection.isVisible(getDID());
  }

  /**
   * Set the visibility.
   * @param visible <code>true</code> if visible, <code>false</code> otherwise.
   */
  public void setVisible(boolean visible)
  {
    _displaySelection.setVisible(getDID(),visible);
  }
}

