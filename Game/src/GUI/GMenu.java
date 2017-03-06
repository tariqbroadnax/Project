package GUI;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JToolBar;

import GUI.Item.ItemFrame;

public class GMenu extends JToolBar
{
	private HUD hud;
	
	public GMenu(HUD hud)
	{
		this.hud = hud;
		
		JButton itemBtn = new JButton("Item"),
				equipBtn = new JButton("Equip");
		
		itemBtn.setFocusable(false);
		itemBtn.addActionListener(e -> toggleItemFrame());
		
		equipBtn.setFocusable(false);
		equipBtn.addActionListener(e -> toggleEquipFrame());
		
		add(itemBtn);
		add(equipBtn);
		
		setFloatable(false);
		
		setPreferredSize(new Dimension(300, 30));
	}
	
	private void toggleItemFrame() {
		toggleFrame(hud.getItemFrame());
	}
	
	private void toggleEquipFrame() {
		toggleFrame(hud.getEquipFrame());
	}
	
	private void toggleFrame(JInternalFrame frame)
	{
		boolean visible = frame.isVisible();
		
		frame.setVisible(!visible);
	}
}
