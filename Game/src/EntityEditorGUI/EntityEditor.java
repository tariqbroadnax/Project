package EntityEditorGUI;

import java.awt.BorderLayout;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import EditorGUI.EntityResourceType;
import EditorGUI.EntityWrapper;
import EntityComponent.GraphicsComponent;
import Game.Entity;

public class EntityEditor extends JPanel
{
	private Point2DEditor locEditor;
	
	private Border locEditorBorder;
		
	private Entity entity;
	
	private EntityComponentEditor compEditor;
	
	public EntityEditor()
	{
		this(new EntityWrapper(new Entity(), EntityResourceType.SCENE));
	}
	
	public EntityEditor(EntityWrapper wrapper)
	{
		entity = wrapper.entity;
				
		compEditor = new EntityComponentEditor(entity);
		
		setLayout(new BorderLayout());
		
		add(compEditor, BorderLayout.CENTER);

		if(wrapper.type == EntityResourceType.SCENE) 
		{
			Point2D.Double loc = entity.getLoc();
			locEditor = new Point2DEditor(loc);
	
			locEditorBorder = 
					BorderFactory.createTitledBorder(
							"Location");
			
			locEditor.setBorder(locEditorBorder);
	
			add(locEditor, BorderLayout.NORTH);
		}
				
	}
}
