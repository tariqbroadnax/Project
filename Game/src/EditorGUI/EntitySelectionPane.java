package EditorGUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JPanel;

import Game.Entity;

public class EntitySelectionPane extends JPanel
	implements ResourceListener
{
	private GUIResources resources;
	
	private Collection<EntitySelectionButton> buttons;
	
	public EntitySelectionPane(GUIResources resources)
	{
		this.resources = resources;
		
		formatComponent();
			
		buttons = new LinkedList<EntitySelectionButton>();
		
		resources.addResourceListener(this);
	
		resources.addEntity(new Entity());
	}
	
	private void formatComponent()
	{
		setBackground(Color.white);
		
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		setLayout(layout);
	}
	
	public void addEntity(Entity template)
	{
		EntitySelectionButton newButton =
				new EntitySelectionButton(resources, template);
		
		Collection<EntitySelectionButton> temp =
				new LinkedList<EntitySelectionButton>();
		
		int newHashCode = template.hashCode();
		
		for(EntitySelectionButton button : buttons)
		{
			int hashCode = button.getEntity()
								 .hashCode();
			
			if(newHashCode > hashCode)
			{
				temp.add(button);
				buttons.remove(button);
				remove(button);
			}
		}
		
		add(newButton);
		buttons.add(newButton);
		
		for(EntitySelectionButton button : temp)
		{
			buttons.add(button);
			add(button);
		}
		
		revalidate();
		repaint();
		
		// remove all comps with lesser id than new entity
		// add new entity
		// add previous comps
	}
	
	public void removeEntity(Entity template)
	{
		EntitySelectionButton buttonToRemove = null;
		
		for(EntitySelectionButton button : buttons)
			if(button.getEntity() == template)
				buttonToRemove = button;
			
		if(buttonToRemove != null)
		{
			buttons.remove(buttonToRemove);
			remove(buttonToRemove);
		}
		
		revalidate();
		repaint();
	}

	@Override
	public void sceneChanged(GUIResources src) {}

	@Override
	public void tilesChanged(GUIResources src) {}

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
}
