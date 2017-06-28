package delta.games.lotro.maps.ui;

import java.io.File;
import java.util.List;

import javax.swing.JFrame;

import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapsManager;

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
    for(MapBundle bundle : bundles)
    {
      MapCanvas canvas=new MapCanvas(mapsManager);
      String key=bundle.getKey();
      canvas.setMap(key);
      JFrame f=new JFrame();
      String title=bundle.getLabel();
      f.setTitle(title);
      f.getContentPane().add(canvas);
      f.pack();
      f.setVisible(true);
      f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
  }
}
