package EntityEditorGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import EditorGUI.ComponentShownListener;
import EditorGUI.MouseListener;
import EditorGUI.MouseMotionListener;
import EntityComponent.EntityComponent;
import EntityComponent.GraphicsComponent;
import Game.Entity;
import Game.EntityListener;

public class EntityComponentEditor extends JTabbedPane
	implements MouseListener, MouseMotionListener,
			   EntityListener
{	
	private JPopupMenu menu;
		
	private JMenuItem graphicsMenu,
					  movementMenu;
	
	private GraphicsComponentEditor gCompEditor;
	 
	private Entity entity;

	public EntityComponentEditor(Entity entity)
	{
		menu = new JPopupMenu();
		
		graphicsMenu = new JMenuItem("Graphics");
		movementMenu = new JMenuItem("Movement");
	
		graphicsMenu.addActionListener(
				e -> addComponent(GraphicsComponent.class));
	
		gCompEditor = new GraphicsComponentEditor();
		
		setEntity(entity);
		
		int addTabNum = getTabCount() - 1;
		setEnabledAt(addTabNum, false);

		addMouseListener(this);
		addMouseMotionListener(this);
	}

	private void addComponent(Class<? extends EntityComponent> c)
	{
		if(c.equals(GraphicsComponent.class))
			entity.add(new GraphicsComponent());
		
		int addTabNum = getTabCount() - 1;
		setEnabledAt(addTabNum, true);

		updateTabs();
		
		addTabNum = getTabCount() - 1;
		setEnabledAt(addTabNum, false);	
	}
	
	private void updateTabs()
	{
		menu.removeAll();
		removeAll();
		
		if(entity.contains(GraphicsComponent.class))
		{
			GraphicsComponent gComp = 
					entity.get(GraphicsComponent.class);
			
		
			gCompEditor.setGraphicsComponent(gComp);
			
			JScrollPane scrollPane = 
					new JScrollPane(gCompEditor);
			
			scrollPane.setVerticalScrollBarPolicy(
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			
			scrollPane.setHorizontalScrollBarPolicy(
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				
			ComponentShownListener list = 
					e ->  
			{
				Dimension prefSize =
						scrollPane.getViewport()
								  .getSize();
				
				prefSize.height = 
						gCompEditor.getPreferredSize().height;
				
				gCompEditor.setPreferredSize(prefSize);
				
				revalidate();
			};
	
			scrollPane.addComponentListener(list);
			
			addTab("G", scrollPane); 	
		}
		else			
			menu.add(graphicsMenu);		

		add("", new JPanel());
		
		revalidate();
	}
	
	public void setEntity(Entity entity)
	{
		if(entity != null)
			entity.removeEntityListener(this);
		
		this.entity = entity;

		entity.addEntityListener(this);
		
		updateTabs();
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		int addTabNum = getTabCount() - 1 ;
		
		Rectangle addTabBound = 
				getUI().getTabBounds(this, addTabNum);
						
		Point mouseLoc = e.getPoint();
		
		Color color = getBackground();
		
		
		if(addTabBound.contains(mouseLoc))
			color = Color.white;
		
		setBackgroundAt(addTabNum, color);
				
		//(int tabNum = 0; tabNum < getTabCount(); tabCount++)
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		int addTabNum = getTabCount() - 1 ;
		
		Rectangle addTabBound = 
				getUI().getTabBounds(this, addTabNum);
						
		Point mouseLoc = e.getPoint();
		
		if(addTabBound.contains(mouseLoc))
			menu.show(this, addTabBound.x, addTabBound.height);
	}

	@Override
	public <E extends EntityComponent> void componentAdded(
			Entity src, Class<E> c) 
	{
		updateTabs();
	}

	@Override
	public <E extends EntityComponent> void componentRemoved(
			Entity src, Class<E> c) 
	{
		updateTabs();
	}

}
