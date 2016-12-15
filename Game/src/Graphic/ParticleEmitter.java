package Graphic;

import java.awt.geom.Rectangle2D.Double;
import java.awt.geom.RectangularShape;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import Maths.Circle2D;
import Maths.Maths;
import Maths.Range;

public class ParticleEmitter extends Graphic
{
	private long spawnDelay, elapsed;
	private Range.Double velX, velY;
	private Range.Long lifetime;
	private RectangularShape shape;
	
	private List<Particle> particles;
	
	public ParticleEmitter()
	{
		spawnDelay = 50; elapsed = 0;
		velX = new Range.Double(-10, 10);
		velY = new Range.Double(-10, 10);
		lifetime = new Range.Long(4000, 10000);
		
		particles = new ArrayList<Particle>();
	
		shape = new Circle2D.Double(-2.5, -2.5, 2.5);
	}
	
	public ParticleEmitter(ParticleEmitter p)
	{
		// FIXME
		spawnDelay = 50; elapsed = 0;
		velX = new Range.Double(-10, 10);
		velY = new Range.Double(-10, 10);
		lifetime = new Range.Long(4000, 10000);
		
		particles = new ArrayList<Particle>();
	
		shape = new Circle2D.Double(-2.5, -2.5, 2.5);
	}

	
	public void update(Duration delta) 
	{
		super.update(delta);
		
		for(Particle p : particles)
			p.update(delta);
	
		particles.removeIf(p -> p.isDead());
		
		elapsed += delta.toMillis();
		
		while(elapsed > spawnDelay)
		{
			elapsed -= spawnDelay;
			
			particles.add(createParticle());
		}
	}
	
	private Particle createParticle()
	{
		Particle p = new Particle();
		
		p.setLoc(loc.x, loc.y);
		
		double velX = this.velX.rand();
		double velY = this.velY.rand();
		
		long lifetime = this.lifetime.rand();
	
		p.setVelocity(velX, velY);
		p.setLifetime(lifetime);
		p.setShape(shape);
		
		return p;
	}
	
	@Override
	protected void _paint(GraphicsContext gc) 
	{
	    //gc.g2d.setComposite(AddComposite.instance);

		for(Particle p : particles)
			p.paint(gc);
		
		//gc.g2d.setComposite(AlphaComposite.SrcOver);
	}

	@Override
	public Double getBound() {
		//FIXME
		return null;
	}

	@Override
	public Object clone() {
		return new ParticleEmitter(this);
	}
}
