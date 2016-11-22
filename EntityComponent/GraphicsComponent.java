package EntityComponent;

import java.awt.geom.Point2D;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

import Graphic.Graphic;
import Graphic.GraphicsContext;
import Graphic.LayeredGraphic;
import Graphic.ShapeGraphic;

public class GraphicsComponent extends EntityComponent
{	
	private LayeredGraphic decorations;
	
	protected Graphic graphic;
	
	private Collection<TemporaryGraphic> tempGraphics;
	
	private Point2D.Double loc;
			
	public GraphicsComponent()
	{
		decorations = new LayeredGraphic();
		
		graphic = new ShapeGraphic();
		
		tempGraphics = new LinkedList<TemporaryGraphic>();
	
		loc = new Point2D.Double();		
		
		graphic.addObserver(this);	
	}
	
	public GraphicsComponent(GraphicsComponent comp)
	{
		this();
		
		graphic = (Graphic)comp.graphic.clone();
		
		graphic.addObserver(this);
	}

	@Override
	public void update(Duration delta)
	{		
		Point2D.Double myloc = parent.getLoc();
		
		loc = new Point2D.Double(myloc.x, myloc.y);
		
		graphic.update(delta);
		
		//tempGraphics.removeIf(g -> g.getRemainingFrames() == 0);
	}
	
	public void updateGraphicLocation()
	{
		Point2D.Double myloc = parent.getLoc();
		
		loc = new Point2D.Double(myloc.x, myloc.y);
		
		decorations.setLoc(loc);
		graphic.setLoc(loc);
	}
	
	public void addTemporaryGraphic(Graphic graphic, int frames)
	{
		tempGraphics.add(
				new TemporaryGraphic(graphic, frames));
	}
	
	public void paint(GraphicsContext gc)
	{	
		 updateGraphicLocation();
		decorations.paint(gc);
		graphic.paint(gc);
	}
	
	public void setGraphic(Graphic graphic)
	{
		if(graphic == null)
			throw new NullPointerException();
		
		if(this.graphic != null)
			this.graphic.deleteObserver(this);
		
		this.graphic = graphic;
		
		graphic.addObserver(this);
		
		setChanged();
		notifyObservers();
	}
	
	public LayeredGraphic getDecorations()
	{
		return decorations;
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
