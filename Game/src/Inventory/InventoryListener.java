package Inventory;

public interface InventoryListener 
{
	public default void itemAdded(){}
	
	public default void itemRemoved(){}
	
	public default void itemUsed(){}
}
