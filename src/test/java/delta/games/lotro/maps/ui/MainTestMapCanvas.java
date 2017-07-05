package delta.games.lotro.maps.ui;

import java.io.File;
import java.util.List;

import javax.swing.JFrame;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.MarkersManager;

/**
 * Test class for the map canvas.
 * @author DAM
 */
public class MainTestMapCanvas
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
    List<MapBundle> bundles=mapsManager.getMaps();

    Filter<Marker> filter=new Filter<Marker>() {

      public boolean accept(Marker item)
      {
        String label=item.getLabel();
        //if (("Ancient weapon".equals(label)) || (label.startsWith("Ranger cache")))
        if ("Ancient weapon".equals(label))
        // Treasure
        //if (item.getCategoryCode()==27)
        {
          return true;
        }
        return false;
      }
    };

    for(MapBundle bundle : bundles)
    {
      MarkersManager markersManager=bundle.getData();
      List<Marker> markers=markersManager.getMarkers(filter);
      if (markers.size()>0)
      {
        MapPanelController panel=new MapPanelController(mapsManager);
        String key=bundle.getKey();
        panel.setMap(key);
        MapCanvas canvas=panel.getCanvas();
        canvas.setFilter(filter);
        canvas.useLabels(true);

        JFrame f=new JFrame();
        String title=bundle.getLabel();
        f.setTitle(title);
        f.getContentPane().add(panel.getLayers());
        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      }
    }
  }
}
