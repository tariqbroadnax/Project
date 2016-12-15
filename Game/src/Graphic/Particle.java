package Graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Rectangle2D.Double;
import java.awt.geom.RectangularShape;
import java.time.Duration;

import Maths.Circle2D;
import Maths.Vector2D;

public class Particle extends Graphic
{
	private Paint paint;
	private Vector2D.Double vel;
	private long lifespan;
	private RectangularShape shape;
	
	public Particle()
	{
		paint = Color.red;
		vel = new Vector2D.Double();
		lifespan = 1000;
		shape = new Circle2D.Double(0, 0, 2.5);
	}
	
	public Particle(Particle p)
	{
		paint = p.paint;
		vel = new Vector2D.Double(p.vel);
		// FIXME 
		lifespan = 1000;
		shape =  new Circle2D.Double(0, 0, 2.5);
	}
	
	public void update(Duration delta)
	{
		super.update(delta);
		
		double t = delta.toNanos() / 1E9;

		loc.x += vel.x * t;
		loc.y += vel.y * t;
		
		lifespan -= delta.toMillis();
	}
	
	@Override
	protected void _paint(GraphicsContext gc) 
	{
		//System.out.println(screenLoc);
		Graphics2D g2d = (Graphics2D)gc.g2d.create();
		
		Dimension size = gc.camera.viewSize(); 
		
		double scaleX = size.width / 100.0,
			   scaleY = size.height / 100.0;
	
		g2d.scale(scaleX, scaleY);

		g2d.setPaint(paint);
	
		g2d.translate(loc.x, loc.y);
	
		g2d.fill(shape);
		
		g2d.translate(-loc.x, -loc.y);

		g2d.dispose();
	}
	
	public void setShape(RectangularShape shape) {
		this.shape = shape;
	}
	
	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	
	public void setVelocity(double velX, double velY) {
		vel.x = velX;
		vel.y = velY;
	}
	
	public void setLifetime(long lifespan) {
		this.lifespan = lifespan;
	}
	
	public boolean isDead() {
		return lifespan < 0;
	}

	@Override
	public Double getBound() {
		return (Double)shape.getBounds2D();
	}

	@Override
	public Object clone() {
		return new Particle(this);
	}
}
