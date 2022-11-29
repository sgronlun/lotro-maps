package delta.games.lotro.maps.ui.displaySelection;

import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import delta.common.ui.swing.GuiFactory;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.displaySelection.DisplaySelection;
import delta.games.lotro.maps.data.markers.MarkersFinder;
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
    List<Marker> markers=finder.findMarkers(1879048295,0);
    DisplaySelection selection=new DisplaySelection();
    DisplaySelectionDetails details=DisplaySelectionDetailsBuilder.build(selection,markers);
    JTabbedPane tab=GuiFactory.buildTabbedPane();
    for(Integer category : details.getCategories())
    {
      DisplaySelectionDetailsForCategory displaySelectionForCategory=details.getDisplaySelection(category.intValue());
      DisplaySelectionCategoryTableController tb=new DisplaySelectionCategoryTableController(displaySelectionForCategory,null);
      tab.add(category.toString(),tb.getTable());
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
