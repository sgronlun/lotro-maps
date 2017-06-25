package delta.games.lotro.maps.data;

/**
 * Marker category.
 * @author DAM
 */
public class Category
{
  private int _code;
  private String _icon;
  private Labels _labels;

  /**
   * Constructor.
   * @param code Identifying code.
   */
  public Category(int code)
  {
    _code=code;
    _labels=new Labels();
  }

  /**
   * Get the label for this category.
   * @return a displayable label.
   */
  public String getLabel()
  {
    return _labels.getLabel();
  }

  /**
   * Get the labels manager for this category.
   * @return a labels manager.
   */
  public Labels getLabels()
  {
    return _labels;
  }

  /**
   * Get the identifying code for this category.
   * @return a code.
   */
  public int getCode()
  {
    return _code;
  }

  /**
   * Get the icon name for this category.
   * @return An icon name.
   */
  public String getIcon()
  {
    return _icon;
  }

  /**
   * Set the icon name for this category.
   * @param icon Icon name to set.
   */
  public void setIcon(String icon)
  {
    _icon=icon;
  }

  @Override
  public String toString()
  {
    return _code+": icon="+_icon+", labels="+_labels;
  }
}
