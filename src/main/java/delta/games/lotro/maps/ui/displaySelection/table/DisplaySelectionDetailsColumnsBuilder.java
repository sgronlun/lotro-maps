package delta.games.lotro.maps.ui.displaySelection.table;

import java.util.ArrayList;
import java.util.List;

import delta.common.ui.swing.tables.CellDataProvider;
import delta.common.ui.swing.tables.CellDataUpdater;
import delta.common.ui.swing.tables.DefaultTableColumnController;
import delta.common.ui.swing.tables.TableColumnController;
import delta.common.ui.swing.tables.panel.FilterUpdateListener;
import delta.games.lotro.maps.ui.displaySelection.DisplaySelectionUIItem;

/**
 * Builds column definitions for display selection UI items.
 * @author DAM
 */
public class DisplaySelectionDetailsColumnsBuilder
{
  /**
   * Build the columns to show the attributes of a display selection item.
   * @param listener Filter update listener.
   * @return a list of columns.
   */
  public static List<TableColumnController<DisplaySelectionUIItem,?>> buildColumns(FilterUpdateListener listener)
  {
    List<TableColumnController<DisplaySelectionUIItem,?>> ret=new ArrayList<TableColumnController<DisplaySelectionUIItem,?>>();
    // Visibility
    ret.add(buildVisbilityColumn(listener));
    // Name
    ret.add(buildNameColumn());
    // Count
    ret.add(buildCountColumn());
    return ret;
  }

  /**
   * Build a column to show/edit the visibility.
   * @param listener Listener for updates.
   * @return a column.
   */
  private static TableColumnController<DisplaySelectionUIItem,?> buildVisbilityColumn(final FilterUpdateListener listener)
  {
    CellDataProvider<DisplaySelectionUIItem,Boolean> visibleCell=new CellDataProvider<DisplaySelectionUIItem,Boolean>()
    {
      @Override
      public Boolean getData(DisplaySelectionUIItem item)
      {
        return Boolean.valueOf(item.isVisible());
      }
    };
    DefaultTableColumnController<DisplaySelectionUIItem,Boolean> visibleColumn=new DefaultTableColumnController<DisplaySelectionUIItem,Boolean>(DisplaySelectionDetailsColumnIds.VISIBLE.name(),"Visible",Boolean.class,visibleCell);
    visibleColumn.setWidthSpecs(30,30,30);
    visibleColumn.setEditable(true);
    // Updater
    CellDataUpdater<DisplaySelectionUIItem> updater=new CellDataUpdater<DisplaySelectionUIItem>()
    {
      @Override
      public void setData(DisplaySelectionUIItem item, Object value)
      {
        // Update visibility
        boolean visible=((Boolean)value).booleanValue();
        item.setVisible(visible);
        // Broadcast filter change
        if (listener!=null)
        {
          listener.filterUpdated();
        }
      }
    };
    visibleColumn.setValueUpdater(updater);
    return visibleColumn;
  }

  private static TableColumnController<DisplaySelectionUIItem,?> buildNameColumn()
  {
    CellDataProvider<DisplaySelectionUIItem,String> nameCell=new CellDataProvider<DisplaySelectionUIItem,String>()
    {
      @Override
      public String getData(DisplaySelectionUIItem item)
      {
        return item.getLabel();
      }
    };
    DefaultTableColumnController<DisplaySelectionUIItem,String> nameColumn=new DefaultTableColumnController<DisplaySelectionUIItem,String>(DisplaySelectionDetailsColumnIds.NAME.name(),"Label",String.class,nameCell);
    nameColumn.setWidthSpecs(70,-1,70);
    nameColumn.setEditable(false);
    return nameColumn;
  }

  private static TableColumnController<DisplaySelectionUIItem,?> buildCountColumn()
  {
    CellDataProvider<DisplaySelectionUIItem,Integer> countCell=new CellDataProvider<DisplaySelectionUIItem,Integer>()
    {
      @Override
      public Integer getData(DisplaySelectionUIItem status)
      {
        return Integer.valueOf(status.getMarkersCount());
      }
    };
    DefaultTableColumnController<DisplaySelectionUIItem,Integer> countColumn=new DefaultTableColumnController<DisplaySelectionUIItem,Integer>(DisplaySelectionDetailsColumnIds.COUNT.name(),"Count",Integer.class,countCell);
    countColumn.setWidthSpecs(50,50,50);
    return countColumn;
  }
}
