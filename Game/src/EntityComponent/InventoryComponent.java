package EntityComponent;

import java.time.Duration;

public class InventoryComponent extends EntityComponent
{
	private Inventory inventory;
	
	public InventoryComponent()
	{
		super();
		
		inventory = new Inventory();
	}
	
	public InventoryComponent(InventoryComponent comp)
	{
		this();
		
		inventory = new Inventory(comp.inventory);
	}
	
	@Override
	public void update(Duration delta){}

	public Inventory getInventory()
	{
		return inventory;
	}
	
	@Override
	protected EntityComponent _clone()
	{
		return new InventoryComponent(this);
	}	
	
	public String toString()
	{
		return super.toString() + '\n' + inventory.toString();
	}
}
