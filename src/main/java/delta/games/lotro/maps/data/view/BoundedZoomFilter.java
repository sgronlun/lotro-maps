package delta.games.lotro.maps.data.view;

/**
 * Simple zoom filter that uses zoom level bounds.
 * @author DAM
 */
public class BoundedZoomFilter implements ZoomFilter
{
  private Float _min; // Max zoom out
  private Float _max; // Max zoom in

  /**
   * Constructor.
   * @param min Minimum geo to pixel factor (max zoom out).
   * @param max Maximum geo to pixel factor (max zoom in).
   */
  public BoundedZoomFilter(Float min, Float max)
  {
    _min=min;
    _max=max;
  }

  @Override
  public boolean acceptZoomLevel(float geo2pixel)
  {
    if (_min!=null)
    {
      float delta=geo2pixel-_min.floatValue();
      if (delta<-0.001)
      {
        return false;
      }
    }
    if (_max!=null)
    {
      float delta=_max.floatValue()-geo2pixel;
      if (delta<-0.001)
      {
        return false;
      }
    }
    return true;
  }
}
