package delta.games.lotro.maps.ui.displaySelection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.common.utils.id.IdentifiableComparator;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.displaySelection.DisplaySelectionForCategory;

/**
 * Details for a single category.
 * @author DAM
 */
public class DisplaySelectionDetailsForCategory
{
  private DisplaySelectionForCategory _displaySelection;
  private int _code;
  private Map<Integer,DisplaySelectionUIItem> _details;

  /**
   * Constructor.
   * @param displaySelection Display selection.
   * @param code Category code.
   */
  public DisplaySelectionDetailsForCategory(DisplaySelectionForCategory displaySelection, int code)
  {
    _displaySelection=displaySelection;
    _code=code;
    _details=new HashMap<Integer,DisplaySelectionUIItem>();
  }

  /**
   * Get the managed category code.
   * @return A category code.
   */
  public int getCode()
  {
    return _code;
  }

  /**
   * Get the UI item for single DID.
   * @param did Data identifier.
   * @return Some details or <code>null</code> if not found.
   */
  public DisplaySelectionUIItem getDetails(int did)
  {
    Integer key=Integer.valueOf(did);
    return _details.get(key);
  }

  /**
   * Get the number of managed items.
   * @return an items count.
   */
  public int getItemsCount()
  {
    return _details.size();
  }

  /**
   * Get the managed items.
   * @return a list of managed items, sorted by DID.
   */
  public List<DisplaySelectionUIItem> getItems()
  {
    List<DisplaySelectionUIItem> ret=new ArrayList<DisplaySelectionUIItem>();
    ret.addAll(_details.values());
    Collections.sort(ret,new IdentifiableComparator<DisplaySelectionUIItem>());
    return ret;
  }

  /**
   * Add a marker
   * @param marker Marker to add. 
   */
  public void addMarker(Marker marker)
  {
    int did=marker.getDid();
    Integer key=Integer.valueOf(did);
    DisplaySelectionUIItem details=_details.get(key);
    if (details==null)
    {
      details=new DisplaySelectionUIItem(_displaySelection,marker);
      _details.put(key,details);
      _displaySelection.setVisible(did,true);
    }
    else
    {
      details.addMarker(marker);
    }
  }
}
