package Shop;

import java.util.HashSet;
import java.util.Set;

import Entity.Entity;
import EntityComponent.StatsComponent;
import Inventory.Inventory;
import Inventory.InventoryComponent;
import Item.Item;
import Stat.Stats;

public class Shop 
{
	private Set<Item> items;
	
	public Shop()
	{
		items = new HashSet<Item>();
	}
	
	public void buy(Entity ent, Item item)
	{
		Stats stats = ent.get(StatsComponent.class)
						 .getStats();
	
		Inventory inventory = ent.get(InventoryComponent.class)
								 .getInventory();
		
		int cost = item.getValue();
		
		stats.removeGold(cost);
		inventory.addItem(item);
	}
	
	public void sell(Entity ent, Item item)
	{
		Stats stats = ent.get(StatsComponent.class)
						 .getStats();
	
		Inventory inventory = ent.get(InventoryComponent.class)
								 .getInventory();
		
		int bid = item.getValue() / 2;
		
		stats.addGold(bid);
		inventory.removeItem(item);
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public void removeItem(Item item) {
		items.remove(item);
	}
}
