package EditorGUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JPanel;

import Entity.Entity;
import EntityComponent.GraphicsComponent;

public class EntityContainer extends JPanel
	implements ResourceListener
{
	private GUIResources resources;
	
	private Collection<EntitySelectionButton> buttons;
	
	public EntityContainer(GUIResources resources)
	{
		this.resources = resources;
		
		formatComponent();
			
		buttons = new LinkedList<EntitySelectionButton>();
		
		resources.addResourceListener(this);
	
		Entity entity = new Entity();
		entity.add(new GraphicsComponent());
		
		resources.addEntity(entity);
	}
	
	private void formatComponent()
	{
		setBackground(Color.white);
		
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		setLayout(layout);
	}
	
	public void addEntity(Entity entity)
	{
		EntitySelectionButton button =
				new EntitySelectionButton(resources, entity);
		
		add(button);
	}
	
	public void removeEntity(Entity template)
	{
		revalidate();
		repaint();
	}

	@Override
	public void sceneChanged(GUIResources src) {}

	@Override
	public void objectSelected(GUIResources src) {}

	@Override
	public void entityAdded(
			GUIResources src, Entity entity) 
	{
		addEntity(entity);
	}

	@Override
	public void entityRemoved(
			GUIResources src, Entity entity) 
	{
		removeEntity(entity);
	}

	@Override
	public void sceneLoaded(GUIResources src) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sceneSaved(GUIResources src) {
		// TODO Auto-generated method stub
		
	}
}
