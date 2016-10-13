package EditorGUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

public class EditorGUI 
{	
	private MultiSplitPane pane;
	
	private TileMapEditor tileMapEditor;
	private ImageChooser tileChooser;
	private EntitySelectionPane entityPane;
		
	private Inspector inspector;
	
	private GUIResources resources;
	
	public EditorGUI()	
	{
		resources = new GUIResources();
		
		tileMapEditor = new TileMapEditor(resources);
		tileChooser = new ImageChooser(resources);
		entityPane = new EntitySelectionPane(resources);

		inspector = new Inspector(resources);
		
		pane = new MultiSplitPane();
		
		pane.setCenterComponent(tileMapEditor);
		pane.setBottomComponent(entityPane);
		pane.setRightComponent(inspector);				
		
	 	resources.frame.add(pane);
		resources.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tileChooser.setPreferredSize(new Dimension(600, 100));
		entityPane.setPreferredSize(new Dimension(600, 100));
		resources.frame.setSize(800, 600);

		resources.frame.setVisible(true);
		
		try {
			resources.getImagePool()
					 .importImage("GrassTileSet.png", 2, 1);
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
