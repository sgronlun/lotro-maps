package delta.games.lotro.maps.data;

/**
 * Marker category.
 * @author DAM
 */
public class Category
{
  private int _code;
  private String _icon;
  private String _name;

  /**
   * Constructor.
   * @param code Identifying code.
   */
  public Category(int code)
  {
    _code=code;
    _name="";
  }

  /**
   * Get the name for this category.
   * @return a displayable name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name for this category.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    _name=name;
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
    return _code+": icon="+_icon+", name="+_name;
  }
}
