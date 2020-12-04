package delta.games.lotro.maps.ui.layers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import delta.common.ui.swing.icons.IconsManager;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.MapPoint;
import delta.games.lotro.maps.data.links.MapLink;
import delta.games.lotro.maps.data.links.MapLinkType;
import delta.games.lotro.maps.ui.MapView;

/**
 * Layer for map links.
 * @author DAM
 */
public class LinksLayer implements VectorLayer
{
  private BufferedImage _mapIcon;
  private BufferedImage _dungeonIcon;
  private List<MapLink> _links;

  /**
   * Constructor.
   */
  public LinksLayer()
  {
    _mapIcon=IconsManager.getImage("/resources/icons/link.png");
    _dungeonIcon=IconsManager.getImage("/resources/icons/dungeonDoor.png");
    _links=new ArrayList<MapLink>();
  }

  @Override
  public int getPriority()
  {
    return 10;
  }

  @Override
  public List<? extends MapPoint> getVisiblePoints()
  {
    return _links;
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
   * @param view Parent view.
   * @param g Graphics.
   */
  @Override
  public void paintLayer(MapView view, Graphics g)
  {
    if (_links.size()>0)
    {
      for(MapLink link : _links)
      {
        paintLink(view,link,g);
      }
    }
  }

  private void paintLink(MapView view, MapLink link, Graphics g)
  {
    GeoReference viewReference=view.getViewReference();
    Dimension pixelPosition=viewReference.geo2pixel(link.getPosition());

    int x=pixelPosition.width;
    int y=pixelPosition.height;
    BufferedImage icon=(link.getType()==MapLinkType.TO_PARCHMENT_MAP)?_mapIcon:_dungeonIcon;
    if (icon!=null)
    {
      int width=icon.getWidth();
      int height=icon.getHeight();
      g.drawImage(icon,x-width/2,y-height/2,null);
    }
    else
    {
      g.setColor(Color.RED);
      g.fillRect(x-10,y-10,20,20);
    }
  }

}
