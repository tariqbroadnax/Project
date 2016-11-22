package DebugGUI;

import java.util.ArrayList;
import java.util.function.Supplier;

import javax.swing.table.AbstractTableModel;

public class ViewerModel extends AbstractTableModel
{
	private ArrayList<FieldRecord> records;
	
	public ViewerModel()
	{
		records = new ArrayList<FieldRecord>();
	}
	
	public void addFieldRecord(String fieldName, Supplier<String> valSupplier)
	{
		records.add(new FieldRecord(fieldName, valSupplier));
	}
	
	@Override
	public int getColumnCount() 
	{
		return 2;
	}

	@Override
	public int getRowCount() 
	{
		return records.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) 
	{
		if(columnIndex == 0)
			return records.get(rowIndex).fieldName;
		else
			return records.get(rowIndex).valueSupplier.get();
	}
}
