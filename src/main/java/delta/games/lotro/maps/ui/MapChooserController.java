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

/**
 * Controller for a map chooser.
 * @author DAM
 */
public class MapChooserController
{
  // Data
  private MapsManager _mapsManager;
  // Controllers
  private MapWindowController _parent;
  // UI
  private ComboBoxController<MapBundle> _mapsCombo;

  /**
   * Constructor.
   * @param parent Parent window.
   * @param mapsManager Maps manager.
   */
  public MapChooserController(MapWindowController parent, MapsManager mapsManager)
  {
    _parent=parent;
    _mapsManager=mapsManager;
    _mapsCombo=buildCombo();
  }

  /**
   * Select the given map.
   * @param key Key of the map to set.
   */
  public void selectMap(String key)
  {
    MapBundle bundle=_mapsManager.getMapByKey(key);
    _mapsCombo.selectItem(bundle);
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
        _parent.mapChangeRequest(item.getKey());
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
    _parent=null;
    // UI
    if (_mapsCombo!=null)
    {
      _mapsCombo.dispose();
      _mapsCombo=null;
    }
  }
}
