package delta.games.lotro.maps.ui.layersmgr;

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
 * Controller for a layers button that triggers the layers manager UI.
 * @author DAM
 */
public class LayersButtonController
{
  // UI
  private JPanel _panel;
  private JButton _triggerButton;
  private Popup _popup;
  // Controllers
  private LayersDisplayController _layersDisplayController;

  /**
   * Constructor.
   * @param layersDisplayController Layers display controller.
   */
  public LayersButtonController(LayersDisplayController layersDisplayController)
  {
    _layersDisplayController=layersDisplayController;
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
    _triggerButton=GuiFactory.buildButton("Layers...");
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
    JButton closeButton=_layersDisplayController.getCloseButton();
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
    _layersDisplayController.update();
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
    JPanel filterPanel=_layersDisplayController.getPanel();
    TitledBorder border=GuiFactory.buildTitledBorder("Layers");
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
    if (_layersDisplayController!=null)
    {
      _layersDisplayController.dispose();
      _layersDisplayController=null;
    }
  }
}
