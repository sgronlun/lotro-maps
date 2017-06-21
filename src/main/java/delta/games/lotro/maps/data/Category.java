package delta.games.lotro.maps.data;

/**
 * @author dm
 */
public class Category
{
  private int _code;
  private String _key;
  private Labels _labels;

  public Category(int code)
  {
    _code=code;
    _labels=new Labels();
  }

  public String getLabel()
  {
    return _labels.getLabel();
  }

  public Labels getLabels()
  {
    return _labels;
  }

  public int getCode()
  {
    return _code;
  }

  public String getKey()
  {
    return _key;
  }

  public void setKey(String key)
  {
    _key=key;
  }

  @Override
  public String toString()
  {
    return _code+": key="+_key+", labels="+_labels;
  }
}
