package delta.games.lotro.maps.ui.layers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import delta.common.ui.swing.icons.IconsManager;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapLink;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.ui.MapView;

/**
 * Layer for map links.
 * @author DAM
 */
public class LinksLayer implements Layer
{
  private MapView _view;
  private BufferedImage _gotoIcon;

  /**
   * Constructor.
   * @param view Map view.
   */
  public LinksLayer(MapView view)
  {
    _view=view;
    _gotoIcon=IconsManager.getImage("/resources/icons/goto.png");
  }

  @Override
  public int getPriority()
  {
    return 10;
  }

  @Override
  public List<Marker> getVisibleMarkers()
  {
    return null;
  }

  /**
   * Paint the links.
   * @param g Graphics.
   */
  @Override
  public void paintLayer(Graphics g)
  {
    MapBundle currentMap=_view.getCurrentMap();
    if (currentMap!=null)
    {
      List<MapLink> links=currentMap.getLinks();
      if (links.size()>0)
      {
        for(MapLink link : links)
        {
          paintLink(link,g);
        }
      }
    }
  }

  private void paintLink(MapLink link, Graphics g)
  {
    GeoReference viewReference=_view.getViewReference();
    Dimension pixelPosition=viewReference.geo2pixel(link.getHotPoint());

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
