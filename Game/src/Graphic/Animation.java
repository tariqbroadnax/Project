package Graphic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

import Utilities.CircularIterator;

public class Animation extends Graphic
{
	private Collection<Graphic> graphics;
	
	private long delay, elapsed;
	
	private Graphic curr;
	
	private CircularIterator<Graphic> iter;

	public Animation()
	{
		graphics = new LinkedList<Graphic>();
	
		elapsed = 0;	
		delay = 100;
		
		iter = new CircularIterator<Graphic>(graphics);
	}
	
	public Animation(Animation ani)
	{
		elapsed = 0;		
		delay = ani.delay;
		
		for(Graphic graph : ani.graphics)
			graphics.add((Graphic)graph.clone());

		iter = new CircularIterator<Graphic>(graphics);	
	}
		
	public void update(Duration delta)
	{		
		elapsed += delta.toMillis();
		
		while(elapsed > delay)
		{
			curr = iter.next();
			elapsed -= delay;	
		}
	
		for(Graphic graph : graphics)
			graph.update(delta);
	}
	
	@Override
	protected void _paint(GraphicsContext gc) {
		curr.paint(gc);
	}
	
	public void reset()
	{
		iter.reset();
		elapsed = 0;
		curr = iter.next();
	}

	public void clear() {
		graphics.clear();
	}

	public void addGraphic(Graphic graph)
	{
		graphics.add(graph);
		
		iter = new CircularIterator<Graphic>(graphics);
		curr = iter.next();
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	@Override
	public void setLoc(Point2D.Double loc) 
	{
		super.setLoc(loc);
		
		for(Graphic graphic : graphics)
			graphic.setLoc(loc);
	}
	
	@Override
	public Rectangle2D.Double getBound() {	
		return curr.getBound();
	}
 	
	@Override
	public Object clone() {
		return new Animation(this);
	}	
}
