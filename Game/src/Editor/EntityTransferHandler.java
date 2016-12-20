package Editor;

import java.awt.geom.Point2D.Double;

import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Graphic.Graphic;
import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;

public class EntityTransferHandler 
	extends SelectionTransferHandler
{
	private EditorResources resources;
	
	private Entity ent;
	
	public EntityTransferHandler(EditorResources resources) 
	{
		super(resources);
		
		this.resources = resources;
	}

	@Override
	public boolean canImport(Object obj) 
	{
		return (obj instanceof Entity &&
				!resources.sceneSelection);
	}

	@Override
	public void setSelection(Object obj) 
	{
		ent = (Entity)obj;
	}

	@Override
	public void importSelection(Double normLoc) 
	{
		Entity clone = (Entity)ent.clone();
		
		clone.setLoc(normLoc);
				
		resources.scene.addEntityNow(clone);
		
		resources.notifyOfSceneChange();
	}

	@Override
	public void paintSelection(GraphicsContext gc, Double normLoc) 
	{
		Graphic graph;		
		if(ent.contains(GraphicsComponent.class))
		{
			graph = ent.get(GraphicsComponent.class)
							   .getGraphic();
		}
		else
			graph = new ShapeGraphic();
		
		
		graph.setLoc(normLoc);
			
		graph.paint(gc);
	}

}