package Editor;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Editor.comp.Guage;
import Editor.entity_selector.EntitySelector;

public class EditorGUI 
{	
	/*private EditorFrame frame;
	
	private MultiSplitPane pane;
	
	private SceneEditorPane sceneEditor;
	private TilesetPane tilePane;
	private EntityContainer entityContainer;
	
	private SceneExplorer explorer;
	private Inspector inspector;
	
	private GUIResources resources;
	*/
	
	private EditorResources resources;
	
	private EditorFrame frame;
	private EditorMenuBar menuBar;
	private Selector selector;
	
	public EditorGUI()	
	{
		resources = new EditorResources();
		
		frame = new EditorFrame(resources);
		selector = new Selector(resources);
		menuBar = new EditorMenuBar(selector);

		ScenePanel scenePnl = new ScenePanel(resources);
		
		MultiSplitPane pane = new MultiSplitPane();

		Guage guage = new Guage();
		guage.setSize(400, 400);
		
		//Accordion acc = new Accordion();
		//acc.add(selector);
		//acc.add(guage);
		
		JPanel right = new JPanel();
		JTabbedPane toolPane = new JTabbedPane(),
					editPane = new JTabbedPane();
		
		right.setLayout(new GridLayout(0, 2));
		right.add(toolPane);
		right.add(editPane);
		
		toolPane.add("Entities", new EntitySelector(resources));
		toolPane.add("Tilesets", selector);
		
		editPane.add("Inspector", new Inspector(resources));
		
		//right.add(acc, BorderLayout.CENTER);
		//right.add(new EntitySelector(resources), BorderLayout.NORTH);
			
		pane.setCenterComponent(scenePnl);
		pane.setRightComponent(right);
	
		JPanel pnl = new JPanel();
		ToolBar toolbar = new ToolBar(resources);
		
		pnl.setLayout(new BorderLayout());
		
		pnl.add(toolbar, BorderLayout.NORTH);
		pnl.add(pane, BorderLayout.CENTER);
		
		frame.add(pnl);
		frame.setVisible(true);

		/*resources = new GUIResources();
		
		frame = new EditorFrame(resources);
		
		sceneEditor = new SceneEditorPane(resources);
		tilePane = new TilesetPane(resources);
		entityContainer = new EntityContainer(resources);

		explorer = new SceneExplorer(resources);
		inspector = new Inspector(resources);		
		pane = new MultiSplitPane();
		
		
		pane.setLeftComponent(explorer);
		pane.setCenterComponent(sceneEditor);
		pane.setBottomComponent(tilePane);
		pane.setRightComponent(inspector);				
		
	    JPanel content = (JPanel)frame.getContentPane();

	 	content.add(pane, BorderLayout.CENTER);
	 	
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		tilePane.setPreferredSize(new Dimension(600, 100));
		entityContainer.setPreferredSize(new Dimension(600, 100));
		frame.setSize(800, 600);

		frame.setVisible(true);
		
		try {
			resources.getImagePool()
					 .importImage("GrassTileSet.png", 2, 1);
				
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
	}
}
