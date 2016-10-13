package EntityComponent;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

import Graphic.Graphic;
import Graphic.GraphicsComponentListener;
import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;

public class GraphicsComponent extends EntityComponent
{	
	protected Graphic graphic;
	
	private transient Collection<TemporaryGraphic> tempGraphics;
	
	private Point2D.Double loc;
	
	private Collection<GraphicsComponentListener> listeners;
	
	public GraphicsComponent()
	{
		graphic = new ShapeGraphic();
		
		tempGraphics = new LinkedList<TemporaryGraphic>();
	
		loc = new Point2D.Double();		
	
		listeners = new LinkedList<GraphicsComponentListener>();
	}
	
	public GraphicsComponent(GraphicsComponent comp)
	{
		this();
		
		graphic = (Graphic)comp.graphic.clone();
	}

	@Override
	public void update(Duration delta)
	{		
		Point2D.Double myloc = parent.getLoc();
		
		loc = new Point2D.Double(myloc.x, myloc.y);
		
		graphic.update(delta);
		
		//tempGraphics.removeIf(g -> g.getRemainingFrames() == 0);
	}
	
	public void addTemporaryGraphic(Graphic graphic, int frames)
	{
		tempGraphics.add(
				new TemporaryGraphic(graphic, frames));
	}
	
	public void paint(GraphicsContext gc)
	{
		Point2D.Double myloc = parent.getLoc();
		
		loc = new Point2D.Double(myloc.x, myloc.y);
		
		graphic.setLoc(loc);
		graphic.paint(gc);
	}
	
	public void addGraphicsComponentListener(
			GraphicsComponentListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeGraphicsComponentListener(
			GraphicsComponentListener listener)
	{
		listeners.remove(listener);
	}
	
	public void setGraphic(Graphic graphic)
	{
		if(this.graphic != graphic)
		{
			notifiyListenersOfGraphicChanged(
					graphic, this.graphic);
			
			this.graphic = graphic;
		}
	}
	
	private void notifiyListenersOfGraphicChanged(
			Graphic newGraphic, Graphic oldGraphic)
	{
		for(GraphicsComponentListener listener :
									  listeners)
			listener.graphicChanged(
					this, newGraphic, oldGraphic);
	}
	
	public Graphic getGraphic()
	{
		return graphic;
	}
	
	@Override
	protected EntityComponent _clone()
	{
		return new GraphicsComponent(this);
	}
	
	public String toString()
	{
		return super.toString() + " graphic: " + graphic.toString();
	}
}
