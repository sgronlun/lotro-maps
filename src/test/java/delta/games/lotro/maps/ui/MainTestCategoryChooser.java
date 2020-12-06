package delta.games.lotro.maps.ui;

import java.io.File;

import javax.swing.JFrame;

import delta.games.lotro.maps.data.categories.CategoriesManager;
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
    File from=new File("../lotro-maps-db/categories");
    CategoriesManager categoriesManager=new CategoriesManager(from);
    CategoryChooserController controller=new CategoryChooserController(categoriesManager);
    JFrame f=new JFrame();
    f.getContentPane().add(controller.getPanel());
    f.pack();
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
}
