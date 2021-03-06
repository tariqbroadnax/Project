package Ability;

import java.awt.geom.Point2D;

import Entity.Entity;
import EntityComponent.GraphicsComponent;
import EntityComponent.LifetimeComponent;
import Graphic.ShapeGraphic;
import Maths.Circle2D;
import Movement.Force;
import Movement.MovementComponent;

public class ProjectileAbility extends PointAbility
{
	private Entity projectile;

	private double offset;

	private Force force;	
	
	private Sector sector;
	
	public ProjectileAbility()
	{
		projectile = defaultProjectile();
		
		force = new Force();
		
		force.setSpeed(75);
		
		offset = 12;
		
		sector = new Sector();
	}
	
	private Entity defaultProjectile()
	{
		Entity ent = new Entity();
		
		ent.add(new GraphicsComponent(),
				new MovementComponent(),
				new LifetimeComponent());
		
		ent.get(LifetimeComponent.class)
		   .getLifetime()
		   .setRemaining(1000);
		
		ShapeGraphic graph = new ShapeGraphic();
		
		Circle2D.Double circ = new Circle2D.Double(0, 0, 2.5);
		
		graph.setShape(circ);
		
		ent.get(GraphicsComponent.class)
		   .setGraphic(graph);
		
		return ent;
	}
	
	public void activate()
	{
		super.activate();	
		
		Point2D.Double src = getSrc().getLoc();
		
		double angle = Math.atan2(target.y - src.y, 
								  target.x - src.x);
		
		int pivots = sector.getPivots();
		
		if(pivots == 0)
		{
			createProjectile(angle, src);
		}
		else
		{
			double sectorAngle = sector.getAngle(),
				   pivotAngle = sector.getAngle() / pivots,
				   newAngle = angle - sectorAngle/2;
	
			for(int i = 0; i < pivots + 1; i++, newAngle += pivotAngle)
				createProjectile(newAngle, src);
		}
	}
	
	private void createProjectile(double angle, Point2D.Double src)
	{
		double x = src.x + Math.cos(angle) * offset,
			   y = src.y + Math.sin(angle) * offset;
			
		Force force = (Force) this.force.clone();
		
		force.setDirection(angle);
		
		Entity clone = (Entity) projectile.clone();
		
		clone.setLoc(x, y);
		
		if(clone.contains(MovementComponent.class))
			clone.get(MovementComponent.class)
				 .getMovement()
				 .addForce(force);
		
		this.src.getSceneLoc()
			.addEntity(clone);
	}
	
	public Force getForce() {
		return force;
	}
	
	public Entity getProjectile() {
		return projectile;
	}
}
