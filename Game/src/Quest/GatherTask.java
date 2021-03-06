package Quest;

import Inventory.Inventory;
import Inventory.InventoryComponent;
import Item.Item;

public class GatherTask extends Task 
{
	private Item item;
	
	private int target;
	
	public GatherTask()
	{
		item = null;
		target = 5;
	}
	
	public GatherTask(Item item, int target)
	{
		this.item = item;
		this.target = target;
	}
	
	public GatherTask(GatherTask task)
	{
		item = task.item;
		target = task.target;
	}
	
	@Override
	public void start() {}

	public void finish()
	{
		Inventory invent = tasker.get(InventoryComponent.class)
								 .getInventory();
		
		for(int i = 0; i < target; i++)
			invent.removeItem(item);
	}
	
	@Override
	public boolean isFinished() 
	{
		Inventory invent = tasker.get(InventoryComponent.class)
				 				 .getInventory();
		
		int quantity = invent.getQuantity(item);
		
		return quantity > target;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public void setTarget(int target) {
		this.target = target;
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getTarget() {
		return target;
	}

	@Override
	public Object clone() {
		return new GatherTask(this);
	}
}
