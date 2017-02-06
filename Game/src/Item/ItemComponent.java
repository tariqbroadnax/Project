package Item;

import java.time.Duration;

import EntityComponent.EntityComponent;

public class ItemComponent extends EntityComponent
{
	private Item model;
	
	public ItemComponent()
	{
		model = new Item();
	}
	
	@Override
	public void update(Duration delta) {
		
	}
	
	public void setModel(Item model) {
		this.model = model;
	}
	
	public Item getModel() {
		return model;
	}

	@Override
	protected EntityComponent _clone() {
		return null;
	}
}
