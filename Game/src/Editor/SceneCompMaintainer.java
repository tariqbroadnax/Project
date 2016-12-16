package Editor;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

import Editor.comp.TileMapComp;
import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Graphic.Camera;
import Graphic.Graphic;
import Tileset.TileMap;

public class SceneCompMaintainer implements SceneListener
{
	/* Each entity and graphic in a scene has a JComponent in 
	 * the scene editor. This class is responsible for making sure they
	 * are created and disposed properly. */
	
	private Map<Graphic, JComponent> graphMap;
	private Map<Entity, JComponent> entMap;
	
	private EditorResources resources;
	private Camera camera;
	private SceneEditor editor;

	public SceneCompMaintainer(
			EditorResources resources,
			Camera camera, SceneEditor editor)
	{
		this.resources = resources;
		this.camera = camera;
		this.editor = editor;
		
		graphMap = new HashMap<Graphic, JComponent>();
		entMap = new HashMap<Entity, JComponent>();
		
		resources.addSceneListener(this);
		
		checkAndMaintainGraphComps();
		checkAndMaintainEntComps();
	}

	@Override
	public void sceneChanged() 
	{
		checkAndMaintainGraphComps();
		checkAndMaintainEntComps();
	}

	private void checkAndMaintainGraphComps()
	{
		List<Graphic> sceneGraphs = resources.scene.getGraphics(),
					  visGraphs = new ArrayList<Graphic>(),
					  nonvisGraphs = new ArrayList<Graphic>();	
		
		findVisGraphs(sceneGraphs, visGraphs, nonvisGraphs);
		createVisGraphComps(visGraphs);
		removeRemovedAndNonvisGraphComps(sceneGraphs, nonvisGraphs);
	}

	private void checkAndMaintainEntComps()
	{
		List<Entity> visEnts = new ArrayList<Entity>(),
				  nonvisEnts = new ArrayList<Entity>(),
				  ents = resources.scene.getEntities(GraphicsComponent.class);		
		
		
		findVisEnts(ents, visEnts, nonvisEnts);
		createVisEntComps(visEnts);
		removeRemovedAndNonvisEntComps(ents, nonvisEnts);
	}

	private void findVisGraphs(
			List<Graphic> sceneGraphs,
			List<Graphic> visGraphs, List<Graphic> nonvisGraphs)
	{
		for(Graphic graph : sceneGraphs)
		{
			Rectangle2D.Double bound = graph.getBound();
			
			if(camera.shows(bound))
				visGraphs.add(graph);
			else
				nonvisGraphs.add(graph);
		}
	}
	
	private void findVisEnts(
			List<Entity> ents, 
			List<Entity> visEnts, List<Entity> nonvisEnts)
	{
		for(Entity ent : ents)
		{
			Rectangle2D.Double bound = ent.get(GraphicsComponent.class)
										  .getGraphic()
										  .getBound();
			
			if(camera.shows(bound))
				visEnts.add(ent);
			else
				nonvisEnts.add(ent);
		}
	}

	private void createVisGraphComps(List<Graphic> visGraphs)
	{
		for(Graphic graph : visGraphs)
		{
			if(!graphMap.containsKey(graph))
			{
				JComponent comp = graphComp(graph);
				
				editor.add(comp);
				graphMap.put(graph, comp);
			}
		}
	}
	
	private void createVisEntComps(List<Entity> visEnts)
	{
		for(Entity ent : visEnts)
		{
			if(!entMap.containsKey(ent))
			{
				JComponent comp = entComp(ent);
			
				editor.add(comp);
				entMap.put(ent, comp);
			}
		}
	}

	private void removeRemovedAndNonvisGraphComps(
			List<Graphic> sceneGraphs,
			List<Graphic> nonvisGraphs)
	{
		List<Graphic> graphsToRemove = new ArrayList<Graphic>();
		
		for(Graphic graph : graphMap.keySet())
		{
			if(!sceneGraphs.contains(graph) ||
				nonvisGraphs.contains(graph))
			{
				graphsToRemove.add(graph);
				editor.remove(graphMap.get(graph));
			}
		}
		
		graphMap.keySet()
				.removeAll(graphsToRemove);
	}
	
	private void removeRemovedAndNonvisEntComps(
			List<Entity> ents, List<Entity> nonvisEnts)
	{
		List<Entity> entsToRemove = new ArrayList<Entity>();
		
		for(Entity ent : entMap.keySet())
		{
			if(!ents.contains(ent) ||
				nonvisEnts.contains(ent))
			{
				entsToRemove.add(ent);
				editor.remove(entMap.get(ent));
			}
		}
		
		entMap.keySet()
				.removeAll(entsToRemove);
	}
	
	private JComponent graphComp(Graphic graph)
	{
		if(graph instanceof TileMap)
		{
			TileMapComp comp = new TileMapComp(resources, (TileMap)graph, camera);
			return comp;
		}
		
		return null;
	}
	
	private JComponent entComp(Entity ent)
	{
		return null;
	}
}