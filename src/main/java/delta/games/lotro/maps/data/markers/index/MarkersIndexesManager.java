package delta.games.lotro.maps.data.markers.index;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import delta.common.utils.text.EncodingNames;
import delta.games.lotro.maps.data.markers.index.io.xml.MarkersIndexXMLParser;
import delta.games.lotro.maps.data.markers.index.io.xml.MarkersIndexXMLWriter;

/**
 * Manager for all marker indexes.
 * @author DAM
 */
public class MarkersIndexesManager
{
  private File _rootDir;
  private Map<Integer,MarkersIndex> _didIndexes;
  private Map<Integer,MarkersIndex> _contentLayerIndexes;

  /**
   * Constructor.
   * @param rootDir Root directory.
   */
  public MarkersIndexesManager(File rootDir)
  {
    _rootDir=rootDir;
    _didIndexes=new HashMap<Integer,MarkersIndex>();
    _contentLayerIndexes=new HashMap<Integer,MarkersIndex>();
  }

  /**
   * Get an index for a DID.
   * @param did DID to use.
   * @return An index or <code>null</code>.
   */
  public MarkersIndex getDidIndex(int did)
  {
    Integer key=Integer.valueOf(did);
    MarkersIndex index=_didIndexes.get(key);
    if (index==null)
    {
      File from=getFileForDidIndex(did);
      if (from.exists())
      {
        index=loadIndex(from,did);
      }
      else
      {
        index=new MarkersIndex(did,new HashSet<Integer>());
      }
      _didIndexes.put(key,index);
    }
    return index;
  }

  /**
   * Get an index for a content layer.
   * @param contentLayerId Content layer to use.
   * @return An index or <code>null</code>.
   */
  public MarkersIndex getContentLayerIndex(int contentLayerId)
  {
    Integer key=Integer.valueOf(contentLayerId);
    MarkersIndex index=_contentLayerIndexes.get(key);
    if (index==null)
    {
      File from=getFileForContentLayerIndex(contentLayerId);
      if (from.exists())
      {
        index=loadIndex(from,contentLayerId);
      }
      else
      {
        index=new MarkersIndex(contentLayerId,new HashSet<Integer>());
      }
      _contentLayerIndexes.put(key,index);
    }
    return index;
  }

  /**
   * Set a DID index.
   * @param index Index to set.
   */
  public void setDidIndex(MarkersIndex index)
  {
    _didIndexes.put(Integer.valueOf(index.getKey()),index);
  }

  /**
   * Set a content layer index.
   * @param index Index to set.
   */
  public void setContentLayerIndex(MarkersIndex index)
  {
    _contentLayerIndexes.put(Integer.valueOf(index.getKey()),index);
  }

  /**
   * Write the managed indexes.
   */
  public void writeIndexes()
  {
    MarkersIndexXMLWriter writer=new MarkersIndexXMLWriter();
    // DID indexes
    for(Map.Entry<Integer,MarkersIndex> entry : _didIndexes.entrySet())
    {
      int did=entry.getKey().intValue();
      MarkersIndex index=entry.getValue();
      File to=getFileForDidIndex(did);
      writer.write(to,index,EncodingNames.UTF_8);
    }
    // Content layers indexes
    for(Map.Entry<Integer,MarkersIndex> entry : _contentLayerIndexes.entrySet())
    {
      int contentLayerId=entry.getKey().intValue();
      MarkersIndex index=entry.getValue();
      File to=getFileForContentLayerIndex(contentLayerId);
      writer.write(to,index,EncodingNames.UTF_8);
    }
  }

  private MarkersIndex loadIndex(File from, int key)
  {
    MarkersIndexXMLParser parser=new MarkersIndexXMLParser();
    MarkersIndex index=parser.parseXML(from,key);
    return index;
  }

  private File getFileForDidIndex(int did)
  {
    File indexsDir=new File(_rootDir,"indexes");
    File didIndexsDir=new File(indexsDir,"did");
    return new File(didIndexsDir,did+".xml");
  }

 private File getFileForContentLayerIndex(int contentLayerId)
 {
   File indexsDir=new File(_rootDir,"indexes");
   File layerIndexsDir=new File(indexsDir,"layers");
   return new File(layerIndexsDir,contentLayerId+".xml");
 }
}
