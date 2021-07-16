package delta.games.lotro.maps.ui.layersmgr;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import delta.common.ui.swing.GuiFactory;
import delta.games.lotro.maps.ui.layers.Layer;

/**
 * Controller for a layer display configuration panel.
 * @author DAM
 */
public class LayersDisplayController implements ActionListener
{
  // Data
  private List<Layer> _layers;
  // UI
  private JPanel _panel;
  private JPanel _layersPanel;
  private List<JCheckBox> _checkboxes;
  private JButton _close;
  // Listeners
  private LayersManagerListener _listener;

  /**
   * Constructor.
   * @param layers Layers to show.
   */
  public LayersDisplayController(List<Layer> layers)
  {
    _layers=layers;
    _checkboxes=new ArrayList<JCheckBox>();
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
   * Set the listener for updates.
   * @param listener Listener to set.
   */
  public void setListener(LayersManagerListener listener)
  {
    _listener=listener;
  }

  private void selectAll()
  {
    for(JCheckBox checkbox : _checkboxes)
    {
      checkbox.setSelected(true);
    }
    for(Layer layer : _layers)
    {
      layer.setVisible(true);
    }
    if (_listener!=null)
    {
      _listener.layersManagerUpdated(LayersDisplayController.this);
    }
  }

  private JPanel buildPanel()
  {
    JPanel panel=GuiFactory.buildBackgroundPanel(new BorderLayout());
    _layersPanel=GuiFactory.buildPanel(new GridBagLayout());
    update();
    TitledBorder border=GuiFactory.buildTitledBorder("Layers");
    _layersPanel.setBorder(border);
    panel.add(_layersPanel,BorderLayout.CENTER);
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
    // Close button
    {
      _close=GuiFactory.buildButton("Close");
      panel.add(_close);
    }
    return panel;
  }

  /**
   * Update the contents of the layers panel.
   */
  public void update()
  {
    _layersPanel.removeAll();
    _checkboxes.clear();
    int y=0;
    int index=0;
    for(Layer layer : _layers)
    {
      String layerName=layer.getName();
      JCheckBox checkbox=GuiFactory.buildCheckbox(layerName);
      checkbox.setActionCommand(String.valueOf(index));
      checkbox.setSelected(layer.isVisible());
      checkbox.addActionListener(this);
      _checkboxes.add(checkbox);
      GridBagConstraints c=new GridBagConstraints(0,y,1,1,1,0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0);
      _layersPanel.add(checkbox,c);
      index++;
      y++;
    }
  }

  @Override
  public void actionPerformed(ActionEvent event)
  {
    Object source=event.getSource();
    int index=_checkboxes.indexOf(source);
    JCheckBox checkbox=_checkboxes.get(index);
    boolean selected=checkbox.isSelected();
    _layers.get(index).setVisible(selected);
    if (_listener!=null)
    {
      _listener.layersManagerUpdated(LayersDisplayController.this);
    }
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
    _layers=null;
    // Listeners
    _listener=null;
  }
}
