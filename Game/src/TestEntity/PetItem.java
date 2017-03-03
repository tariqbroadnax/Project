package TestEntity;

import Entity.Entity;
import Item.EquipItem;

public class PetItem extends EquipItem
{
	private Pet pet;
	
	public PetItem()
	{
		pet = new Pet();
	}
	
	public void equip(Entity ent)
	{
		super.equip(ent);
		
		ent.getSceneLoc()
		   .addEntity(ent);
		
		double angle = Math.random() * Math.PI * 2;
		
		double x = ent.getLoc().x + Math.cos(angle) * 10,
			   y = ent.getLoc().y + Math.sin(angle) * 10;
	}
	
	public void unequip(Entity ent)
	{
		super.unequip(ent);
		
		ent.getSceneLoc()
		   .removeEntity(ent);
	}
}
