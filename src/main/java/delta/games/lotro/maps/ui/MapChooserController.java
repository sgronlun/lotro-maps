package delta.games.lotro.maps.ui;

import java.util.Collections;
import java.util.List;

import javax.swing.JComboBox;

import delta.common.ui.swing.combobox.ComboBoxController;
import delta.common.ui.swing.combobox.ComboBoxItem;
import delta.common.ui.swing.combobox.ItemSelectionListener;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapBundleNameComparator;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.ui.navigation.NavigationSupport;

/**
 * Controller for a map chooser.
 * @author DAM
 */
public class MapChooserController
{
  // Data
  private MapsManager _mapsManager;
  // Controllers
  private NavigationSupport _navigation;
  // UI
  private ComboBoxController<MapBundle> _mapsCombo;

  /**
   * Constructor.
   * @param navigation Navigation support.
   * @param mapsManager Maps manager.
   */
  public MapChooserController(NavigationSupport navigation, MapsManager mapsManager)
  {
    _navigation=navigation;
    _mapsManager=mapsManager;
    _mapsCombo=buildCombo();
  }

  /**
   * Select the given map.
   * @param key Key of the map to set.
   */
  public void selectMap(int key)
  {
    MapBundle bundle=_mapsManager.getMapByKey(key);
    MapBundle selectedBundle=_mapsCombo.getSelectedItem();
    if (selectedBundle!=bundle)
    {
      _mapsCombo.setSelectedItem(bundle);
    }
  }

  private ComboBoxController<MapBundle> buildCombo()
  {
    ComboBoxController<MapBundle> combo=new ComboBoxController<MapBundle>();
    List<MapBundle> maps=_mapsManager.getMaps();
    Collections.sort(maps,new MapBundleNameComparator());
    for(MapBundle map : maps)
    {
      combo.addItem(map,map.getName());
    }
    ItemSelectionListener<MapBundle> listener=new ItemSelectionListener<MapBundle>()
    {
      public void itemSelected(MapBundle item)
      {
        _navigation.requestMap(item.getKey());
      }
    };
    combo.addListener(listener);
    return combo;
  }

  /**
   * Get the managed combobox.
   * @return the managed combobox.
   */
  public JComboBox<ComboBoxItem<MapBundle>> getCombo()
  {
    return _mapsCombo.getComboBox();
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    _mapsManager=null;
    // Controllers
    _navigation=null;
    // UI
    if (_mapsCombo!=null)
    {
      _mapsCombo.dispose();
      _mapsCombo=null;
    }
  }
}
