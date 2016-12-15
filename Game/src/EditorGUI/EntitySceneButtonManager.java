package EditorGUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import Entity.Entity;
import Game.Scene;
import Graphic.Camera;

public class EntitySceneButtonManager 
	implements Observer
{
	private GUIResources resources;
	private SceneEditorPane pane;
	
	private Map<Entity, EntitySceneButton> buttons;
	
	public EntitySceneButtonManager(
			GUIResources resources,
			SceneEditorPane pane)
	{
		this.resources = resources;
		
		this.pane = pane;
		
		buttons = new HashMap<Entity, EntitySceneButton>();
	
		Scene scene = resources.getScene();
		
		Camera camera = pane.getCamera();
		
		addButtonForEachVisibleEntity();
		
		camera.addObserver(this);
		scene.addObserver(this);
	}
	
	private void update()
	{
		Camera camera = pane.getCamera();

		List<Entity> visibleEntities = 
				resources.getScene()
						 .entitiesVisibleInsideCamera(camera);
				
		removeInvalidButtons(visibleEntities);
		
		addButtonForEachNewVisibleEntity(
				camera, visibleEntities);	
	}
	
	private void addButtonForEachVisibleEntity()
	{
		Camera camera = pane.getCamera();

		List<Entity> visibleEntities = 
				resources.getScene()
						 .entitiesVisibleInsideCamera(camera);
		
		for(Entity entity : visibleEntities)
		{
			EntitySceneButton button = 
					new EntitySceneButton(
							resources, camera, entity);
			
			pane.add(button);
			
			buttons.put(entity, button);
		
			button.setEnabled(false);
		}
	}
	
	private void addButtonForEachNewVisibleEntity(
			Camera camera,
			List<Entity> visibleEntities)
	{
		for(Entity entity : visibleEntities)
			if(!buttons.containsKey(entity))
			{
				EntitySceneButton button = 
						new EntitySceneButton(
								resources, camera, entity);
				
				pane.add(button);
				
				buttons.put(entity, button);
			
				if(!resources.getSelectionManager()
						     .objectSelected() ||
				   pane.sceneContainsSelectedObject())
					button.setEnabled(true);
				else {
					button.setEnabled(false);
				}

			}
	}

	private void removeInvalidButtons(
			List<Entity> visibleEntities)
	{
		for(Entity buttonEntity : buttons.keySet())
			if(!visibleEntities.contains(buttonEntity))	
				pane.remove(buttons.get(buttonEntity));
		
		buttons.keySet()
		   .removeIf(e -> !visibleEntities.contains(e));
	}

	public void enableAllButtons()
	{
		for(EntitySceneButton button : buttons.values())
		{
			button.setEnabled(true);
		}
	}
	
	public void disableAllButtons()
	{
		for(EntitySceneButton button : buttons.values()) {
			button.setEnabled(false);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		update();
	}
}
