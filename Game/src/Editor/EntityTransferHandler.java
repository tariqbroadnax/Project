package Editor;

import java.awt.geom.Point2D.Double;

import EditorGUI.UndoManager;
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
	
	private UndoManager undoManager;
	
	public EntityTransferHandler(
			EditorResources resources,
			UndoManager undoManager) 
	{
		super(resources, undoManager);
		
		this.resources = resources;
		this.undoManager = undoManager;
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
