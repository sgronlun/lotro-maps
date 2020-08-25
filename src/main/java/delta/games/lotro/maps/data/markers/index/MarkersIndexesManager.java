package delta.games.lotro.maps.data.markers.index;

import java.io.File;
import java.util.HashMap;
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
   * @param createIfNecessary Indicates if the index shall be created if
   * it does not exist.
   * @return An index or <code>null</code>.
   */
  public MarkersIndex getDidIndex(int did, boolean createIfNecessary)
  {
    Integer key=Integer.valueOf(did);
    MarkersIndex index=_didIndexes.get(key);
    if ((index==null) && (createIfNecessary))
    {
      File from=getFileForDidIndex(did);
      if (from.exists())
      {
        index=loadIndex(from);
      }
      else
      {
        index=new MarkersIndex(did);
      }
      _didIndexes.put(key,index);
    }
    return index;
  }

  /**
   * Get an index for a content layer.
   * @param contentLayerId Content layer to use.
   * @param createIfNecessary Indicates if the index shall be created if
   * it does not exist.
   * @return An index or <code>null</code>.
   */
  public MarkersIndex getContentLayerIndex(int contentLayerId, boolean createIfNecessary)
  {
    Integer key=Integer.valueOf(contentLayerId);
    MarkersIndex index=_contentLayerIndexes.get(key);
    if ((index==null) && (createIfNecessary))
    {
      File from=getFileForContentLayerIndex(contentLayerId);
      if (from.exists())
      {
        index=loadIndex(from);
      }
      else
      {
        index=new MarkersIndex(contentLayerId);
      }
      _contentLayerIndexes.put(key,index);
    }
    return index;
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

  private MarkersIndex loadIndex(File from)
  {
    MarkersIndexXMLParser parser=new MarkersIndexXMLParser();
    MarkersIndex index=parser.parseXML(from);
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
