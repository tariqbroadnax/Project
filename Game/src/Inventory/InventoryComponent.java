package Inventory;

import java.time.Duration;

import EntityComponent.EntityComponent;
import Item.UseItem;

public class InventoryComponent extends EntityComponent
{
	private Inventory inventory;
	
	public InventoryComponent()
	{
		inventory = new Inventory();
	}
	
	public void useItem(UseItem item) {
		inventory.useItem(item, parent);
	}
	
	@Override
	public void update(Duration delta) {		
	}
	
	public Inventory getInventory() {
		return inventory;
	}

	@Override
	protected EntityComponent _clone() {
		// TODO Auto-generated method stub
		return null;
	}
}
