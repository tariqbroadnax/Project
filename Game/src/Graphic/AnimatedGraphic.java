package Graphic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

import Utilities.CircularIterator;

public class AnimatedGraphic extends Graphic
{
	private Collection<Graphic> graphics;
	
	private Duration delay, elapsed;
	
	private Graphic curr;
	
	private CircularIterator<Graphic> iter;

	public AnimatedGraphic()
	{
		graphics = new LinkedList<Graphic>();
	
		elapsed = Duration.ZERO;
		
		delay = Duration.ofMillis(500);
	}
	
	public AnimatedGraphic(AnimatedGraphic aniGraph)
	{
		this();
				
		elapsed = aniGraph.elapsed;
		
		delay = aniGraph.delay;
		
		for(Graphic graph : aniGraph.graphics)
			graphics.add(graph._clone());

		int pos = aniGraph.iter.getPosition();
		
		iter = new CircularIterator<Graphic>(
				graphics, pos - 1);	
		
		curr = iter.next();
	}
		
	public void update(Duration delta)
	{		
		elapsed = elapsed.plus(delta);
				
		while(elapsed.compareTo(delay) > 0)
		{
			curr = iter.next();
			elapsed = elapsed.minus(delay);	
		}
	
		for(Graphic graph : graphics)
			graph.update(delta);
	}
	
	public void reset()
	{
		iter = new CircularIterator<Graphic>(graphics);
		
		elapsed = Duration.ZERO;
		
		curr = iter.next();
	}
		
	@Override
	protected void _paint(GraphicsContext gc) 
	{
		curr.paint(gc);
	}
	
	public void setDelay(Duration delay)
	{
		this.delay = delay;
	}
	
	public void addGraphic(Graphic graph)
	{
		graphics.add(graph);
		
		iter = new CircularIterator<Graphic>(graphics);
		curr = iter.next();
	}
	
	@Override
	public void setLoc(Point2D.Double loc)
	{
		super.setLoc(loc);
		
		for(Graphic graphic : graphics)
			graphic.setLoc(loc);
	}
	
	public void clear()
	{
		graphics.clear();
	}
	
	@Override
	public Rectangle2D.Double getBound()
	{	
		return curr.getBound();
	}
 	
	@Override
	protected Graphic _clone() 
	{
		return new AnimatedGraphic(this);
	}	
	
	
}
