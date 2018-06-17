package delta.games.lotro.maps.ui.layers;

import java.util.Comparator;

/**
 * Comparator for layers, using their priority.
 * @author DAM
 */
public class LayerPriorityComparator implements Comparator<Layer>
{
  @Override
  public int compare(Layer o1, Layer o2)
  {
    int priority1=o1.getPriority();
    int priority2=o2.getPriority();
    return Integer.compare(priority1,priority2);
  }
}
