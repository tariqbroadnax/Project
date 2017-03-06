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
	
	private Inventory inventory;
	
	public void setPetItem(PetItem pet)
	{
		if(this.pet != null)
		{
			inventory.addPetItem(this.pet);
			
			this.pet.unequip(src);
		}
		
		this.pet = pet;
		
		pet.equip(src);
	}
	
	public void setArmorItem(ArmorItem armor)
	{
		if(this.armor != null)
		{
			inventory.addEquipItem(this.armor);
			
			this.armor.unequip(src);
		}
		
		this.armor = armor;
		
		armor.equip(src);
	}
	
	public void setWeaponItem(WeaponItem weapon)
	{
		if(this.weapon != null)
		{
			inventory.addEquipItem(this.weapon);
			
			this.weapon.unequip(src);
		}
		
		this.weapon = weapon;
		
		weapon.equip(src);
	}
	
	public void setAccessory1(Accessory access1)
	{
		if(this.access1 != null)
		{
			inventory.addEquipItem(this.access1);
			
			this.access1.unequip(src);
		}
		
		this.access1 = access1;
		
		access1.equals(src);
	}
	
	public void setAccessory2(Accessory access2)
	{
		if(this.access2 != null)
		{
			inventory.addEquipItem(this.access2);
			
			this.access2.unequip(src);
		}
		
		this.access2 = access2;
		
		access2.equals(src);
	}
	
	public void setEquipItem(EquipItem item)
	{
		if(item instanceof PetItem)
		{
			PetItem petItem = (PetItem) item;
			
			setPetItem(petItem);
		}
		else if(item instanceof WeaponItem)
		{
			WeaponItem weapon = (WeaponItem) item;
			
			setWeaponItem(weapon);
		}
		else if(item instanceof ArmorItem)
		{
			ArmorItem armor = (ArmorItem) item;
			
			setArmorItem(armor);
		}
		else
		{
			Accessory accessory = (Accessory) item;
			
			if(access1 == null)
				setAccessory1(accessory);
			else
				setAccessory2(accessory);
		}
	}
	
	public PetItem getPetItem() {
		return pet;
	}
	
	public WeaponItem getWeaponItem() {
		return weapon;
	}
	
	public ArmorItem getArmorItem() {
		return armor;
	}
	
	public Accessory getAccessory1() {
		return access1;
	}
	
	public Accessory getAccessory2() {
		return access2;
	}
 	
	public void setSource(Entity src) 
	{
		this.src = src;
		
		inventory = src.get(InventoryComponent.class)
					   .getInventory();
	}
}
