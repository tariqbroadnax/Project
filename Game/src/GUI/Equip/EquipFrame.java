package GUI.Equip;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import Entity.Entity;

public class EquipFrame extends JInternalFrame
{
	public EquipFrame(Entity player)
	{
		super("Equip", false, true, false, false);
		
		EquipPane pane = new EquipPane(player);
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		
		panel.add(pane, BorderLayout.CENTER);
		
		add(panel);
		
		pack();
		setBorder(null);
	}
}
