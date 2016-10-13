package EntityComponent;

import java.io.Serializable;
import java.util.ArrayList;

import Item.EquipItem;
import Item.InventoryItem;
import Item.UseItem;

public class Inventory
implements Serializable
{	
	private ArrayList<InventoryItem> equipItems,
									 useItems;
	
	public Inventory()
	{
		equipItems = new ArrayList<InventoryItem>();
		
		useItems = new ArrayList<InventoryItem>();
	}
	
	public Inventory(Inventory inventory)
	{
		this();
	}
	
	public void addEquipItem(EquipItem item)
	{
		equipItems.add(item);
	}
	
	public void addUseItem(UseItem item)
	{
		useItems.add(item);
	}
	
	public ArrayList<InventoryItem> 
		getItems(Class<?> clas)
	{
		if(clas == EquipItem.class)
			return equipItems;
		else if(clas == UseItem.class)
			return useItems;
		return null;
	}

	public String toString()
	{
		String str = super.toString();
		
		str += "\nItems:";
		
		return str;
	}
}

