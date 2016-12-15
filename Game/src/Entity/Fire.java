package Entity;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.time.Duration;
import java.util.List;

import EntityComponent.EffectComponent;
import EntityComponent.GraphicsComponent;
import EntityComponent.LifetimeComponent;
import EntityComponent.RigidBody;
import EntityComponent.RigidBodyComponent;
import Graphic.Animation;
import Graphic.Sprite;
import Maths.Circle2D;
import Maths.Range;
import Maths.Vector2D;
import Utilities.Collections2;

public class Fire extends Entity
{	
	private long sparkDelay, elapsed;
	
	private double radius;
	
	public Fire()
	{
		super();
	
		sparkDelay = 500;
		elapsed = 0;
		radius = 5;
		
		add(createGraphicsComponent(),
			createRigidBodyComponent(),
			new EffectComponent(),
			new LifetimeComponent());
	}
	
	@Override
	public void update(Duration delta) 
	{		
		elapsed += delta.toMillis();

		if(elapsed >= sparkDelay)
		{
			elapsed -= sparkDelay;
			
			double angle = Range.Double.rand(0, 2 * Math.PI);
						
			double x = loc.x + 2 * radius * cos(angle),
				   y = loc.y + 2 * radius * sin(angle);
			
			Circle2D.Double circ = new Circle2D.Double(x - radius, y - radius + 10, radius);
			
			List<Entity> collisions = 
						 getSceneLoc()
						.getCollisionManager()
						.collisions(circ);
			
			collisions.remove(this);
			collisions.removeIf(e -> 
				!(e instanceof Terrain || e instanceof Fire));
			
			if(!collisions.isEmpty() &&
			   !Collections2.contains(collisions, e -> e instanceof Fire))
			{		
				Fire fire = new Fire();
				fire.setLoc(x, y);
				getSceneLoc().addEntity(fire);
			}
		}
		
		/* must be after since above code assumes
		 * fire is still in scene (sceneLoc != null)
		 */
		super.update(delta); 		
	}
	
	public GraphicsComponent createGraphicsComponent()
	{
		GraphicsComponent comp = new GraphicsComponent();
		
		Animation fireAnim =
				Sprite.animation(
						"fire.png", 4, 8, 20, 40);
		
		fireAnim.setLayer(1);

		comp.setGraphic(fireAnim);
		
		return comp;
	}
	
	public RigidBodyComponent createRigidBodyComponent()
	{
		RigidBodyComponent comp = new RigidBodyComponent();
		
		RigidBody body = new RigidBody();
		Vector2D.Double offset = new Vector2D.Double(0, 10);
		
		Circle2D.Double circ = new Circle2D.Double(
				loc.x - radius, loc.y - radius, radius);
		
		body.addComponent(offset, circ);
		
		comp.setRigidBody(body);
		
		return comp;
	}
	
}
