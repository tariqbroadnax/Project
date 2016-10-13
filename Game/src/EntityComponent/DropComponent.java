package EntityComponent;

import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

import Item.DropItem;
import Item.Item;
import Utilities.EntityUtilities;

public class DropComponent extends EntityComponent
{
	private Collection<DropItem> dropItems;

	public DropComponent()
	{
		super();
		
		dropItems = new LinkedList<DropItem>();
	}
	
	public DropComponent(DropComponent comp)
	{
		this();
		
		for(DropItem dropItem : comp.dropItems)
			dropItems.add(new DropItem(dropItem));
	}

	@Override
	public void update(Duration delta)
	{
		if(EntityUtilities.isDead(parent))
			for(DropItem dropItem : dropItems)
				checkAndDropItem(dropItem);
	}
	
	public void addDropItem(Item item, double dropRate)
	{
		dropItems.add(new DropItem(item, dropRate));
	}
	
	private void checkAndDropItem(DropItem dropItem)
	{
		double ceil = Math.random();
		
		if(dropItem.rate > ceil)
		{
			setDropItemLoc(dropItem);
			dropItem(dropItem);
		}
	}
	
	private void setDropItemLoc(DropItem dropItem)
	{
		dropItem.item.setLoc(parent.getLoc());
	}
	
	private void dropItem(DropItem dropItem)
	{
		SpawnComponent spawnComp = parent
				.get(SpawnComponent.class);
		
		spawnComp.addChildren(dropItem.item);
		
		//FIXME DELETEME
		System.out.println("dropping Item: " + dropItem.item.toString2());
	}
	
	@Override
	protected EntityComponent _clone()
	{
		return new DropComponent(this);
	}
	
	public String toString()
	{
		String str = super.toString();
		
		str += "\nDrop Items: ";
		for(DropItem dropItem: dropItems)
			str += '\n' + dropItem.toString();
		
		return str;
	}
}
