package delta.games.lotro.maps.ui.layers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import delta.common.ui.swing.draw.HaloPainter;
import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.MapPoint;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.markers.MapPointsUtils;
import delta.games.lotro.maps.ui.MapView;
import delta.games.lotro.maps.ui.MarkerIconProvider;

/**
 * Layer for markers.
 * @author DAM
 */
public class MarkersLayer implements VectorLayer
{
  private Filter<Marker> _filter;
  private boolean _useLabels;
  private int _priority;
  private MarkerIconProvider _iconProvider;
  private MarkersProvider _markersProvider;

  /**
   * Constructor.
   * @param iconProvider Icon provider.
   * @param markersProvider Markers provider.
   */
  public MarkersLayer(MarkerIconProvider iconProvider, MarkersProvider markersProvider)
  {
    _filter=null;
    _useLabels=false;
    _priority=50;
    _iconProvider=iconProvider;
    _markersProvider=markersProvider;
  }

  @Override
  public int getPriority()
  {
    return _priority;
  }

  /**
   * Set a filter.
   * @param filter Filter to set or <code>null</code> to remove it.
   */
  public void setFilter(Filter<Marker> filter)
  {
    _filter=filter;
  }

  /**
   * Set the flag to use labels or not.
   * @param useLabels <code>true</code> to display labels, <code>false</code> to hide them.
   */
  public void useLabels(boolean useLabels)
  {
    _useLabels=useLabels;
  }

  @Override
  public List<? extends MapPoint> getVisiblePoints()
  {
    List<Marker> markers=getMarkers();
    List<MapPoint> ret=MapPointsUtils.getFilteredMarkers(_filter,markers);
    return ret;
  }

  private List<Marker> getMarkers()
  {
    List<Marker> markers=null;
    if (_markersProvider!=null)
    {
      markers=_markersProvider.getMarkers();
    }
    return markers;
  }

  /**
   * Paint the markers.
   * @param view Parent view.
   * @param g Graphics.
   */
  @Override
  public void paintLayer(MapView view, Graphics g)
  {
    List<Marker> markers=getMarkers();
    paintMarkers(view,markers,g);
  }

  private void paintMarkers(MapView view, List<Marker> markers, Graphics g)
  {
    if ((markers==null) || (markers.size()==0))
    {
      return;
    }
    for(Marker marker : markers)
    {
      boolean ok=((_filter==null)||(_filter.accept(marker)));
      if (ok)
      {
        paintMarker(view,marker,g);
      }
    }
  }

  private void paintMarker(MapView view, Marker marker, Graphics g)
  {
    GeoReference viewReference=view.getViewReference();
    Dimension viewSize=view.getViewSize();
    Dimension pixelPosition=viewReference.geo2pixel(marker.getPosition());
    if ((pixelPosition.width<0)||(pixelPosition.height<0)||
        (pixelPosition.width>viewSize.width)||(pixelPosition.height>viewSize.height))
    {
      // Point is out of view bounds, do not paint!
      return;
    }

    // Grab icon
    BufferedImage image=null;
    if (_iconProvider!=null)
    {
      image=_iconProvider.getImage(marker);
    }
    int x=pixelPosition.width;
    int y=pixelPosition.height;
    if (image!=null)
    {
      int width=image.getWidth();
      int height=image.getHeight();
      g.drawImage(image,x-width/2,y-height/2,null);
    }
    // Label
    if (_useLabels)
    {
      String label=marker.getLabel();
      if ((label!=null) && (label.length()>0))
      {
        int index=label.indexOf('\n');
        if (index==-1)
        {
          HaloPainter.drawStringWithHalo(g,x+10,y,label,Color.WHITE,Color.BLACK);
        }
        else
        {
          String[] lines=label.split("\n");
          HaloPainter.drawStringsWithHalo(g,x+10,y,lines,Color.WHITE,Color.BLACK);
        }
      }
    }
  }
}
