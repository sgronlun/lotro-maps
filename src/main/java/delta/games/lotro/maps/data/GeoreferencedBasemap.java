package delta.games.lotro.maps.data;

import java.io.File;

/**
 * Georeferenced base map.
 * @author DAM
 */
public class GeoreferencedBasemap
{
  private int _key;
  private String _name;
  private GeoReference _geoRef;
  private File _imageFile;

  /**
   * Constructor.
   * @param key Identifying key for this basemap.
   */
  public GeoreferencedBasemap(int key)
  {
    _key=key;
    _name="";
  }

  /**
   * Get the identifying key for this basemap.
   * @return an identifying key.
   */
  public int getKey()
  {
    return _key;
  }

  /**
   * Get the name of this basemap.
   * @return a map name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name for this basemap.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the geographic reference.
   * @return the geographic reference.
   */
  public GeoReference getGeoReference()
  {
    return _geoRef;
  }

  /**
   * Set the geographic reference.
   * @param geoReference Geographic reference to set.
   */
  public void setGeoReference(GeoReference geoReference)
  {
    _geoRef=geoReference;
  }

  /**
   * Get the image file.
   * @return the image file.
   */
  public File getImageFile()
  {
    return _imageFile;
  }

  /**
   * Set the image file.
   * @param imageFile Image file.
   */
  public void setImageFile(File imageFile)
  {
    _imageFile=imageFile;
  }

  @Override
  public String toString()
  {
    return "Basemap: key="+_key+", name="+_name+", reference=" + _geoRef + ", file="+_imageFile;
  }
}
