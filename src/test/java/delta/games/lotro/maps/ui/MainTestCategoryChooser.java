package delta.games.lotro.maps.ui;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;

import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.ui.filter.CategoryChooserController;

/**
 * Test class for the category chooser.
 * @author DAM
 */
public class MainTestCategoryChooser
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    File rootDir=new File("../lotro-maps-db");
    MapsManager mapsManager=new MapsManager(rootDir);
    mapsManager.load();

    CategoryChooserController controller=new CategoryChooserController(mapsManager);
    JFrame f=new JFrame();
    JButton button=controller.getTriggerButton();
    f.getContentPane().add(button);
    f.pack();
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
}
