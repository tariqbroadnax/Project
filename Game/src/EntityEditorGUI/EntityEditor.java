package EntityEditorGUI;

import java.awt.BorderLayout;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Entity.Entity;

public class EntityEditor extends JPanel
	implements ChangeListener
{
	private Point2DEditor locEditor;
	
	private Border locEditorBorder;
		
	private Entity entity;
	
	private EntityComponentEditor compEditor;
	
	public EntityEditor()
	{
		this(new Entity());
	}
	
	public EntityEditor(Entity entity)
	{
		this.entity = entity;
				
		compEditor = new EntityComponentEditor(entity);
		
		locEditor = new Point2DEditor();

		setLayout(new BorderLayout());

		Point2D.Double loc = entity.getLoc();

		locEditor.setPoint2DValue(loc);
		
		locEditorBorder = 
				BorderFactory.createTitledBorder(
						"Location");
		
		locEditor.setBorder(locEditorBorder);

		add(compEditor, BorderLayout.CENTER);
		add(locEditor, BorderLayout.NORTH);	
		
		locEditor.addChangeListener(this);
	}
	
	public void setEntity(Entity entity)
	{
		this.entity = entity;
		
		Point2D.Double entityLoc = entity.getLoc();

		locEditor.setPoint2DValue(entityLoc);
		
		compEditor.setEntity(entity);
	}
	
	@Override
	public void fieldChanged() 
	{
		Point2D.Double pt = 
				locEditor.getPoint2DValue();
		
		entity.setLoc(pt);
	}
}
