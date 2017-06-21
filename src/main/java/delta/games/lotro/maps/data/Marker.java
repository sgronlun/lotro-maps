package delta.games.lotro.maps.data;

/**
 * @author dm
 */
public class Marker
{
  private GeoPoint _position;
  private Labels _labels;
  private Category _category;
  private String _comment;

  public Marker()
  {
    _labels=new Labels();
  }

  /**
   * @return the position
   */
  public GeoPoint getPosition()
  {
    return _position;
  }

  /**
   * @param position the position to set
   */
  public void setPosition(GeoPoint position)
  {
    _position=position;
  }

  /**
   * @return the labels
   */
  public Labels getLabels()
  {
    return _labels;
  }

  public String getLabel()
  {
    return _labels.getLabel();
  }

  /**
   * @return the category
   */
  public Category getCategory()
  {
    return _category;
  }

  public int getCategoryCode()
  {
    return _category!=null?_category.getCode():0;
  }
  /**
   * @param category the category to set
   */
  public void setCategory(Category category)
  {
    _category=category;
  }

  /**
   * @return the comment
   */
  public String getComment()
  {
    return _comment;
  }

  /**
   * @param comment the comment to set
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
