package delta.games.lotro.maps.data.displaySelection;

import java.util.HashSet;
import java.util.Set;

/**
 * Display selection for a single category.
 * @author DAM
 */
public class DisplaySelectionForCategory
{
  private int _code;
  private Set<Integer> _visibleDIDs;
  private Set<Integer> _allDIDs;

  /**
   * Constructor.
   * @param code Category code.
   */
  public DisplaySelectionForCategory(int code)
  {
    _code=code;
    _visibleDIDs=new HashSet<Integer>();
    _allDIDs=new HashSet<Integer>();
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
   * Indicates if the given DID is visible or not.
   * @param did Data identifier to use.
   * @return <code>true</code> if visible, <code>false</code> otherwise.
   */
  public boolean isVisible(int did)
  {
    return _visibleDIDs.contains(Integer.valueOf(did));
  }

  /**
   * Add a DID.
   * @param did Data identifier to use.
   * @param visible Initial visibility.
   */
  public void addDID(int did, boolean visible)
  {
    Integer key=Integer.valueOf(did);
    _allDIDs.add(key);
    if (visible)
    {
      _visibleDIDs.add(key);
    }
    else
    {
      _visibleDIDs.remove(key);
    }
  }

  @Override
  public String toString()
  {
    HashSet<Integer> hiddenDIDs=new HashSet<Integer>(_allDIDs);
    hiddenDIDs.removeAll(_visibleDIDs);
    return "Display selection for code: "+_code+" => visible: "+_visibleDIDs+", hidden: "+hiddenDIDs;
  }
}
