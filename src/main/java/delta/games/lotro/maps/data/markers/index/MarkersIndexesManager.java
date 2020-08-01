package delta.games.lotro.maps.data.markers.index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manager for all marker indexes.
 * @author DAM
 */
public class MarkersIndexesManager
{
  private Map<Integer,DIDMarkersIndex> _didIndexes;

  /**
   * Constructor.
   */
  public MarkersIndexesManager()
  {
    _didIndexes=new HashMap<Integer,DIDMarkersIndex>();
  }

  /**
   * Get an index for a DID.
   * @param did DID to use.
   * @param createIfNecessary Indicates if the index shall be created if
   * it does not exist.
   * @return An index or <code>null</code>.
   */
  public DIDMarkersIndex getDidIndex(int did, boolean createIfNecessary)
  {
    Integer key=Integer.valueOf(did);
    DIDMarkersIndex index=_didIndexes.get(key);
    if ((index==null) && (createIfNecessary))
    {
      index=new DIDMarkersIndex(did);
      _didIndexes.put(key,index);
    }
    return index;
  }

  /**
   * Get the managed DIDs.
   * @return An ordered list of DIDs.
   */
  public List<Integer> getDids()
  {
    List<Integer> dids=new ArrayList<Integer>(_didIndexes.keySet());
    Collections.sort(dids);
    return dids;
  }
}
