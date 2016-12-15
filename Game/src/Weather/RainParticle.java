package Weather;

import java.awt.Color;
import java.time.Duration;

import Game.Updatable;
import Graphic.GraphicsContext;
import Graphic.Paintable;

public class RainParticle implements Updatable, Paintable
{
	private double x, y, x2, y2, speed; 
	
	private double angle;
	
	private long lifetime, elapsed;
	
	public RainParticle(int x, int y, int length, double angle, int speed, long lifetime)
	{
		this.x = x; this.y = y;
		
		this.angle = angle;
		this.speed = speed;
		this.lifetime = lifetime;
		
		x2 = x + (length * Math.cos(angle));
		y2 = y + (length * Math.sin(angle));
	}

	@Override
	public void update(Duration delta) 
	{
		double xoffset = (speed * delta.toMillis() / 1000 * Math.cos(angle));
		double yoffset = (speed * delta.toMillis() / 1000 * Math.sin(angle));
		
		x += xoffset; x2 += xoffset;
		y += yoffset; y2 += yoffset;
		
		elapsed += delta.toMillis();
	}
	
	@Override
	public void paint(GraphicsContext gc) 
	{
		gc.g2d.setColor(Color.blue);
		gc.g2d.drawLine((int)x,(int)y,(int)x2,(int)y2);
	}
	
	public boolean isDead()
	{
		return lifetime < elapsed;
	}
}
