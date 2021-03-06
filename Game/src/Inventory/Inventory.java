package Inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Entity.Entity;
import Item.EquipItem;
import Item.Item;
import Item.UseItem;
import TestEntity.PetItem;

public class Inventory 
{
	private List<EquipItem> equips;
	private List<UseItem> consumables;
	private List<PetItem> pets;
	private List<Item> etcs;
	
	private List<InventoryListener> lists;
	
	private int cap;
	
	public Inventory()
	{
		equips = new ArrayList<EquipItem>();
		consumables = new ArrayList<UseItem>();
		pets = new ArrayList<PetItem>();
		etcs = new ArrayList<Item>();
		
		lists = new ArrayList<InventoryListener>();
		
		cap = 300;
		
		UseItem item = new UseItem();
		
		item.setIconFile(new File("SwordIcon.PNG"));
		
		for(int i = 0; i < 50; i++)
			addUseItem(item);		
	}
	
	public void useItem(UseItem item, Entity ent)
	{
		if(consumables.contains(item))
		{
			item.use(ent);
			
			itemUsed();
		
			if(item.quantity() == 0)
			{
				consumables.remove(item);
				itemRemoved();
			}
		}
	}
	
	private void itemAdded() 
	{
		List<InventoryListener> copy = new ArrayList<InventoryListener>(lists);
	
		for(InventoryListener list : copy)
			list.itemAdded();
	}
	
	private void itemRemoved()
	{
		List<InventoryListener> copy = new ArrayList<InventoryListener>(lists);
		
		for(InventoryListener list : copy)
			list.itemRemoved();
	}
	
	private void itemUsed()
	{
		List<InventoryListener> copy = new ArrayList<InventoryListener>(lists);
		
		for(InventoryListener list : copy)
			list.itemUsed();
	}
	
	public void addEquipItem(EquipItem equip) 
	{
		equips.add(equip);	
		
		itemAdded();
	}
	
	public void addUseItem(UseItem item)
	{
		consumables.add(item);
		
		itemAdded();
	}
	
	public void addPetItem(PetItem pet)
	{
		pets.add(pet);
		
		itemAdded();
	}

	public void addEtcItem(Item item) 
	{
		etcs.add(item);
		
		itemAdded();
	}
	
	public boolean addItem(Item item)
	{
		
	}
	
	public void removeEquipItem(EquipItem equip) 
	{
		if(equips.remove(equip))
			itemRemoved();
	}
	
	public void removeUseItem(UseItem item)
	{
		if(consumables.remove(item))
			itemRemoved();
	}
	
	public void removePetItem(PetItem pet) 
	{
		if(pets.remove(pet))
			itemRemoved();
	}
	
	public void removeEtcItem(Item item) 
	{
		if(etcs.remove(item))
			itemRemoved();
	}
	
	public int itemCount(int id) 
	{
		int count = 0;
		
		for(Item item : equips)
			if(item.getID() == id)
				count++;
		
		for(Item item : consumables)
			if(item.getID() == id)
				count++;
		
		for(Item item : pets)
			if(item.getID() == id)
				count++;
		
		for(Item item : etcs)
			if(item.getID() == id)
				count++;
		
		return count;
	}
	

	public List<EquipItem> getEquipItems() {
		return Collections.unmodifiableList(equips);
	}
	
	public List<UseItem> getUseItems() {
		return Collections.unmodifiableList(consumables);
	}
	
	public List<PetItem> getPetItems() {
		return Collections.unmodifiableList(pets);
	}
	
	public List<Item> getEtcItems() {
		return Collections.unmodifiableList(etcs);
	}
	
	public int getQuantity(Item item) {
		return -1;
	}
	
	public int size() 
	{
		int size = 0;
		
		for(Item item : equips)
			size += item.quantity();
		
		for(Item item : consumables)
			size += item.quantity();
		
		return size;
	}
	
	public int capacity() {
		return cap;
	}
	
	public void addInventoryListener(InventoryListener list) {
		lists.add(list);
	}
	
	public void removeInventoryListener(InventoryListener list) {
		lists.remove(list);
	}
}
