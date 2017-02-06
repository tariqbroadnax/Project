package Quest;

import Entity.Entity;
import Inventory.Inventory;
import Inventory.InventoryComponent;
import Inventory.InventoryListener;

public class GatherTask extends Task
	implements InventoryListener
{
	private Entity ent;
	
	private int id, target;

	public GatherTask()
	{
		id = -1;
		target = 5;
	}
	
	@Override
	public void start(Entity ent) 
	{
		this.ent = ent;
		
		Inventory i = ent.get(InventoryComponent.class)
						 .getInventory();
		
		if(i.itemCount(id) < target)
		{
			ent.get(InventoryComponent.class)
			   .getInventory()
			   .addInventoryListener(this);
		}
		else
			taskCompleted();
	}
	
	public void setTargetID(int id) {
		this.id = id;
	}
	
	public void setTargetGatherCount(int target) {
		this.target = target;
	}
	
	public int getTargetID() {
		return id;
	}
	
	public int getTargetGatherCount() {
		return target;
	}

	@Override
	public void itemAdded() 
	{
		Inventory i = ent.get(InventoryComponent.class)
						 .getInventory();
		
		// DEBUG ---
		System.out.println("Progess: " + i.itemCount(id) + "/" + target);
		
		// ---------
		if(i.itemCount(id) >= target)
		{
			taskCompleted();

			i.removeInventoryListener(this);
		}
	}
}
