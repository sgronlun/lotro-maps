package delta.games.lotro.maps.ui.filter;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextField;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.text.DynamicTextEditionController;
import delta.common.ui.swing.text.TextListener;
import delta.games.lotro.maps.data.CategoriesManager;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.ui.MapPanelController;

/**
 * Controller for a map filter edition panel.
 * @author DAM
 */
public class MapFilterPanelController
{
  // Data
  private MapMarkersFilter _filter;
  // GUI
  private JPanel _panel;
  private JTextField _contains;
  // Controllers
  private CategoryChooserController _categoryChooser;
  private DynamicTextEditionController _textController;
  private MapPanelController _mapController;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   * @param filter Markers filter.
   * @param mapController Associated map controller.
   */
  public MapFilterPanelController(MapsManager mapsManager, MapMarkersFilter filter, MapPanelController mapController)
  {
    _filter=filter;
    _mapController=mapController;
    _categoryChooser=new CategoryChooserController(mapsManager);
    initCategoriesFilter(mapsManager);
  }

  private void initCategoriesFilter(MapsManager mapsManager)
  {
    CategoriesManager categoriesManager=mapsManager.getCategories();
    Set<Integer> codes=categoriesManager.getCodes();
    _filter.getCategoryFilter().setCategories(codes);
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    if (_panel==null)
    {
      _panel=build();
      setFilter();
      filterUpdated();
    }
    return _panel;
  }

  private void setFilter()
  {
    // Name
    String contains=_filter.getNameFilter().getPattern();
    if (contains!=null)
    {
      _contains.setText(contains);
    }
    // Categories
    Set<Integer> acceptedCategories=_filter.getCategoryFilter().getCategories();
    _categoryChooser.setSelectedCategories(acceptedCategories);
  }

  private JPanel build()
  {
    JPanel panel=GuiFactory.buildPanel(new GridBagLayout());
    // Categories
    {
      CategoryFilterUpdateListener listener=new CategoryFilterUpdateListener()
      {
        public void categoryFilterUpdated(CategoryChooserController controller)
        {
          Set<Integer> selectedCategories=controller.getSelectedCategories();
          _filter.getCategoryFilter().setCategories(selectedCategories);
        }
      };
      _categoryChooser.setListener(listener);
    }
    // Label filter
    {
      JPanel containsPanel=GuiFactory.buildPanel(new FlowLayout(FlowLayout.LEADING));
      containsPanel.add(GuiFactory.buildLabel("Label filter:"));
      _contains=GuiFactory.buildTextField("");
      _contains.setColumns(20);
      containsPanel.add(_contains);
      TextListener listener=new TextListener()
      {
        public void textChanged(String newText)
        {
          if (newText.length()==0) newText=null;
          _filter.getNameFilter().setPattern(newText);
          filterUpdated();
        }
      };
      _textController=new DynamicTextEditionController(_contains,listener);
      GridBagConstraints c=new GridBagConstraints(0,1,2,1,1.0,0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0);
      panel.add(containsPanel,c);
    }
    return panel;
  }

  /**
   * Called when the managed filter was updated.
   */
  public void filterUpdated()
  {
    // Repaint the associated map
    _mapController.getCanvas().repaint();
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // Data
    _filter=null;
    // Controllers
    if (_categoryChooser!=null)
    {
      _categoryChooser.dispose();
      _categoryChooser=null;
    }
    if (_textController!=null)
    {
      _textController.dispose();
      _textController=null;
    }
    // GUI
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
    _contains=null;
  }
}
