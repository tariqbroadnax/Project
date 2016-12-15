package Weather;

import java.awt.geom.AffineTransform;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

import Game.Updatable;
import Graphic.GraphicsContext;
import Graphic.Paintable;

public class RainManager implements Updatable, Paintable
{	
	private Collection<RainParticle> particles;

	private long spawnDelay, elapsed;
	
	private Random rand;
	
	public RainManager()
	{
		particles = new LinkedList<RainParticle>();
			
		spawnDelay = 125;
		elapsed = 0;

		rand = new Random();
	}
	
	@Override
	public void paint(GraphicsContext gc) 
	{
		AffineTransform trans = gc.g2d.getTransform();
		gc.g2d.setTransform(new AffineTransform());
		particles.forEach(p -> p.paint(gc));

		gc.g2d.setTransform(trans);
	}

	@Override
	public void update(Duration delta) 
	{
		particles.removeIf(p -> p.isDead());
		particles.forEach(p -> p.update(delta));
	
		elapsed += delta.toMillis();
		
		if(elapsed > spawnDelay)
		{
			for(int x = 0; x < 2000; x += 25)
				if(rand.nextDouble() < 0.5)
				 particles.add(new RainParticle(x, 0, 50, 3*Math.PI/4, 1000, 3000));
			elapsed = 0;
		}
	}
}
