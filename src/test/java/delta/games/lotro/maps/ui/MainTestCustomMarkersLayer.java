package delta.games.lotro.maps.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import delta.games.lotro.maps.data.GeoPoint;
import delta.games.lotro.maps.data.GeoreferencedBasemap;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.ui.layers.MarkersLayer;
import delta.games.lotro.maps.ui.layers.SimpleMarkersProvider;

/**
 * Test class for the custom markers layer.
 * @author DAM
 */
public class MainTestCustomMarkersLayer
{
  private static List<Marker> getMarkers()
  {
    List<Marker> ret=new ArrayList<Marker>();
    ret.add(buildMarker(699401058,-29.985605,16.890995));
    ret.add(buildMarker(699401059,-29.318632,27.885517));
    ret.add(buildMarker(699401060,-28.923046,26.216347));
    ret.add(buildMarker(699401061,-29.178652,27.277988));
    ret.add(buildMarker(699401062,-28.855879,28.021463));
    ret.add(buildMarker(699401063,-27.723846,22.813301));
    ret.add(buildMarker(699401064,-28.273094,23.743174));
    ret.add(buildMarker(699401065,-27.85659,27.422499));
    ret.add(buildMarker(699401066,-27.61079,28.876045));
    ret.add(buildMarker(699401067,-27.07458,26.423456));
    ret.add(buildMarker(699401068,-26.70619,28.174404));
    ret.add(buildMarker(699401069,-25.611504,21.633038));
    ret.add(buildMarker(699401070,-24.665096,22.29916));
    ret.add(buildMarker(699401071,-23.79952,26.62537));
    ret.add(buildMarker(699401072,-23.222265,20.057323));
    return ret;
  }

  private static Marker buildMarker(int id, double lon, double lat)
  {
    Marker marker=new Marker();
    GeoPoint position=new GeoPoint((float)lon,(float)lat);
    marker.setPosition(position);
    return marker;
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
    String key="268452526"; // Northern Mirkwood
    panel.setMap(key);
    MapCanvas canvas=panel.getCanvas();
    MarkerIconProvider customIconProvider=new CompletedOrNotMarkerIconProvider();
    SimpleMarkersProvider markersProvider=new SimpleMarkersProvider();
    List<Marker> markers=getMarkers();
    markersProvider.setMarkers(markers);
    MarkersLayer custom=new MarkersLayer(customIconProvider,markersProvider);
    canvas.addLayer(custom);
    MapBundle mapBundle=mapsManager.getMapByKey(key);
    GeoreferencedBasemap map=mapBundle.getMap();
    String mapTitle=(map!=null)?map.getName():"?";

    JFrame f=new JFrame();
    f.setTitle(mapTitle);
    f.getContentPane().add(panel.getLayers());
    f.pack();
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
}
