package DebugGUI;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import Game.Entity;

public class EntityViewerFrame extends JFrame
{
	private EntityViewer viewer;
	
	public EntityViewerFrame(Entity entity)
	{
		super("Entity Viewer");
		
		viewer = new EntityViewer(entity);	
		
		add(new JScrollPane(viewer, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
			
		pack();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}
}
