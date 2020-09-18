package delta.games.lotro.maps.ui.navigation;

import java.awt.Dimension;

import delta.games.lotro.maps.data.GeoReference;

/**
 * Map view definition.
 * @author DAM
 */
public class MapViewDefinition
{
  private int _mapKey;
  private GeoReference _viewReference;
  private Dimension _size;

  /**
   * Constructor.
   * @param mapKey Map identifier.
   * @param viewReference View reference (<code>null</code> to use default map reference).
   * @param size Size of view (<code>null</code> to use default size).
   */
  public MapViewDefinition(int mapKey, GeoReference viewReference, Dimension size)
  {
    _mapKey=mapKey;
    _viewReference=viewReference;
    _size=size;
  }

  /**
   * Get the map identifier.
   * @return a map identifier.
   */
  public int getMapKey()
  {
    return _mapKey;
  }

  /**
   * Get the georeference of the view.
   * @return a georeference or <code>null</code>.
   */
  public GeoReference getViewReference()
  {
    return _viewReference;
  }

  /**
   * Get the size of the view.
   * @return a size or <code>null</code>.
   */
  public Dimension getDimension()
  {
    return _size;
  }

  @Override
  public String toString()
  {
    return "Map view: key="+_mapKey+", view reference="+_viewReference+", size="+_size;
  }
}
