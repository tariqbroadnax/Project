package Editor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Editor.layer_selector.LayerSelectorPanel;

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
	private SceneEditor sceneEditor;
	private Selector selector;
	
	public EditorGUI()	
	{
		resources = new EditorResources();
		
		frame = new EditorFrame(resources);
		selector = new Selector(resources);
		menuBar = new EditorMenuBar(selector);
		sceneEditor = new SceneEditor(resources);
		
		MultiSplitPane pane = new MultiSplitPane();
		
		JPanel right = new JPanel();
		right.setLayout(new BorderLayout());
		right.add(selector, BorderLayout.CENTER);
		right.add(new LayerSelectorPanel(resources.scene), BorderLayout.NORTH);
		
		
		pane.setCenterComponent(sceneEditor);
		pane.setRightComponent(right);
		
		frame.add(pane);
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
