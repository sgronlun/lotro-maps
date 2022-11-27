package delta.games.lotro.maps.ui.displaySelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.games.lotro.maps.data.Marker;

/**
 * Access point for display selection details.
 * @author DAM
 */
public class DisplaySelectionDetails
{
  private Map<Integer,DisplaySelectionDetailsForCategory> _details;

  /**
   * Constructor.
   */
  public DisplaySelectionDetails()
  {
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
    DisplaySelectionDetailsForCategory displaySelection=_details.get(codeKey);
    if (displaySelection==null)
    {
      displaySelection=new DisplaySelectionDetailsForCategory(code);
      _details.put(codeKey,displaySelection);
    }
    displaySelection.addMarker(marker);
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
}
