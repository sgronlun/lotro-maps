package delta.games.lotro.maps.ui.displaySelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.displaySelection.DisplaySelection;
import delta.games.lotro.maps.data.displaySelection.DisplaySelectionForCategory;

/**
 * Access point for display selection details.
 * @author DAM
 */
public class DisplaySelectionDetails
{
  private DisplaySelection _displaySelection;
  private Map<Integer,DisplaySelectionDetailsForCategory> _details;

  /**
   * Constructor.
   * @param displaySelection Display selection.
   */
  public DisplaySelectionDetails(DisplaySelection displaySelection)
  {
    _displaySelection=displaySelection;
    _details=new HashMap<Integer,DisplaySelectionDetailsForCategory>();
  }

  /**
   * Add a marker.
   * @param marker Marker to add.
   */
  public void addMarker(Marker marker)
  {
    int code=marker.getCategoryCode();
    Integer codeKey=Integer.valueOf(code);
    DisplaySelectionDetailsForCategory displaySelectionDetails=_details.get(codeKey);
    if (displaySelectionDetails==null)
    {
      DisplaySelectionForCategory displaySelection=_displaySelection.getDisplaySelectionForCategory(code);
      displaySelectionDetails=new DisplaySelectionDetailsForCategory(displaySelection,code);
      _details.put(codeKey,displaySelectionDetails);
    }
    displaySelectionDetails.addMarker(marker);
  }

  /**
   * Get the managed categories.
   * @return A sorted list of category codes.
   */
  public List<Integer> getCategories()
  {
    List<Integer> ret=new ArrayList<Integer>(_details.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get the display selection for a single category.
   * @param code Category code.
   * @return A display selection or <code>null</code> if not found.
   */
  public DisplaySelectionDetailsForCategory getDisplaySelection(int code)
  {
    return _details.get(Integer.valueOf(code));
  }
}
