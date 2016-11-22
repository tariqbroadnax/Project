package DebugGUI;

import EntityComponent.EntityComponent;
import EntityComponent.GraphicsComponent;
import Game.Entity;

public class GraphicsCompViewer extends EntityCompViewer
{
	public GraphicsCompViewer(Entity entity) 
	{
		super(entity);
		// TODO Auto-generated constructor stub
	
		add(GraphicViewer.viewerOf(()->_comp().getGraphic()));
	}

	@Override
	protected EntityComponent comp() 
	{
		return _comp();
	}
	
	private GraphicsComponent _comp()
	{
		return entity.get(GraphicsComponent.class);
	}
}
