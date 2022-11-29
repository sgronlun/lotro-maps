package delta.games.lotro.maps.ui.displaySelection.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import delta.common.ui.swing.tables.GenericTableController;
import delta.common.ui.swing.tables.ListDataProvider;
import delta.common.ui.swing.tables.TableColumnController;
import delta.common.ui.swing.tables.TableColumnsManager;
import delta.common.ui.swing.tables.panel.FilterUpdateListener;
import delta.games.lotro.maps.ui.displaySelection.DisplaySelectionDetailsForCategory;
import delta.games.lotro.maps.ui.displaySelection.DisplaySelectionUIItem;

/**
 * Controller for a table that shows the display selection elements for a single category.
 * @author DAM
 */
public class DisplaySelectionCategoryTableController
{
  // Data
  private DisplaySelectionDetailsForCategory _data;
  // GUI
  private JTable _table;
  private GenericTableController<DisplaySelectionUIItem> _tableController;

  /**
   * Constructor.
   * @param data Data to show.
   * @param listener Listener.
   */
  public DisplaySelectionCategoryTableController(DisplaySelectionDetailsForCategory data, FilterUpdateListener listener)
  {
    _data=data;
    _tableController=buildTable(listener);
    _tableController.setFilter(null);
  }

  private GenericTableController<DisplaySelectionUIItem> buildTable(FilterUpdateListener listener)
  {
    List<DisplaySelectionUIItem> items=_data.getItems();
    ListDataProvider<DisplaySelectionUIItem> provider=new ListDataProvider<DisplaySelectionUIItem>(items);
    GenericTableController<DisplaySelectionUIItem> table=new GenericTableController<DisplaySelectionUIItem>(provider);
    // Columns
    List<TableColumnController<DisplaySelectionUIItem,?>> columns=DisplaySelectionDetailsColumnsBuilder.buildColumns(listener);
    for(TableColumnController<DisplaySelectionUIItem,?> column : columns)
    {
      table.addColumnController(column);
    }

    // Setup
    List<String> columnsIds=getColumnIds();
    TableColumnsManager<DisplaySelectionUIItem> columnsManager=table.getColumnsManager();
    columnsManager.setColumns(columnsIds);

    return table;
  }

  private List<String> getColumnIds()
  {
    List<String> columnIds=new ArrayList<String>();
    columnIds.add(DisplaySelectionDetailsColumnIds.VISIBLE.name());
    columnIds.add(DisplaySelectionDetailsColumnIds.NAME.name());
    columnIds.add(DisplaySelectionDetailsColumnIds.COUNT.name());
    return columnIds;
  }

  /**
   * Get the managed table controller.
   * @return the managed table controller.
   */
  public GenericTableController<DisplaySelectionUIItem> getTableController()
  {
    return _tableController;
  }

  /**
   * Update managed filter.
   */
  public void updateFilter()
  {
    _tableController.filterUpdated();
  }

  /**
   * Get the total number of items.
   * @return A number of items.
   */
  public int getNbItems()
  {
    return _data.getItemsCount();
  }

  /**
   * Get the number of filtered items in the managed table.
   * @return A number of items.
   */
  public int getNbFilteredItems()
  {
    int ret=_tableController.getNbFilteredItems();
    return ret;
  }

  /**
   * Get the managed table.
   * @return the managed table.
   */
  public JTable getTable()
  {
    if (_table==null)
    {
      _table=_tableController.getTable();
    }
    return _table;
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    // GUI
    _table=null;
    if (_tableController!=null)
    {
      _tableController.dispose();
      _tableController=null;
    }
    // Data
    _data=null;
  }
}
