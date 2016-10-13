package GUI;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import Game.Resources;

public class HUD extends JPanel
	implements ComponentListener
{	
	private final static double STATUS_BAR_WEIGHT_X = 0.25,
						 		STATUS_BAR_WEIGHT_Y = 0.05;
			
	//private Stats stats;
	
	//private GMainMenu mainMenu;
	
	//private JInternalFrame statFrame;
	
	//private InventoryFrame inventoryFrame;
	
	//private AbilityFrame abilityFrame;
	
	//private DialogueFrame dialogueFrame;
	
	public HUD(Resources resources)
	{
		/*inventoryFrame = new InventoryFrame(resources);
	
		abilityFrame = new AbilityFrame(
				resources.getPlayer()
						 .get(AbilityComponent.class),
				resources.getImagePool());
		
		dialogueFrame = new DialogueFrame(resources);
		
		mainMenu = new GMainMenu(resources);

		//dialogueFrame.setVisible(true);
				
		mainMenu.add(new JButton());
		*/
		setOpaque(false);
		setLayout(null);
		/*
		insets = new Insets(5, 5, 5, 5);
		
		statFrame = new JInternalFrame();
		
		statFrame.setSize(200, 200);
		statFrame.setVisible(true);
		
		for(JInternalFrame frame : 
			Arrays.asList(statFrame, dialogueFrame, inventoryFrame))
			frame.setLocation(200, 200);
		
		statFrame.setResizable(true);
		
		setStats(
				resources.getPlayer()
						 .get(StatsComponent.class)
						 .getStats());
		
		addComponents();
		
		addComponentListener(this);
		*/
	
	}
	
	/*private void addComponents()
	{
		int x = 0, y = 0;
		
		int width = (int)(getWidth() * STATUS_BAR_WEIGHT_X),
		    height = (int)(getHeight() * STATUS_BAR_WEIGHT_Y);
		
		x += insets.left;
		
		// FIXME test location
		addMainMenu();
		//add(statFrame);
		
		dialogueFrame.setSize(400, 200);
		inventoryFrame.setSize(250, 350);
		abilityFrame.setSize(200, 200);
		
		add(dialogueFrame);
		add(inventoryFrame);
		add(abilityFrame);
	}
	
	private void addMainMenu()
	{
		Dimension dim = getSize(),
				  menuDim = new Dimension(
						  dim.width / 2,
						  dim.height / 20);
	
		mainMenu.setSize(menuDim);
		mainMenu.setLocation(dim.width - menuDim.width,
							 dim.height - menuDim.height);
		add(mainMenu);
	}
	
	public void setStats(Stats stats)
	{		
		this.stats = stats;	
	}
	
	public JInternalFrame getInventoryFrame()
	{
		return inventoryFrame;
	}
	
	public JInternalFrame getAbilityFrame()
	{
		return abilityFrame;
	} */
	
	/*public DialogueFrame getDialogueFrame()
	{
		return dialogueFrame;
	}*/

	public void componentHidden(ComponentEvent e){}

	public void componentMoved(ComponentEvent e){}

	public void componentShown(ComponentEvent e){}

	public void componentResized(ComponentEvent e)
	{
		//removeAll();
		//addComponents();
	}
	
	/*
	 * level
	 * health bar
	 * exp bar
	 */
}
