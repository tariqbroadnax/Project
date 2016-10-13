package DebugGUI;

import javax.swing.JLabel;

import EntityComponent.EntityComponent;
import Game.Entity;
import Movement.MovementComponent;

public class MovementCompViewer extends EntityCompViewer
{
	public MovementCompViewer(Entity entity)
	{
		super(entity);
		
		model.addFieldRecord("Movement Enabled", 
				() -> _comp().isMovementEnabled()+ "");
		
		model.addFieldRecord("Moving", 
				() -> _comp().isMoving() + "");
		
		model.addFieldRecord("Direction", 
				() -> _comp().getDirection() + "");
		
		model.addFieldRecord("Previous Direction", 
				() -> _comp().getPreviousDirection() + "");
		
		add(new JLabel("Normal Movement: " + _comp().getNormalMovement().getClass().getSimpleName()));
		add(new MovementViewer(()->_comp().getNormalMovement()));
		add(new JLabel("Disabling Movement: " + _comp().getDisablingMovement().getClass().getSimpleName()));
		add(new MovementViewer(()->_comp().getDisablingMovement()));
	}

	@Override
	protected EntityComponent comp() 
	{
		return _comp();
	}
	
	private MovementComponent _comp()
	{
		return entity.get(MovementComponent.class);
	}
}
