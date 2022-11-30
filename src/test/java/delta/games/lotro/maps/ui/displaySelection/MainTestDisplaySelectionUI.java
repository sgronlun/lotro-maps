package delta.games.lotro.maps.ui.displaySelection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import delta.common.ui.swing.GuiFactory;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.displaySelection.DisplaySelection;
import delta.games.lotro.maps.data.markers.MarkersFinder;
import delta.games.lotro.maps.ui.displaySelection.table.DisplaySelectionCategoryPanelController;
import delta.games.lotro.maps.ui.displaySelection.table.DisplaySelectionCategoryTableController;

/**
 * Test for the display selection UI.
 * @author DAM
 */
public class MainTestDisplaySelectionUI
{
  private void doIt()
  {
    File rootDir=new File("../lotro-maps-db");
    MapsManager mapsManager=new MapsManager(rootDir);
    MarkersFinder finder=mapsManager.getMarkersFinder();
    //int dungeonID=1879048295; // Thorin' Hall
    //int dungeonID=1879418771; 
    //List<Marker> markers=finder.findMarkers(dungeonID,0);

    int[] ids=new int[] {
        1879063921, // Vale of Thrain
        1879063922, // Low Lands
        1879063923, // Haudh Lin
        1879063924, // Falathlorn
        1879063925, // Sarnúr
        1879063926, // Rath Teraig
        1879063928, // Falathlorn
        1879063929, // Celondim
        1879063930, // Limael's Vineyard
        1879063931, // Kheledûl
        1879206150, // Low Lands
    };
    List<Marker> markers=new ArrayList<Marker>();
    for(int id : ids)
    {
      markers.addAll(finder.findMarkers(id,0));
    }

    DisplaySelection selection=new DisplaySelection();
    DisplaySelectionDetails details=DisplaySelectionDetailsBuilder.build(selection,markers);
    JTabbedPane tab=GuiFactory.buildTabbedPane();
    CategoriesManager categoriesMgr=mapsManager.getCategories();
    for(Integer category : details.getCategories())
    {
      DisplaySelectionDetailsForCategory displaySelectionForCategory=details.getDisplaySelection(category.intValue());
      DisplaySelectionCategoryTableController tb=new DisplaySelectionCategoryTableController(displaySelectionForCategory,null);
      DisplaySelectionCategoryPanelController panel=new DisplaySelectionCategoryPanelController(null,tb);
      String categoryName=categoriesMgr.getByCode(category.intValue()).getName();
      tab.add(categoryName,panel.getPanel());
    }
    JFrame f=new JFrame();
    f.add(tab);
    f.pack();
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    new MainTestDisplaySelectionUI().doIt();
  }
}
