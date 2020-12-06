package delta.games.lotro.maps.ui.filter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import delta.common.ui.ImageUtils;
import delta.common.ui.swing.GuiFactory;
import delta.common.utils.NumericTools;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.categories.Category;
import delta.games.lotro.maps.data.categories.comparators.CategoryNameComparator;

/**
 * Controller for a category chooser panel.
 * @author DAM
 */
public class CategoryChooserController
{
  // Data
  private Set<Integer> _selectedCodes;
  private CategoriesManager _categoriesManager;
  // UI
  private JPanel _panel;
  private HashMap<Integer,JCheckBox> _checkboxes;
  private JButton _close;
  // Listeners
  private CategoryFilterUpdateListener _listener;

  /**
   * Constructor.
   * @param categoriesManager Categories manager.
   */
  public CategoryChooserController(CategoriesManager categoriesManager)
  {
    _categoriesManager=categoriesManager;
    _selectedCodes=new HashSet<Integer>();
    _checkboxes=new HashMap<Integer,JCheckBox>();
    _panel=buildPanel();
  }

  /**
   * Get the managed panel.
   * @return the managed panel.
   */
  public JPanel getPanel()
  {
    return _panel;
  }

  /**
   * Get the managed close button.
   * @return the managed close button.
   */
  public JButton getCloseButton()
  {
    return _close;
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
   * Set the listener for updates.
   * @param listener Listener to set.
   */
  public void setListener(CategoryFilterUpdateListener listener)
  {
    _listener=listener;
  }

  /**
   * Set the selected categories.
   * @param selectedCategories Categories to show.
   */
  public void setSelectedCategories(Set<Integer> selectedCategories)
  {
    // Clear current codes
    _selectedCodes.clear();
    // Then update UI
    for(JCheckBox checkbox : _checkboxes.values())
    {
      checkbox.setSelected(false);
    }
    for(Integer code : selectedCategories)
    {
      JCheckBox checkbox=_checkboxes.get(code);
      if (checkbox!=null)
      {
        _selectedCodes.add(code);
        checkbox.setSelected(true);
      }
    }
  }

  private void selectAll()
  {
    for(Integer code : _checkboxes.keySet())
    {
      JCheckBox checkbox=_checkboxes.get(code);
      _selectedCodes.add(code);
      checkbox.setSelected(true);
    }
    if (_listener!=null)
    {
      _listener.categoryFilterUpdated(CategoryChooserController.this);
    }
  }

  private void deselectAll()
  {
    for(Integer code : _checkboxes.keySet())
    {
      JCheckBox checkbox=_checkboxes.get(code);
      _selectedCodes.remove(code);
      checkbox.setSelected(false);
    }
    if (_listener!=null)
    {
      _listener.categoryFilterUpdated(CategoryChooserController.this);
    }
  }

  private JPanel buildPanel()
  {
    JPanel panel=GuiFactory.buildBackgroundPanel(new BorderLayout());
    JPanel categoriesPanel=buildCategoriesPanel();
    TitledBorder border=GuiFactory.buildTitledBorder("Categories");
    categoriesPanel.setBorder(border);
    panel.add(categoriesPanel,BorderLayout.CENTER);
    JPanel commandPanel=buildCommandPanel();
    panel.add(commandPanel,BorderLayout.SOUTH);
    return panel;
  }

  private JPanel buildCommandPanel()
  {
    JPanel panel=GuiFactory.buildPanel(new FlowLayout(FlowLayout.TRAILING));
    // Select all button
    {
      JButton selectAllButton=GuiFactory.buildButton("Select all");
      ActionListener al=new ActionListener()
      {
        public void actionPerformed(ActionEvent event)
        {
          selectAll();
        }
      };
      selectAllButton.addActionListener(al);
      panel.add(selectAllButton);
    }
    // Deselect all button
    {
      JButton deselectAllButton=GuiFactory.buildButton("Deselect all");
      ActionListener al=new ActionListener()
      {
        public void actionPerformed(ActionEvent event)
        {
          deselectAll();
        }
      };
      deselectAllButton.addActionListener(al);
      panel.add(deselectAllButton);
    }
    // Close button
    {
      _close=GuiFactory.buildButton("Close");
      panel.add(_close);
    }
    return panel;
  }

  private JPanel buildCategoriesPanel()
  {
    List<Category> categories=_categoriesManager.getAllSortedByCode();
    Collections.sort(categories,new CategoryNameComparator());
    JPanel panel=GuiFactory.buildPanel(new GridBagLayout());
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
      File iconFile=_categoriesManager.getIconFile(category);
      BufferedImage image=null;
      if (iconFile.exists())
      {
        image=ImageUtils.loadImage(iconFile);
      }
      if (image==null)
      {
        continue;
      }
      JLabel icon=GuiFactory.buildIconLabel(new ImageIcon(image));
      JLabel label=GuiFactory.buildLabel(category.getName());
      JCheckBox checkbox=GuiFactory.buildCheckbox("");
      checkbox.setActionCommand(String.valueOf(category.getCode()));
      checkbox.addActionListener(al);
      _checkboxes.put(Integer.valueOf(category.getCode()),checkbox);
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
    // UI
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
    _checkboxes=null;
    _close=null;
    // Data
    _categoriesManager=null;
    // Listeners
    _listener=null;
  }
}
