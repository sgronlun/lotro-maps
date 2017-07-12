package delta.games.lotro.maps.ui;

import java.util.Collections;
import java.util.List;

import javax.swing.JComboBox;

import delta.common.ui.swing.combobox.ComboBoxController;
import delta.common.ui.swing.combobox.ItemSelectionListener;
import delta.common.utils.ListenersManager;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapBundleNameComparator;
import delta.games.lotro.maps.data.MapsManager;

/**
 * Controller for a map chooser.
 * @author DAM
 */
public class MapChooserController
{
  // Data
  private MapsManager _mapsManager;
  // Controllers
  private MapPanelController _mapPanel;
  // UI
  private ComboBoxController<MapBundle> _mapsCombo;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   * @param mapPanel Map panel controller.
   */
  public MapChooserController(MapsManager mapsManager, MapPanelController mapPanel)
  {
    _mapsManager=mapsManager;
    _mapPanel=mapPanel;
    _mapsCombo=buildCombo();
    initMapPanel();
  }

  private void initMapPanel()
  {
    ListenersManager<NavigationListener> listeners=_mapPanel.getListenersManager();
    NavigationListener listener=new NavigationListener()
    {
      public void mapChangeRequest(String key)
      {
        MapBundle bundle=_mapsManager.getMapByKey(key);
        _mapsCombo.selectItem(bundle);
      }
    };
    listeners.addListener(listener);
  }

  private ComboBoxController<MapBundle> buildCombo()
  {
    ComboBoxController<MapBundle> combo=new ComboBoxController<MapBundle>();
    List<MapBundle> maps=_mapsManager.getMaps();
    Collections.sort(maps,new MapBundleNameComparator());
    for(MapBundle map : maps)
    {
      combo.addItem(map,map.getLabel());
    }
    ItemSelectionListener<MapBundle> listener=new ItemSelectionListener<MapBundle>()
    {
      public void itemSelected(MapBundle item)
      {
        _mapPanel.mapChangeRequest(item.getKey());
      }
    };
    combo.addListener(listener);
    return combo;
  }

  /**
   * Get the managed combobox.
   * @return the managed combobox.
   */
  public JComboBox getCombo()
  {
    return _mapsCombo.getComboBox();
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    if (_mapsCombo!=null)
    {
      _mapsCombo.dispose();
    }
    _mapsManager=null;
  }
}
