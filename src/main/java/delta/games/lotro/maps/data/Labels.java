package delta.games.lotro.maps.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Labels manager.
 * Stores a single label in multiple locales.
 * @author DAM
 */
public class Labels
{
  private HashMap<String,String> _labels;

  /**
   * Constructor.
   */
  public Labels()
  {
    _labels=new HashMap<String,String>();
  }

  /**
   * Set a label for a given locale.
   * @param locale Locale.
   * @param value Label to set.
   */
  public void putLabel(String locale, String value)
  {
    _labels.put(locale,value);
  }

  /**
   * Get the label for the default locale.
   * @return a displayable label.
   */
  public String getLabel()
  {
    String label=_labels.get(LocaleNames.DEFAULT_LOCALE);
    if (label==null)
    {
      label="???";
    }
    return label;
  }

  /**
   * Get the label for a given locale.
   * @param locale Locale to use.
   * @return A label or <code>null</code> if not found.
   */
  public String getLabel(String locale)
  {
    return _labels.get(locale);
  }

  /**
   * Get all supported locales.
   * @return A list of locale identifiers.
   */
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
