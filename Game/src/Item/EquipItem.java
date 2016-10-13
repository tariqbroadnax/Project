package Item;

import java.util.Collection;

import EntityComponent.StatsComponent;
import Game.Player;
import Modifiers.StatModifier;
import Stat.Stats;

public abstract class EquipItem extends InventoryItem
{
	public EquipItem(int id) 
	{
		super(id);
	}
	
	public void equip(Player player)
	{
		Stats stats = player
				.get(StatsComponent.class)
				.getStats();
		
	}
	
	public abstract Collection<StatModifier> getStatModifiers();
}
