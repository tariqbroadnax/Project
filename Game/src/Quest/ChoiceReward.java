package Quest;

import java.util.ArrayList;
import java.util.List;

import Entity.Entity;
import Inventory.InventoryComponent;
import Item.Item;

public class ChoiceReward implements Reward
{
	private List<Item> choices;
	
	private Item choice;
	
	public ChoiceReward()
	{
		choices = new ArrayList<Item>();
	}
	
	public ChoiceReward(ChoiceReward reward)
	{
		this();
		
		for(Item choice : reward.choices)
			addChoice(choice);
	}
	
	@Override
	public void give(Entity ent) 
	{
		ent.get(InventoryComponent.class)
		   .getInventory()
		   .addItem(choice);
	}
	
	public void setChoice(Item choice) {
		this.choice = choice;
	}
	
	public void addChoice(Item choice) 
	{
		choices.add(choice);
		
		this.choice = choice;
	}
	
	public void removeChoice(Item choice) 
	{
		choices.remove(choice);

		if(choices.isEmpty())
			this.choice = null;
		else
			this.choice = choices.get(choices.size() - 1);
	}
	
	public List<Item> getChoices() {
		return choices;
	}
	
	public Object clone() {
		return new ChoiceReward(this);
	}
}
