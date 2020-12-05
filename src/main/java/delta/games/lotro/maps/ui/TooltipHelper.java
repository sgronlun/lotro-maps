package delta.games.lotro.maps.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.games.lotro.maps.data.MapPoint;
import delta.games.lotro.maps.data.MapPointNameComparator;
import delta.games.lotro.maps.data.Marker;

/**
 * Helper methods for tooltips in maps.
 * @author DAM
 */
public class TooltipHelper
{
  /**
   * Build the tooltip for a collection of points.
   * @param points Points to use.
   * @return the tooltip HTML text (<code>null</code> if no tooltip).
   */
  public static String buildTooltip(List<MapPoint> points)
  {
    if (points.size()==0)
    {
      return null;
    }
    Collections.sort(points,new MapPointNameComparator());
    List<List<MapPoint>> pointsLists=sortPoints(points);
    StringBuilder sb=new StringBuilder();
    sb.append("<html>");
    int count=0;
    for(List<MapPoint> pointsList : pointsLists)
    {
      String label=pointsList.get(0).getLabel();
      if (label!=null)
      {
        if (count>0) sb.append("<br>");
        int nb=pointsList.size();
        if (nb>1) sb.append(nb).append("x ");
        sb.append(label.replace("\n","&nbsp;-&nbsp;"));
        count++;
      }
    }
    sb.append("</html>");
    return sb.toString();
  }

  private static List<List<MapPoint>> sortPoints(List<MapPoint> points)
  {
    List<List<MapPoint>> ret=new ArrayList<List<MapPoint>>();
    int lastDid=0;
    List<MapPoint> currentList=new ArrayList<MapPoint>();
    for(MapPoint point : points)
    {
      if (point instanceof Marker)
      {
        Marker marker=(Marker)point;
        int did=marker.getDid();
        if (did!=lastDid)
        {
          currentList=new ArrayList<MapPoint>();
          ret.add(currentList);
          lastDid=did;
        }
        currentList.add(marker);
      }
      else
      {
        List<MapPoint> links=new ArrayList<MapPoint>();
        links.add(point);
        ret.add(links);
        lastDid=0;
      }
    }
    return ret;
  }
}
