package delta.games.lotro.maps.data.categories.comparators;

import java.util.Comparator;

import delta.games.lotro.maps.data.categories.Category;

/**
 * Comparator for marker categories, using their name.
 * @author DAM
 */
public class CategoryNameComparator implements Comparator<Category>
{
  public int compare(Category o1, Category o2)
  {
    String label1=o1.getName();
    String label2=o2.getName();
    return label1.compareTo(label2);
  }
}
