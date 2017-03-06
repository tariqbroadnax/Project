package Game;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import Entity.Entity;
import EntityComponent.GraphicsComponent;
import EntityComponent.LifetimeComponent;
import Maths.Circle2D;

public class Spawner implements Updatable
{
	private int size;
	
	private Circle2D.Double area;

	private Entity model;
	
	private List<Entity> clones;
	
	private Scene scene;
	
	public Spawner(Scene scene)
	{
		this.scene = scene;
		
		size = 5;
		
		area = new Circle2D.Double(0, 0, 100);
		
		model = new Entity();
		
		clones = new ArrayList<Entity>();
		
		LifetimeComponent life = new LifetimeComponent();
		
		life.getLifetime()
			.setRemaining(10000);
		
		model.add(new GraphicsComponent(),
				  life);
	}

	@Override
	public void update(Duration delta) 
	{
		clones.removeIf(ent -> isDead(ent));
		
		while(clones.size() < size)
		{
			Entity clone = (Entity) model.clone();
			
			Point2D.Double loc = area.randomPoint();
			
			clone.setLoc(loc);
			
			clones.add(clone);
			
			scene.addEntity(clone);
		}
	}
	
	private boolean isDead(Entity ent)
	{
		return ent.get(LifetimeComponent.class)
				  .getLifetime()
				  .isLifeOver();
	}
}