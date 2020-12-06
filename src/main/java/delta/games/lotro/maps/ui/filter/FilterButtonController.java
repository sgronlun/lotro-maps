package delta.games.lotro.maps.ui.filter;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.TitledBorder;

import delta.common.ui.swing.GuiFactory;

/**
 * Controller for a filter button that triggers the filter UI.
 * @author DAM
 */
public class FilterButtonController
{
  // UI
  private JPanel _panel;
  private JButton _triggerButton;
  private Popup _popup;
  // Controllers
  private MapFilterPanelController _filterController;

  /**
   * Constructor.
   * @param filterController Filter controller.
   */
  public FilterButtonController(MapFilterPanelController filterController)
  {
    _filterController=filterController;
    build();
  }

  /**
   * Get the button used to trigger the popup for the filter UI.
   * @return A button.
   */
  public JButton getTriggerButton()
  {
    return _triggerButton;
  }

  private void build()
  {
    _panel=buildPanel();
    _triggerButton=GuiFactory.buildButton("Filter...");
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
    JButton closeButton=_filterController.getCategoryChooser().getCloseButton();
    ActionListener alClose=new ActionListener()
    {
      public void actionPerformed(ActionEvent event)
      {
        if (_popup!=null)
        {
          hide();
        }
      }
    };
    closeButton.addActionListener(alClose);
  }

  private void show()
  {
    PopupFactory popupFactory=PopupFactory.getSharedInstance();
    Point p=_triggerButton.getLocationOnScreen();
    int x=p.x;
    int y=p.y+_triggerButton.getHeight();
    _popup=popupFactory.getPopup(_triggerButton,_panel,x,y);
    _popup.show();
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
    JPanel filterPanel=_filterController.getPanel();
    TitledBorder border=GuiFactory.buildTitledBorder("Filter");
    filterPanel.setBorder(border);
    panel.add(filterPanel,BorderLayout.CENTER);
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
    _triggerButton=null;
    _popup=null;
    // Controllers
    if (_filterController!=null)
    {
      _filterController.dispose();
      _filterController=null;
    }
  }
}
