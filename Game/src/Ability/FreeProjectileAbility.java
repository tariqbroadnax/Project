package Ability;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import CollisionResponses.ApplyEffects;
import CollisionResponses.EndLife;
import Entity.AbilityEntity;
import Entity.Entity;
import EntityComponent.CollisionFilter;
import EntityComponent.GraphicsComponent;
import EntityComponent.Lifetime;
import EntityComponent.LifetimeComponent;
import EntityComponent.RigidBody;
import EntityComponent.RigidBodyComponent;
import Graphic.ShapeGraphic;
import Maths.Circle2D;
import Maths.Maths;
import Modifiers.Effect;
import Modifiers.Knockback;
import Modifiers.WaterEffect;
import Movement.Movement;
import Movement.MovementComponent;

public class FreeProjectileAbility 
	extends TargetPointAbility
{
	private double speed, offset, angleOffset;
	private long lifetime;
	private int projCount;
	
	private List<Effect> effects;
	
	public FreeProjectileAbility()
	{
		speed = 75; offset = 10;
		angleOffset = Math.PI/32;
		lifetime = 1000;
		projCount = 10;
		
		effects = new LinkedList<Effect>();
		
		add(new Knockback());
		add(new WaterEffect());

	}
	
	protected void activate()
	{
		super.activate();	
		createProjectile();
	}
	
	private void createProjectile()
	{
		for(int i = 0; i < projCount; i++)
		{
			Entity projectile = new AbilityEntity();
			
			Point2D.Double projectLoc =
					findProjectileLoc(i);
			
			projectile.setLoc(projectLoc);
			
			projectile.add(createMovementComponent(i),
						   createGraphicsComponent(),
						   createLifetimeComponent(),
						   createRigidBodyComponent());
		
			src.getSceneLoc()
			   .addEntity(projectile);
		}
	}
	
	private MovementComponent createMovementComponent(int i)
	{
		MovementComponent moveComp = 
				new MovementComponent();

		Point2D.Double srcLoc = src.getLoc();
		
		int j = (i + 1) / 2 * (int)Math.pow(-1, i);
				
		double angle = Maths.angleFrom(srcLoc, target) +
					   j * angleOffset;
		
		Movement movement = moveComp.getNormalMovement();
		
		movement.setSpeed(speed);
		movement.setEnabled(true);
		movement.setDirection(angle);
		
		return moveComp;
	}
	
	private LifetimeComponent createLifetimeComponent()
	{
		LifetimeComponent lifeComp =
				new LifetimeComponent();
		
		Lifetime lifetime = new Lifetime(this.lifetime);
	
		lifeComp.setLifetime(lifetime);
		
		return lifeComp;
	}
	
	private GraphicsComponent createGraphicsComponent()
	{
		GraphicsComponent graphComp = 
				new GraphicsComponent();
	
		ShapeGraphic graphic = new ShapeGraphic();
		
		Circle2D.Double circle = new Circle2D.Double();
		circle.setRadius(2.5);
		
		graphic.setShape(circle);
		graphComp.setGraphic(graphic);
		
		return graphComp;
	}
	
	private RigidBodyComponent createRigidBodyComponent()
	{
		RigidBodyComponent comp =
				new RigidBodyComponent();
	
		RigidBody body = new RigidBody();
		body.addComponent(new Circle2D.Double(0,0,2.5));
		
		comp.setRigidBody(body);
		
		EndLife end = new EndLife();
		ApplyEffects applyfx = new ApplyEffects(effects);
		
		CollisionFilter filter = e -> 
			!(e == src || e instanceof AbilityEntity);
		
		comp.add(end, filter);
		comp.add(applyfx, filter);
		
		return comp;
	}
	
	private Point2D.Double findProjectileLoc(int i)
	{
		Point2D.Double srcLoc = src.getLoc();
		
		int j = (i + 1) / 2 * (int)Math.pow(-1, i);
				
		double angle = Maths.angleFrom(srcLoc, target) +
					   j * angleOffset;
		
		double xOffset = offset * Math.cos(angle),
			   yOffset = offset * Math.sin(angle);
		
		return new Point2D.Double(srcLoc.x + xOffset,
								  srcLoc.y + yOffset);
	}
	
	public void add(Effect effect) { 
		effects.add(effect);
		effect.setSource(src);
	}
	
	public void remove(Effect effect) {
		effects.remove(effect);
	}
	
	public void setProjectileSpeed(double speed) {
		this.speed = speed;
	}
	
	public void setProjectileOffset(double offset) {
		this.offset = offset;
	}
	
	public void setProjectileLifetime(long lifetime) {
		this.lifetime = lifetime;
	}
	
	public void setSrc(Entity source)
	{
		super.setSrc(source);
		
		for(Effect effect : effects)
			effect.setSource(source);
	}
	
	public void setProjCount(int projCount) 
	{
		if(projCount < 1)
			throw new IllegalArgumentException();
		
		this.projCount = projCount;
	}
	
	public void setAngleOffset(double angleOffset) {
		this.angleOffset = angleOffset;
	}
}
