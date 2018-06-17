package delta.games.lotro.maps.ui;

import java.io.File;
import java.util.List;

import javax.swing.JFrame;

import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.MarkersManager;
import delta.games.lotro.maps.ui.layers.CustomMarkersLayers;

/**
 * Test class for the custom markers layer.
 * @author DAM
 */
public class MainTestCustomMarkersLayer
{
  private static List<Marker> getMarkers(String mapKey, final String name)
  {
   File rootDir=new File("../lotro-maps-db");
    MapsManager mapsManager=new MapsManager(rootDir);
    mapsManager.load();

    MapBundle map=mapsManager.getMapByKey(mapKey);
    MarkersManager markers=map.getData();
    Filter<Marker> filter=new Filter<Marker>() {

      public boolean accept(Marker item)
      {
        String label=item.getLabel();
        if (label.contains(name))
        {
          return true;
        }
        return false;
      }
    };
    List<Marker> ret=markers.getMarkers(filter);
    return ret;
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    File rootDir=new File("../lotro-maps-db");
    MapsManager mapsManager=new MapsManager(rootDir);
    mapsManager.load();

    MapPanelController panel=new MapPanelController(mapsManager);
    String key="northern_mirkwood";
    panel.setMap(key);
    MapCanvas canvas=panel.getCanvas();
    MarkerIconProvider customIconProvider=new CompletedOrNotMarkerIconProvider();
    CustomMarkersLayers custom=new CustomMarkersLayers(customIconProvider,canvas);
    List<Marker> markers=getMarkers("northern_mirkwood","Dwarf marker");
    custom.setMarkers(markers);
    canvas.addLayer(custom);
    canvas.removeLayer(panel.getMarkersLayer());
    String mapTitle=panel.getCanvas().getMap().getLabel();

    JFrame f=new JFrame();
    f.setTitle(mapTitle);
    f.getContentPane().add(panel.getLayers());
    f.pack();
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
}
