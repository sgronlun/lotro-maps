package delta.games.lotro.maps.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.windows.DefaultWindowController;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.ui.filter.MapFilterPanelController;
import delta.games.lotro.maps.ui.filter.MapMarkersFilter;

/**
 * Controller for a map window.
 * @author DAM
 */
public class MapWindowController extends DefaultWindowController
{
  private MapPanelController _mapPanel;
  private MapFilterPanelController _filter;
  private MapChooserController _mapChooser;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   */
  public MapWindowController(MapsManager mapsManager)
  {
    _mapPanel=new MapPanelController(mapsManager);
    MapMarkersFilter filter=new MapMarkersFilter();
    _filter=new MapFilterPanelController(mapsManager,filter,_mapPanel);
    _mapChooser=new MapChooserController(mapsManager,_mapPanel);
    _mapPanel.getCanvas().setFilter(filter);
    _mapPanel.getCanvas().useLabels(true);
  }

  @Override
  protected JFrame build()
  {
    JFrame frame=super.build();
    _mapPanel.mapChangeRequest("angmar");
    frame.setTitle("Middle Earth maps");
    frame.setLocation(100,100);
    frame.pack();
    frame.setResizable(false);
    frame.getContentPane().setBackground(GuiFactory.getBackgroundColor());
    return frame;
  }

  @Override
  protected JComponent buildContents()
  {
    JPanel panel=GuiFactory.buildBackgroundPanel(new BorderLayout());
    // Top
    JPanel topPanel=GuiFactory.buildBackgroundPanel(new FlowLayout());
    // Map chooser
    JComboBox mapChooserCombo=_mapChooser.getCombo();
    topPanel.add(mapChooserCombo);
    // Markers filter
    JPanel filterPanel=_filter.getPanel();
    topPanel.add(filterPanel);
    panel.add(topPanel,BorderLayout.NORTH);
    // Center
    MapCanvas mapCanvas=_mapPanel.getCanvas();
    panel.add(mapCanvas,BorderLayout.CENTER);
    return panel;
  }

  /**
   * Release all managed resources.
   */
  @Override
  public void dispose()
  {
    if (_filter!=null)
    {
      _filter.dispose();
      _filter=null;
    }
    if (_mapChooser==null)
    {
      _mapChooser.dispose();
      _mapChooser=null;
    }
    if (_mapPanel!=null)
    {
      _mapPanel.dispose();
      _mapPanel=null;
    }
    super.dispose();
  }
}
