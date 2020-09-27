package delta.games.lotro.maps.ui.filter;

import java.awt.FlowLayout;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import delta.common.ui.swing.GuiFactory;
import delta.common.ui.swing.text.DynamicTextEditionController;
import delta.common.ui.swing.text.TextListener;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.ui.MapCanvas;

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
  private MapCanvas _mapCanvas;

  /**
   * Constructor.
   * @param categoriesManager Categories manager.
   * @param filter Markers filter.
   * @param mapCanvas Associated map canvas.
   */
  public MapFilterPanelController(CategoriesManager categoriesManager, MapMarkersFilter filter, MapCanvas mapCanvas)
  {
    _filter=filter;
    _mapCanvas=mapCanvas;
    _categoryChooser=new CategoryChooserController(categoriesManager);
    initCategoriesFilter(categoriesManager);
  }

  private void initCategoriesFilter(CategoriesManager categoriesManager)
  {
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
    JPanel panel=GuiFactory.buildPanel(new FlowLayout());
    // Categories
    JButton button=buildCategoriesChooser();
    panel.add(button);
    // Label filter
    JPanel containsPanel=buildLabelFilter();
    panel.add(containsPanel);
    // 
    return panel;
  }

  private JButton buildCategoriesChooser()
  {
    CategoryFilterUpdateListener listener=new CategoryFilterUpdateListener()
    {
      public void categoryFilterUpdated(CategoryChooserController controller)
      {
        Set<Integer> selectedCategories=controller.getSelectedCategories();
        _filter.getCategoryFilter().setCategories(selectedCategories);
        filterUpdated();
      }
    };
    _categoryChooser.setListener(listener);
    JButton button=_categoryChooser.getTriggerButton();
    return button;
  }

  private JPanel buildLabelFilter()
  {
    JPanel containsPanel=GuiFactory.buildPanel(new FlowLayout(FlowLayout.LEADING));
    containsPanel.add(GuiFactory.buildLabel("Label:"));
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
    return containsPanel;
  }

  /**
   * Called when the managed filter was updated.
   */
  public void filterUpdated()
  {
    // Repaint the associated map
    _mapCanvas.repaint();
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
