package delta.games.lotro.maps.ui;

import java.util.Collections;
import java.util.List;

import javax.swing.JComboBox;

import delta.common.ui.swing.combobox.ComboBoxController;
import delta.common.ui.swing.combobox.ComboBoxItem;
import delta.common.ui.swing.combobox.ItemSelectionListener;
import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemap;
import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemapNameComparator;
import delta.games.lotro.maps.data.basemaps.GeoreferencedBasemapsManager;
import delta.games.lotro.maps.ui.navigation.NavigationSupport;

/**
 * Controller for a map chooser.
 * @author DAM
 */
public class MapChooserController
{
  // Data
  private GeoreferencedBasemapsManager _basemapsManager;
  // Controllers
  private NavigationSupport _navigation;
  // UI
  private ComboBoxController<GeoreferencedBasemap> _mapsCombo;

  /**
   * Constructor.
   * @param navigation Navigation support.
   * @param basemapsManager Basemaps manager.
   */
  public MapChooserController(NavigationSupport navigation, GeoreferencedBasemapsManager basemapsManager)
  {
    _navigation=navigation;
    _basemapsManager=basemapsManager;
    _mapsCombo=buildCombo();
  }

  /**
   * Select the given map.
   * @param basemapId Key of the map to set.
   */
  public void selectMap(int basemapId)
  {
    GeoreferencedBasemap mapToShow=_basemapsManager.getMapById(basemapId);
    GeoreferencedBasemap selectedMap=_mapsCombo.getSelectedItem();
    if (selectedMap!=mapToShow)
    {
      _mapsCombo.setSelectedItem(mapToShow);
    }
  }

  private ComboBoxController<GeoreferencedBasemap> buildCombo()
  {
    ComboBoxController<GeoreferencedBasemap> combo=new ComboBoxController<GeoreferencedBasemap>();
    List<GeoreferencedBasemap> maps=_basemapsManager.getBasemaps();
    Collections.sort(maps,new GeoreferencedBasemapNameComparator());
    for(GeoreferencedBasemap map : maps)
    {
      combo.addItem(map,map.getName());
    }
    ItemSelectionListener<GeoreferencedBasemap> listener=new ItemSelectionListener<GeoreferencedBasemap>()
    {
      public void itemSelected(GeoreferencedBasemap item)
      {
        _navigation.requestMap(item.getIdentifier());
      }
    };
    combo.addListener(listener);
    return combo;
  }

  /**
   * Get the managed combobox.
   * @return the managed combobox.
   */
  public JComboBox<ComboBoxItem<GeoreferencedBasemap>> getCombo()
  {
    return _mapsCombo.getComboBox();
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    _basemapsManager=null;
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
