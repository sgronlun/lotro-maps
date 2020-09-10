package delta.games.lotro.maps.ui.layers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import delta.common.ui.swing.icons.IconsManager;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.MapLink;
import delta.games.lotro.maps.data.MapPoint;
import delta.games.lotro.maps.ui.MapView;

/**
 * Layer for map links.
 * @author DAM
 */
public class LinksLayer implements VectorLayer
{
  private MapView _view;
  private BufferedImage _gotoIcon;
  private List<MapLink> _links;

  /**
   * Constructor.
   * @param view Map view.
   */
  public LinksLayer(MapView view)
  {
    _view=view;
    _gotoIcon=IconsManager.getImage("/resources/icons/goto.png");
    _links=new ArrayList<MapLink>();
  }

  @Override
  public int getPriority()
  {
    return 10;
  }

  @Override
  public List<MapPoint> getVisiblePoints()
  {
    return null;
  }

  /**
   * Set the links to show.
   * @param links Links to show.
   */
  public void setLinks(List<MapLink> links)
  {
    _links.clear();
    _links.addAll(links);
  }

  /**
   * Paint the links.
   * @param g Graphics.
   */
  @Override
  public void paintLayer(Graphics g)
  {
    if (_links.size()>0)
    {
      for(MapLink link : _links)
      {
        paintLink(link,g);
      }
    }
  }

  private void paintLink(MapLink link, Graphics g)
  {
    GeoReference viewReference=_view.getViewReference();
    Dimension pixelPosition=viewReference.geo2pixel(link.getPosition());

    int x=pixelPosition.width;
    int y=pixelPosition.height;
    if (_gotoIcon!=null)
    {
      int width=_gotoIcon.getWidth();
      int height=_gotoIcon.getHeight();
      g.drawImage(_gotoIcon,x-width/2,y-height/2,null);
    }
    else
    {
      g.setColor(Color.RED);
      g.fillRect(x-10,y-10,20,20);
    }
  }

}
