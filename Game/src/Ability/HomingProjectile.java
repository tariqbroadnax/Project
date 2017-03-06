package Ability;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import Behavior.BehaviorComponent;
import Behavior.Follow;
import Entity.Entity;
import EntityComponent.BodyType;
import EntityComponent.CollisionEvent;
import EntityComponent.CollisionResponse;
import EntityComponent.EffectComponent;
import EntityComponent.GraphicsComponent;
import EntityComponent.RigidBody;
import EntityComponent.RigidBodyComponent;
import Graphic.ShapeGraphic;
import Maths.Circle2D;
import Modifiers.Damage;
import Modifiers.Effect;
import Movement.MovementComponent;

public class HomingProjectile extends Entity
	implements CollisionResponse
{
	private double speed;
	
	private List<Effect> effects;
	
	private Entity src, target;
	
	public HomingProjectile()
	{
		speed = 10;
		
		effects = new ArrayList<Effect>();
		
		effects.add(new Damage(0, 0));
		
		GraphicsComponent graphComp = new GraphicsComponent();
		
		ShapeGraphic shape = new ShapeGraphic();
		
		Circle2D.Double circ = new Circle2D.Double(0, 0, 2.5);
		
		shape.setShape(circ);
		graphComp.setGraphic(shape);
		shape.setLayer(1);
				
		RigidBodyComponent bodyComp = new RigidBodyComponent();
		
		RigidBody body = new RigidBody();
		
		Rectangle2D.Double box = new Rectangle2D.Double(-2.5, -2.5, 5, 5);
		
		body.addLimbs(box);
		bodyComp.setRigidBody(body);
		bodyComp.setBodyType(BodyType.NONE);
		
		add(graphComp, bodyComp,
			new MovementComponent(),
			new BehaviorComponent());
	}
	
	public HomingProjectile(HomingProjectile proj)
	{
		super(proj);
		
		effects = new ArrayList<Effect>();
		
		for(Effect eff : proj.effects)
		{
			eff = (Effect) eff.clone();
			effects.add(eff);
		}
	}
	
	@Override
	public void start()
	{	
		BehaviorComponent behavComp = get(BehaviorComponent.class);
		
		Follow follow = new Follow(target);
	
		behavComp.addBehavior(follow);
			
		RigidBodyComponent bodyComp = get(RigidBodyComponent.class);
		
		bodyComp.add(this);

		Point2D.Double srcLoc = src.getLoc(),
				   targetLoc = target.getLoc();
	
		double angle = Math.atan2(targetLoc.y - srcLoc.y,
								  targetLoc.x - srcLoc.x);
		
		setLoc(srcLoc.x + 5 * Math.cos(angle),
			   srcLoc.y + 5 * Math.sin(angle));
		
		super.start();
	}
	
	public void setSource(Entity src)
	{
		this.src = src;
		
		for(Effect eff : effects)
			eff.setSource(src);
	}
	
	public void setTarget(Entity target)
	{
		this.target = target;
	}

	@Override
	public void collisionOccurred(CollisionEvent e)
	{
		if(e.collided == target || e.collider == target)
		{
			EffectComponent effectComp = target.get(EffectComponent.class);
			
			for(Effect eff : effects)
				effectComp.apply(eff);
			
			src.getSceneLoc()
			   .removeEntity(this);
		}
	}
	
	public Object clone() {
		return new HomingProjectile(this);
	}
}
