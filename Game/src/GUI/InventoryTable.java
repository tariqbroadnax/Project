package GUI;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import EntityComponent.GraphicsComponent;
import EntityComponent.Inventory;
import Graphic.Graphic;
import Item.InventoryItem;

public class InventoryTable extends JTable
{
	private int height = 0;
	
	private InventoryModel model;
	
	public InventoryTable(Class<? extends InventoryItem> clas)
	{
		super();
	
		model = new InventoryModel(clas);
		
		setModel(model);
		
		setDefaultRenderer(InventoryItem.class, new InventoryItemRenderer());
		
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		setTableHeader(null);
	}
	
	public void setInventory(Inventory inven)
	{
		model.setInventory(inven);
	}
	
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		
		InventoryModel model = (InventoryModel)getModel();
		
		int nVisibleRow = model.getVisibleRowCount(),
			rowHeight = height / nVisibleRow;
		
		setRowHeight(rowHeight);	
	}	
}

class InventoryModel extends AbstractTableModel
{
	private Inventory inventory;
	
	private Class<? extends InventoryItem> clas;
	
	private ArrayList<InventoryItem> items;
	
	private int rowCount = 10, columnCount = 5,
				nVisibleRow = 5;

	public InventoryModel(Class<? extends InventoryItem> clas)
	{
		this.clas = clas;
	}
	
	public void setInventory(Inventory inventory)
	{
		items = inventory.getItems(clas);
	}
	
	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public int getRowCount() {
		return rowCount;
	}
	
	public int getVisibleRowCount() {
		return nVisibleRow;
	}

	@Override
	public Object getValueAt(int row, int col) 
	{
		int i = row * columnCount + col;
		
		if(items.size() < i + 1)
			return null;
		else
			return items.get(row * columnCount + col);
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}
}

class InventoryItemRenderer extends JLabel
	implements TableCellRenderer
{
	private Graphic graphic;
	
	public void paintComponent(Graphics g)
	{
		graphic.paint(new Point(), getSize(), (Graphics2D)g);
	}
	
	@Override
	public Component getTableCellRendererComponent(
			JTable table, Object item,
			boolean isSelected, boolean hasFocus,
			int row, int col) 
	{
		InventoryItem newItem = (InventoryItem)item;
		
		graphic = newItem.get(GraphicsComponent.class)
						 .getGraphic();
		
		return this;
	}

}
						
