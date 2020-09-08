package delta.games.lotro.maps.ui;

import java.io.File;
import java.util.List;

import javax.swing.JFrame;

import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.categories.CategoriesManager;
import delta.games.lotro.maps.data.markers.MarkersFinder;
import delta.games.lotro.maps.ui.layers.MarkersLayer;
import delta.games.lotro.maps.ui.layers.SimpleMarkersProvider;

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

    int breeMapId=268437716;
    int breaArea=1879064015;
    MapBundle bundle=mapsManager.getMapByKey(String.valueOf(breeMapId));
    MapPanelController panel=new MapPanelController(mapsManager);
    String key=bundle.getKey();
    panel.setMap(key);
    MapCanvas canvas=panel.getCanvas();
    CategoriesManager categoriesManager=mapsManager.getCategories();
    MarkerIconProvider iconsProvider=new DefaultMarkerIconsProvider(categoriesManager);
    SimpleMarkersProvider markersProvider=new SimpleMarkersProvider();
    MarkersFinder finder=mapsManager.getMarkersFinder();
    List<Marker> markers=finder.findMarkers(breaArea,0);
    markersProvider.setMarkers(markers);
    MarkersLayer markersLayer=new MarkersLayer(canvas,iconsProvider,markersProvider);
    canvas.addLayer(markersLayer);
    markersLayer.useLabels(true);

    JFrame f=new JFrame();
    String title=bundle.getName();
    f.setTitle(title);
    f.getContentPane().add(panel.getLayers());
    f.pack();
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
}
