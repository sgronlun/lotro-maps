package delta.games.lotro.maps.ui.filter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
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
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.TitledBorder;

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
  private JButton _triggerButton;
  private Popup _popup;
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
    build();
  }

  /**
   * Get the button used to trigger the popup for choosing categories.
   * @return A button.
   */
  public JButton getTriggerButton()
  {
    return _triggerButton;
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

  private void build()
  {
    _panel=buildPanel();
    _triggerButton=GuiFactory.buildButton("Categories...");
    ActionListener al=new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        if (_popup!=null)
        {
          hide();
        }
        else
        {
          show();
        }
      }
    };
    _triggerButton.addActionListener(al);
  }

  private void show()
  {
    PopupFactory popupFactory=PopupFactory.getSharedInstance();
    Point p=_triggerButton.getLocationOnScreen();
    int x=p.x;
    int y=p.y+_triggerButton.getHeight()+5;
    _popup=popupFactory.getPopup(_triggerButton,_panel,x,y);
    _popup.show();
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

  private void hide()
  {
    if (_popup!=null)
    {
      _popup.hide();
      _popup=null;
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
      JButton closeButton=GuiFactory.buildButton("Close");
      ActionListener al=new ActionListener()
      {
        public void actionPerformed(ActionEvent event)
        {
          hide();
        }
      };
      closeButton.addActionListener(al);
      panel.add(closeButton);
    }
    return panel;
  }

  private JPanel buildCategoriesPanel()
  {
    CategoriesManager categoriesManager=_mapsManager.getCategories();
    List<Category> categories=categoriesManager.getAllSortedByCode();
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
      String iconName=category.getIcon();
      File iconFile=_mapsManager.getIconFile(iconName);
      BufferedImage image=ImageUtils.loadImage(iconFile);
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
    _triggerButton=null;
    _popup=null;
    // Data
    _mapsManager=null;
    // Listeners
    _listener=null;
  }
}
