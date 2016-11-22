package EntityComponent;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import Game.Entity;
import Graphic.Graphic;
import Graphic.ShapeGraphic;
import Movement.Cardinal;
import Movement.MovementComponent;
import Movement.MovementListener;

public class BeingGraphicsComp extends GraphicsComponent 
	implements MovementListener
{
	private Map<Cardinal, Graphic> standbyGraphics,
								   movementGraphics;
	
	private boolean moving;
	private Cardinal dir;
	
	public BeingGraphicsComp()
	{
		super();
		
		standbyGraphics = new HashMap<Cardinal, Graphic>();
		movementGraphics = new HashMap<Cardinal, Graphic>();
		
		moving = false;
		dir = Cardinal.NORTH;
		
		alloc();
		updateGraphic();
	}
	
	private void alloc()
	{
		ShapeGraphic graphic = new ShapeGraphic();
				
		for(Cardinal dir : Cardinal.values())
		{
			standbyGraphics.put(dir, graphic);
			movementGraphics.put(dir, graphic);
		}	
	}
	
	private void updateGraphic()
	{
		Graphic graphic;
		
		if(moving)
			graphic = movementGraphics.get(dir);
		else
			graphic = standbyGraphics.get(dir);
		
		setGraphic(graphic);	
	}
	
	public void setParent(Entity parent)
	{
		super.setParent(parent);
		
		parent.get(MovementComponent.class)
			  .addMovementListener(this);
	}
	
	public void putStandyGraphic(
			Cardinal dir, Graphic graphic) {
		standbyGraphics.put(dir, graphic);
	}
	
	public void putMovementGraphic(
			Cardinal dir, Graphic graphic) {
		movementGraphics.put(dir, graphic);
	}
	
	@Override
	public void movementStarted(
			MovementComponent src)
	{
		moving = true;		
		updateGraphic();
	}
	
	@Override
	public void movementStopped(
			MovementComponent src)
	{
		moving = false;
		updateGraphic();
	}
	
	@Override
	public void directionChanged(
			MovementComponent src)
	{
		dir = src.getDirection();
		updateGraphic();
	}
}
