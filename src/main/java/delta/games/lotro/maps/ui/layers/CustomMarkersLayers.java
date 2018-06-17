package delta.games.lotro.maps.ui.layers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import delta.common.ui.swing.draw.HaloPainter;
import delta.games.lotro.maps.data.GeoBox;
import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.ui.MapView;
import delta.games.lotro.maps.ui.MarkerIconProvider;

/**
 * Layer for custom markers.
 * @author DAM
 */
public class CustomMarkersLayers implements Layer
{
  private MapView _view;
  private List<Marker> _markers;
  private boolean _useLabels;
  private MarkerIconProvider _iconProvider;

  /**
   * Constructor.
   * @param iconProvider Icons provider.
   * @param view Map view.
   */
  public CustomMarkersLayers(MarkerIconProvider iconProvider, MapView view)
  {
    _iconProvider=iconProvider;
    _view=view;
    _useLabels=false;
    _markers=new ArrayList<Marker>();
  }

  @Override
  public int getPriority()
  {
    return 50;
  }

  @Override
  public List<Marker> getVisibleMarkers()
  {
    return _markers;
  }

  /**
   * Set the markers to show.
   * @param markers Markers to show.
   */
  public void setMarkers(List<Marker> markers)
  {
    _markers.clear();
    _markers.addAll(markers);
  }

  /**
   * Set the flag to use labels or not.
   * @param useLabels <code>true</code> to display labels, <code>false</code> to hide them.
   */
  public void useLabels(boolean useLabels)
  {
    _useLabels=useLabels;
  }

  /**
   * Paint the markers.
   * @param g Graphics.
   */
  @Override
  public void paintLayer(Graphics g)
  {
    for(Marker marker : _markers)
    {
      paintMarker(marker,g);
    }
  }

  private void paintMarker(Marker marker, Graphics g)
  {
    GeoReference viewReference=_view.getViewReference();
    Dimension pixelPosition=viewReference.geo2pixel(marker.getPosition());

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
        HaloPainter.drawStringWithHalo(g,x+10,y,label,Color.WHITE,Color.BLACK);
      }
    }
  }

  /**
   * Find the markers at a given pixel location.
   * @param x Horizontal position.
   * @param y Vertical position.
   * @param sensibility Pixel sensibility.
   * @return A possibly empty, but not <code>null</code> list of markers.
   */
  public List<Marker> findMarkersAtLocation(int x, int y, int sensibility)
  {
    GeoReference viewReference=_view.getViewReference();
    GeoPoint topLeft=viewReference.pixel2geo(new Dimension(x-sensibility,y-sensibility));
    GeoPoint bottomRight=viewReference.pixel2geo(new Dimension(x+sensibility,y+sensibility));
    GeoBox box=new GeoBox(topLeft,bottomRight);
    List<Marker> markers=new ArrayList<Marker>();
    for(Marker marker : _markers)
    {
      if (box.isInBox(marker.getPosition()))
      {
        markers.add(marker);
      }
    }
    return markers;
  }
}
