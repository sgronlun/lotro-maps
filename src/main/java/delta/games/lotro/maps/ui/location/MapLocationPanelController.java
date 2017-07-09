package delta.games.lotro.maps.ui.location;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import delta.common.ui.swing.draw.HaloPainter;
import delta.games.lotro.maps.data.GeoPoint;

/**
 * Controller for a panel that display a map location.
 * @author DAM
 */
public class MapLocationPanelController implements MapLocationListener
{
  private LocationDisplayPanel _panel;

  /**
   * Default font.
   */
  public static final Font DEFAULT_FONT=new Font(Font.DIALOG,Font.BOLD,12);

  /**
   * Constructor.
   */
  public MapLocationPanelController()
  {
    _panel=new LocationDisplayPanel();
    mapLocationUpdated(new GeoPoint(0,0));
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    return _panel;
  }

  public void mapLocationUpdated(GeoPoint point)
  {
    String location=point.asString();
    _panel.setLocation(location);
    _panel.repaint();
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
  }

  private class LocationDisplayPanel extends JPanel
  {
    private String _location;

    /**
     * Constructor.
     */
    public LocationDisplayPanel()
    {
      _location="";
      setOpaque(false);
    }

    /**
     * Set the location string to display.
     * @param location A location.
     */
    public void setLocation(String location)
    {
      _location=location;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      g.setFont(DEFAULT_FONT);
      FontMetrics metrics=g.getFontMetrics();
      Rectangle2D r=metrics.getStringBounds(_location,g);
      int x=5;
      int y=(int)(getHeight()-20-r.getY());
      HaloPainter.drawStringWithHalo(g,x,y,_location,Color.WHITE,Color.BLACK);
    }
  }
}
