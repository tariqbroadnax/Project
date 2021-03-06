package Editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Editor.entity_selector.EntitySelector;

public class Editor
{	
	public static final EditorResources RESOURCES = new EditorResources();
	
	private EditorFrame frame;
	private Selector selector;
	
	public Editor()	
	{
		frame = new EditorFrame(RESOURCES);
		selector = new Selector(RESOURCES);

		ScenePanel scenePnl = new ScenePanel(RESOURCES);
		
		MultiSplitPane pane = new MultiSplitPane();

		JPanel right = new JPanel();
		JTabbedPane toolPane = new JTabbedPane(),
					editPane = new JTabbedPane();
		
		right.setLayout(new GridLayout(0, 2));
		right.add(toolPane);
		right.add(editPane);
		
		toolPane.add("Entities", new EntitySelector(RESOURCES));
		toolPane.add("Tilesets", selector);
		
		editPane.add("Inspector", new Inspector(RESOURCES));
		
		pane.setCenterComponent(scenePnl);
		pane.setRightComponent(right);
	
		JPanel pnl = new JPanel();
		ToolBar toolbar = new ToolBar(RESOURCES);
		
		pnl.setLayout(new BorderLayout());
		
		pnl.add(toolbar, BorderLayout.NORTH);
		pnl.add(pane, BorderLayout.CENTER);
		
		frame.add(pnl);
		frame.setVisible(true);		
	}
}
