package EditorGUI;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import Entity.Entity;
import Game.Scene;

public class SceneExplorer extends JPanel
{
	private JTree tree;
	
	private GUIResources resources;
	
	private DefaultMutableTreeNode top;
    
	private SceneExplorerPopupMenu popupMenu;
	
	public SceneExplorer(GUIResources resources) 
	{
		this.resources = resources;

	    top = new DefaultMutableTreeNode("Scene");
	    
	    tree = new JTree(top);
	
	    popupMenu = new SceneExplorerPopupMenu();
	    
	    updateSceneNode();
	    
	    add(tree);
	    
	    setBackground(Color.white);
	
	    setPreferredSize(new Dimension(200, 600));
	
	    setComponentPopupMenu(popupMenu);
	}
	
	private void updateSceneNode() 
	{
		top.removeAllChildren();
		
		Scene scene = resources.getScene();
		
		int i = 0;
		for(Entity entity : scene.getEntities())
			addEntityNode(entity, i++);
		
		addEntityNode(new Entity(), i);
	}
	
	private void addEntityNode(Entity entity, int i) 
	{
		DefaultMutableTreeNode entityNode =
				new DefaultMutableTreeNode(
						"Entity " + i);
		
		top.add(entityNode);
	}
	
	
}
