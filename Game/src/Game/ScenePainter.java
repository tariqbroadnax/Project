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
	implements Comparator<GraphicsComponent>, Serializable
{
	private PriorityQueue<GraphicsComponent> comps;
	
	private Scene scene;
	
	public ScenePainter(Scene scene)
	{
		this.scene = scene;
		
		comps = new PriorityQueue<GraphicsComponent>(this);
	}
	
	public void paint(GraphicsContext gc)
	{				
		for(Entity e : scene.getEntities(GraphicsComponent.class))
		{
			GraphicsComponent comp = e.get(GraphicsComponent.class);
			
			comps.add(comp);
		}
		
		while(!comps.isEmpty())
		{	
			GraphicsComponent comp = comps.poll();
			
			comp.paint(gc);
		}
	}
	
	
	@Override
	public int compare(GraphicsComponent comp1, GraphicsComponent comp2) 
	{
		Graphic graph = comp1.getGraphic(),
				graph2 = comp2.getGraphic();
		
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
			
			if(bound == null)
				return -1;
			else if(bound2 == null)
				return 1;
			else
			{
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

}
