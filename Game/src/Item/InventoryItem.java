package Item;

import EntityComponent.InventoryComponent;
import Game.Entity;

public class InventoryItem extends Item
{
	private int id;
			
	public InventoryItem(int id)
	{
		super();
		
		this.id = id;		
	}
	
	public InventoryItem(InventoryItem item)
	{
		super(item);
		
		id = item.id;		
	}
	
	@Override
	protected void _getPickedUp(Entity e)
	{
		e.get(InventoryComponent.class)
		 .getInventory();
	}
	
	public int getID()
	{
		return id;
	}

	@Override
	public Object _clone()
	{
		return new InventoryItem(this);
	}
}
