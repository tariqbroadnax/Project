package GUI;

import java.awt.event.ComponentEvent;

import javax.swing.JDesktopPane;
import javax.swing.JPanel;

import Editor.ComponentListener;
import Entity.Entity;
import GUI.Equip.EquipFrame;
import GUI.Item.ItemFrame;

public class HUD extends JPanel
	implements ComponentListener
{		
	private DialogueArea area;
	
	private ExpBar expBar;
	private HealthBar healthBar;
	private ManaBar manaBar;
	
	private GMenu menu;
	
	private ItemFrame itemFrame;
	private EquipFrame equipFrame;
	
	private JDesktopPane pane;
	
	public HUD(Entity player)
	{	
		area = new DialogueArea();
		
		expBar = new ExpBar(player);
		healthBar = new HealthBar(player);
		manaBar = new ManaBar(player);
		
		itemFrame = new ItemFrame(player);
		equipFrame = new EquipFrame(player);
		
		menu = new GMenu(this);
		
		area.setVisible(false);
		
		setOpaque(false);
		setLayout(null);
		
		add(area);
		add(expBar);
		add(healthBar);
		add(manaBar);
		add(menu);
		
		pane = new JDesktopPane();
		
		pane.setOpaque(false);
		
		pane.add(itemFrame);
		pane.add(equipFrame);
		
		add(pane);
		
//		EventQueue.invokeLater(()->itemFrame.setVisible(true));
//		
		expBar.setLocation(5, 5);
		expBar.setSize(expBar.getPreferredSize());
		healthBar.setLocation(5, 20);
		healthBar.setSize(healthBar.getPreferredSize());
		manaBar.setLocation(5, 35);
		manaBar.setSize(manaBar.getPreferredSize());
		menu.setSize(menu.getPreferredSize());
		itemFrame.setLocation(300, 150);
		equipFrame.setLocation(200, 150);

		addComponentListener(this);
	}
	
	@Override
	public void componentResized(ComponentEvent e)
	{
		int width = getWidth(),
			height = getHeight();
		
		int menuWidth = menu.getWidth(),
			menuHeight = menu.getHeight();
		
		menu.setLocation(width - menuWidth, height - menuHeight);	
	
		pane.setLocation(0, 0);
		pane.setSize(width, height);
	}
	
	public DialogueArea getDialogueArea() {
		return area;
	}
	
	public ItemFrame getItemFrame() {
		return itemFrame;
	}
	
	public EquipFrame getEquipFrame() {
		return equipFrame;
	}
}
