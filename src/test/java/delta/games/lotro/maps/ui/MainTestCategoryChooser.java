package delta.games.lotro.maps.ui;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
    JPanel panel=controller.getPanel();
    f.getContentPane().add(panel);
    f.pack();
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
}
