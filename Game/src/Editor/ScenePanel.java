package Editor;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import Editor.headings.ColumnHeading;
import Editor.headings.RowHeading;
import Graphic.Camera;

public class ScenePanel extends JPanel 
{
	private JPanel northPanel;
	
	private RowHeading rowHeading;
	
	public ScenePanel(EditorResources resources)
	{
		setLayout(new BorderLayout());
		
		Camera camera = new Camera();
		
		ColumnHeading colHeading = new ColumnHeading(resources, camera);
		rowHeading = new RowHeading(resources, camera);
		SceneEditor editor = new SceneEditor(resources, camera);
		
		northPanel = new JPanel();
		JButton button = new JButton();
		
		northPanel.setPreferredSize(new Dimension(800, 25));
		button.setPreferredSize(new Dimension(25, 25));
		
		add(editor, BorderLayout.CENTER);
		add(northPanel, BorderLayout.NORTH);
		add(rowHeading, BorderLayout.WEST);
		
		northPanel.setLayout(new BorderLayout());
		
		northPanel.add(button, BorderLayout.WEST);
		northPanel.add(colHeading, BorderLayout.CENTER);
	}
	
	public void setHeadingVisible(boolean visible)
	{
		northPanel.setVisible(visible);
		rowHeading.setVisible(visible);
		
		revalidate();
	}
}