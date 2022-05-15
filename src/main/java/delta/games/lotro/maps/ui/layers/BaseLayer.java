package delta.games.lotro.maps.ui.layers;

/**
 * Base class for layers.
 * @author DAM
 */
public abstract class BaseLayer implements Layer
{
  private boolean _visible;
  private String _name;

  /**
   * Constructor.
   */
  protected BaseLayer()
  {
    _visible=true;
    _name="";
  }

  @Override
  public boolean isVisible()
  {
    return _visible;
  }

  /**
   * Set the visible flag.
   * @param visible Value to set.
   */
  public void setVisible(boolean visible)
  {
    _visible=visible;
  }

  @Override
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this layer.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }
}
