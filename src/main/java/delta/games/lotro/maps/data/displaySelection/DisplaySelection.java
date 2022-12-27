package delta.games.lotro.maps.data.displaySelection;

import java.util.HashMap;
import java.util.Map;

/**
 * Display selection.
 * @author DAM
 */
public class DisplaySelection
{
  private Map<Integer,DisplaySelectionForCategory> _displaySelections;

  /**
   * Constructor.
   */
  public DisplaySelection()
  {
    _displaySelections=new HashMap<Integer,DisplaySelectionForCategory>();
  }

  /**
   * Add an element.
   * @param code Category code.
   * @param did Data identifier to use.
   * @param visible Initial visibility.
   */
  public void addElement(int code, int did, boolean visible)
  {
    DisplaySelectionForCategory displaySelection=getDisplaySelectionForCategory(code);
    displaySelection.addDID(did,visible);
  }

  /**
   * Get the display selection for the given category (create it if needed).
   * @param code Category code.
   * @return A display selection for the given category.
   */
  public DisplaySelectionForCategory getDisplaySelectionForCategory(int code)
  {
    Integer codeKey=Integer.valueOf(code);
    DisplaySelectionForCategory displaySelection=_displaySelections.get(codeKey);
    if (displaySelection==null)
    {
      displaySelection=new DisplaySelectionForCategory(code);
      _displaySelections.put(codeKey,displaySelection);
    }
    return displaySelection;
  }

  /**
   * Indicates if a given element is visible or not.
   * @param code Category code.
   * @param did Data identifier.
   * @return <code>true</code> if visible, <code>false</code> otherwise.
   */
  public boolean isVisible(int code, int did)
  {
    DisplaySelectionForCategory displaySelection=_displaySelections.get(Integer.valueOf(code));
    if (displaySelection==null)
    {
      return false;
    }
    return displaySelection.isVisible(did);
  }
}
