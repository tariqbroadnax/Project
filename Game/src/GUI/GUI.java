package GUI;


import java.awt.Font;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.KeyStroke;
import javax.swing.plaf.FontUIResource;

import Actions.AbilityAction;
import Actions.MouseIconUpdater;
import Game.GameResources;
import Utilities.Components;

public class GUI
{
	private GFrame frame;
	
	private GLayeredPane layeredPane;
	
	private HUD hud;
	
	private DebugPanel debugPanel;
	
	private GameResources resources;
	
	public GUI(GameResources resources)
	{
		this.resources = resources;
		
		frame = new GFrame(resources.updater);
		
		layeredPane = new GLayeredPane();
			
		hud = new HUD();
		
		debugPanel = new DebugPanel(resources);
		
		frame.getRootPane().setOpaque(false);
		frame.setContentPane(layeredPane);

		layeredPane.addAndSetLayer(debugPanel, 2);
		
		frame.addMouseListener(resources.miHandler);
		frame.addMouseMotionListener(resources.miHandler);
		
		frame.addMouseMotionListener(
				new MouseIconUpdater(resources));
		
		addActions();
		
		FontUIResource font = 
				new FontUIResource("Consolas", 12, Font.PLAIN);
	
		Components.setUIFont(font);
	}
	
	private void addActions()
	{
		InputMap inputMap = 
				frame.getRootPane().getInputMap(
						JComponent.WHEN_IN_FOCUSED_WINDOW);
		
		inputMap.put(KeyStroke.getKeyStroke("D"),
					 "toggleVisibility");
		
		ActionMap actionMap = 
				frame.getRootPane().getActionMap();
		
		actionMap.put("toggleVisibility", 
					  new ToggleVisibility(debugPanel));
	
		inputMap.put(KeyStroke.getKeyStroke("Q"),
				 "test");
		actionMap.put("test", 
				  new AbilityAction(resources, 1));
	}
	
	public void repaintAll()
	{		
		layeredPane.repaint();
	}
	
	public void setVisible(boolean visible)
	{
		frame.setVisible(visible);
	}
	
	public JLayeredPane getLayeredPane()
	{
		return layeredPane;
	}
	
	
	public JFrame getFrame()
	{
		return frame;
	}
	
	/*public HUD getHUD()
	{
		return hud;
	}*/
	
	public DebugPanel getDebugPanel()
	{
		return debugPanel;
	}
}
