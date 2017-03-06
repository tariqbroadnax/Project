package EntityComponent;

import java.time.Duration;

import Entity.Entity;
import Item.Equipment;

public class EquipmentComponent extends EntityComponent
{
	private Equipment equipment;
	
	public EquipmentComponent()
	{
		equipment = new Equipment();
	}
	
	@Override
	public void update(Duration delta) {
		
	}
	
	public Equipment getEquipment() {
		return equipment;
	}
	
	public void setParent(Entity parent) 
	{
		super.setParent(parent);
		equipment.setSource(parent);
	}

	@Override
	protected EntityComponent _clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
