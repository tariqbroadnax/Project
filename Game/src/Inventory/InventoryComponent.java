package Inventory;

import java.time.Duration;

import EntityComponent.EntityComponent;

public class InventoryComponent extends EntityComponent
{
	private Inventory inventory;
	
	public InventoryComponent()
	{
		inventory = new Inventory();
	}
	
	@Override
	public void update(Duration delta) {
		// TODO Auto-generated method stub
		
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
