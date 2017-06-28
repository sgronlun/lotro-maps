package delta.games.lotro.maps.data;

/**
 * Marker.
 * @author DAM
 */
public class Marker
{
  private GeoPoint _position;
  private Labels _labels;
  private Category _category;
  private String _comment;

  /**
   * Constructor.
   */
  public Marker()
  {
    _labels=new Labels();
  }

  /**
   * Get the position of this marker.
   * @return a geographic position or <code>null</code> if not set.
   */
  public GeoPoint getPosition()
  {
    return _position;
  }

  /**
   * Set the position of this marker.
   * @param position the position to set (may be <code>null</code>).
   */
  public void setPosition(GeoPoint position)
  {
    _position=position;
  }

  /**
   * Get the labels for this marker.
   * @return a labels manager.
   */
  public Labels getLabels()
  {
    return _labels;
  }

  /**
   * Get the default label for this marker.
   * @return a label.
   */
  public String getLabel()
  {
    return _labels.getLabel();
  }

  /**
   * Get the category of this marker.
   * @return a category or <code>null</code> if not set.
   */
  public Category getCategory()
  {
    return _category;
  }

  /**
   * Get the category code for this marker.
   * @return a category code (<code>0</code> as default).
   */
  public int getCategoryCode()
  {
    return _category!=null?_category.getCode():0;
  }

  /**
   * Set the category for this marker.
   * @param category the category to set (may be <code>null</code>).
   */
  public void setCategory(Category category)
  {
    _category=category;
  }

  /**
   * Get the comment for this marker.
   * @return a comment or <code>null</code> if not set.
   */
  public String getComment()
  {
    return _comment;
  }

  /**
   * Set the comment for this marker.
   * @param comment the comment to set (may be <code>null</code>).
   */
  public void setComment(String comment)
  {
    _comment=comment;
  }

  @Override
  public String toString()
  {
    String label=_labels.getLabel();
    String category=(_category!=null)?_category.getLabel():"?";
    return label+" @"+_position+", category="+category+", comment="+_comment;
  }
}
