package delta.games.lotro.maps.ui.filter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import delta.common.ui.ImageUtils;
import delta.common.ui.swing.GuiFactory;
import delta.common.utils.NumericTools;
import delta.games.lotro.maps.data.CategoriesManager;
import delta.games.lotro.maps.data.Category;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.comparators.CategoryNameComparator;

/**
 * Controller for a category chooser panel.
 * @author DAM
 */
public class CategoryChooserController
{
  // Data
  private Set<Integer> _selectedCodes;
  private MapsManager _mapsManager;
  // UI
  private JPanel _panel;
  private HashMap<Integer,JCheckBox> _checkboxes;
  // Listeners
  private CategoryFilterUpdateListener _listener;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   */
  public CategoryChooserController(MapsManager mapsManager)
  {
    _mapsManager=mapsManager;
    _selectedCodes=new HashSet<Integer>();
    _checkboxes=new HashMap<Integer,JCheckBox>();
  }

  /**
   * Get the managed panel (build it if necessary).
   * @return The managed panel.
   */
  public JPanel getPanel()
  {
    if (_panel==null)
    {
      _panel=build();
    }
    return _panel;
  }

  /**
   * Get the selected categories.
   * @return the selected categories.
   */
  public Set<Integer> getSelectedCategories()
  {
    return new HashSet<Integer>(_selectedCodes);
  }

  /**
   * Set the selected categories.
   * @param selectedCategories Categories to show.
   */
  public void setSelectedCategories(Set<Integer> selectedCategories)
  {
    // Ensure UI is build
    getPanel();
    // Clear current codes
    _selectedCodes.clear();
    // Then update UI
    for(Integer code : selectedCategories)
    {
      JCheckBox checkbox=_checkboxes.get(code);
      if (checkbox!=null)
      {
        _selectedCodes.add(code);
        checkbox.setSelected(true);
      }
    }
    // Call listeners?
  }

  private JPanel build()
  {
    CategoriesManager categoriesManager=_mapsManager.getCategories();
    List<Category> categories=categoriesManager.getAllSortedByCode();
    Collections.sort(categories,new CategoryNameComparator());
    JPanel panel=GuiFactory.buildBackgroundPanel(new GridBagLayout());
    ActionListener al=new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        String command=event.getActionCommand();
        Integer code=NumericTools.parseInteger(command);
        JCheckBox checkbox=_checkboxes.get(code);
        boolean selected=checkbox.isSelected();
        boolean updated=false;
        if (selected) {
          updated=_selectedCodes.add(code);
        } else {
          updated=_selectedCodes.remove(code);
        }
        if (updated)
        {
          if (_listener!=null)
          {
            _listener.categoryFilterUpdated(CategoryChooserController.this);
          }
        }
      }
    };
    int nbColumns=3;
    int nbItems=categories.size();
    int nbItemsPerColumn=nbItems/nbColumns+(nbItems%nbColumns>0?1:0);
    int currentColumn=0;
    int x=currentColumn*3;
    int y=0;
    for(Category category : categories)
    {
      String iconName=category.getIcon();
      File iconFile=_mapsManager.getIconFile(iconName);
      BufferedImage image=ImageUtils.loadImage(iconFile);
      JLabel icon=GuiFactory.buildIconLabel(new ImageIcon(image));
      JLabel label=GuiFactory.buildLabel(category.getLabel());
      JCheckBox checkbox=GuiFactory.buildCheckbox("");
      checkbox.setActionCommand(String.valueOf(category.getCode()));
      checkbox.addActionListener(al);
      x=currentColumn*3;
      GridBagConstraints c=new GridBagConstraints(x,y,1,1,0,0,GridBagConstraints.WEST,GridBagConstraints.NONE,new Insets(0,0,0,0),0,0);
      panel.add(checkbox,c);
      c.gridx++;
      panel.add(icon,c);
      c.gridx++;
      panel.add(label,c);
      y++;
      if (y==nbItemsPerColumn) {
        y=0;
        currentColumn++;
      }
    }
    return panel;
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
    _mapsManager=null;
  }
}
