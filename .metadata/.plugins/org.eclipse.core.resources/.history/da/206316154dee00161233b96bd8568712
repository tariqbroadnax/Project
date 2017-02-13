package Event;

import java.awt.geom.Point2D;

import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Game.Game;
import Graphic.Vector2D;
import Movement.Force;
import Movement.Movement;
import Movement.MovementComponent;

public class SpawnProjectile extends PointAbilityEvent
{
	private Entity model;
	
	private double radius;
	
	private double speed;
	
	public SpawnProjectile(Game game) 
	{
		super(game);
		
		model = new Entity();
		
		model.add(new MovementComponent(),
				  new GraphicsComponent());	
	
		speed = 20;
		radius = 10;
	}
	
	private void moveEntity(
			Entity clone, Point2D.Double src, Vector2D.Double dv)
	{
		dv = dv.getUnit().getScaled(radius);
		
		clone.setLoc(src.x + dv.x, src.y + dv.y);
	}
	
	private void addForce(Entity clone, Vector2D.Double dv)
	{
		Movement movement = clone.get(MovementComponent.class)
					   		     .getMovement();
		
		Force force = new Force(speed, dv.angle());
				
		movement.addForce(force);
	}

	@Override
	public void run() 
	{
		Entity clone = (Entity) model.clone();

		Point2D.Double src = ability.getSrc()
			    					.getLoc(),
			    	   target = ability.getTarget();
		
		Vector2D.Double dv = new Vector2D.Double(
				target.x - src.x, target.y - src.y);
		
		moveEntity(clone, src, dv);
		addForce(clone, dv);
		
		game.getScene()
			.addEntity(clone);
	}
}