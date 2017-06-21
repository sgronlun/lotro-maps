package delta.games.lotro.maps.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author dm
 */
public class Labels
{
  private HashMap<String,String> _labels;

  public Labels()
  {
    _labels=new HashMap<String,String>();
  }

  public void putLabel(String locale, String value)
  {
    _labels.put(locale,value);
  }

  public String getLabel()
  {
    String label=_labels.get(LocaleNames.DEFAULT_LOCALE);
    if (label==null)
    {
      label="???";
    }
    return label;
  }

  public String getLabel(String locale)
  {
    return _labels.get(locale);
  }

  public List<String> getLocales()
  {
    List<String> locales=new ArrayList<String>(_labels.keySet());
    Collections.sort(locales);
    return locales;
  }

  @Override
  public String toString()
  {
    return _labels.toString();
  }
}
