package Item;

import Entity.Entity;
import Inventory.Inventory;
import Inventory.InventoryComponent;
import TestEntity.PetItem;

public class Equipment 
{
	private Entity src;
	
	private WeaponItem weapon;
	
	private ArmorItem armor;
	
	private Accessory access1, access2;
	
	private PetItem pet;
	
	public void setPetItem(PetItem pet)
	{
		if(this.pet != null)
		{
			Inventory inventory = src.get(InventoryComponent.class)
									 .getInventory();
			
			inventory.addEquipItem(this.pet);
			
			this.pet.unequip(src);
		}
		
		this.pet = pet;
		
		pet.equip(src);
	}
	
	public void setArmorItem(ArmorItem armor)
	{
		
	}
	
	public void setWeaponItem(WeaponItem weapon)
	{
		
	}
	
	public void setAccessory1(Accessory access1)
	{
		
	}
	
	public void setAccessory2(Accessory access2)
	{
		
	}
}
