package Game;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Comparator;
import java.util.PriorityQueue;

import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Graphic.Graphic;
import Graphic.GraphicsContext;

public class ScenePainter 
	implements Comparator<Graphic>, Serializable
{
	private PriorityQueue<Graphic> visibleGraphics;
	
	private Scene scene;
	
	public ScenePainter(Scene scene)
	{
		this.scene = scene;
		
		visibleGraphics = new PriorityQueue<Graphic>(this);
	}
	
	public void paint(GraphicsContext gc)
	{				
		for(Graphic graph : scene.getGraphics())
		{
			Rectangle2D.Double bound = graph.getBound();
			
			if(gc.camera.shows(bound))
				visibleGraphics.add(graph);
		}
		
		for(Entity e : scene.getEntities(GraphicsComponent.class))
		{
			GraphicsComponent comp = e.get(GraphicsComponent.class);
			Graphic graph = comp.getGraphic();
			
			Rectangle2D.Double bound = graph.getBound();
			
			if(gc.camera.shows(bound))
			{
				comp.updateGraphicLocation();
				visibleGraphics.add(graph);
			}
		}
	
		while(!visibleGraphics.isEmpty())
			visibleGraphics.poll().paint(gc);		
	}
	
	
	@Override
	public int compare(Graphic graph, Graphic graph2) 
	{
		int layer = graph.getLayer(),
			layer2 = graph2.getLayer();
		
		if(layer < layer2)
			return -1;
		else if(layer > layer2)
			return 1;
		else
		{
			Rectangle2D.Double bound = graph.getBound(),
							   bound2 = graph2.getBound();
			
			double bottom = bound.y + bound.height,
				   bottom2 = bound2.y + bound2.height;
			
			if(bottom < bottom2)
				return -1;
			else if(bottom > bottom2)
				return 1;
			else
			{
				if(bound.y > bound2.y)
					return -1;
				else if(bound.y < bound2.y)
					return 1;
				else 
					return 0;
			}
		}
	}

}
