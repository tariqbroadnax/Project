package Editor;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import Editor.comp.ScenePlayer;
import Editor.headings.ColumnHeading;
import Editor.headings.RowHeading;
import Editor.headings.SelectAllButton;

public class ScenePanel extends JPanel 
	implements SceneListener
{
	private JPanel northPanel;
	
	private RowHeading rowHeading;
	
	private SceneEditor editor;
	private ScenePlayer player;
	
	public ScenePanel(EditorResources resources)
	{
		setLayout(new BorderLayout());
		
		ColumnHeading colHeading = new ColumnHeading(resources);
		rowHeading = new RowHeading(resources);
		editor = new SceneEditor(resources);
		player = new ScenePlayer(resources);
		
		northPanel = new JPanel();
		JButton button = new SelectAllButton(resources);
		
		northPanel.setPreferredSize(new Dimension(800, 25));
		button.setPreferredSize(new Dimension(25, 25));
		
		add(editor, BorderLayout.CENTER);
		add(northPanel, BorderLayout.NORTH);
		add(rowHeading, BorderLayout.WEST);
		
		northPanel.setLayout(new BorderLayout());
		
		northPanel.add(button, BorderLayout.WEST);
		northPanel.add(colHeading, BorderLayout.CENTER);
		
		resources.addSceneListener(this);
	}
	
	public void setHeadingVisible(boolean visible)
	{
		northPanel.setVisible(visible);
		rowHeading.setVisible(visible);
		
		revalidate();
	}
	
	@Override
	public void scenePlayerStarted() 
	{		
		remove(editor);
		add(player, BorderLayout.CENTER);
	
		revalidate();
		repaint();
		
		player.start();
	}
	
	@Override
	public void scenePlayerStopped() 
	{		
		player.stop();
		remove(player);
		add(editor, BorderLayout.CENTER);

		revalidate();
		repaint();
	}
}
