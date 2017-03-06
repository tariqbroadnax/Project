package GUI.Equip;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import EditorGUI.MouseListener;
import Entity.Entity;
import EntityComponent.EquipmentComponent;
import Item.Equipment;

public class EquipPane extends JComponent
	implements MouseListener
{
	private Equipment equip;
	
	private EquipItemComponent hat, hatDecor,
							   sprint,
							   weapon, shield,
							   access1, access2,
							   card, drill,
							   pet,
							   belt, tail, cape,
							   hatAcc, mask, bag,
							   shirt, cloak, glove,
							   sock, shoe, pants, beltAcc;							   
							   
	public EquipPane(Entity player)
	{
		equip = player.get(EquipmentComponent.class)
					  .getEquipment();	
		
		hat = new EquipItemComponent();
		hatDecor = new EquipItemComponent();
		sprint = new EquipItemComponent();
		weapon = new EquipItemComponent();
		shield = new EquipItemComponent();
		access1 = new EquipItemComponent();
		access2 = new EquipItemComponent();
		card = new EquipItemComponent();
		drill = new EquipItemComponent();
		pet = new EquipItemComponent();
		belt = new EquipItemComponent();
		tail = new EquipItemComponent();
		cape = new EquipItemComponent();
		hatAcc = new EquipItemComponent();
		mask = new EquipItemComponent();
		bag = new EquipItemComponent();
		shirt = new EquipItemComponent();
		cloak = new EquipItemComponent();
		glove = new EquipItemComponent();
		sock = new EquipItemComponent();
		shoe = new EquipItemComponent();
		pants = new EquipItemComponent();
		beltAcc = new EquipItemComponent();
		
		EquipItemComponent[] comps = {hat, hatDecor,
				   sprint,
				   weapon, shield,
				   access1, access2,
				   card, drill,
				   pet,
				   belt, tail, cape,
				   hatAcc, mask, bag,
				   shirt, cloak, glove,
				   sock, shoe, pants, beltAcc};							   
 	
		int[] xs = {77, 132, 187, 22, 187, 22, 187, 22, 187, 22, 77, 132, 187,
				    5, 55, 105, 155, 205,
				    5, 55, 105, 155, 205};
		
		int[] ys = {5, 5, 5, 55, 55, 105, 105, 155, 155, 205, 205, 205, 205,
				    260, 260, 260, 260, 260,
				  	310, 310, 310, 310, 310};
		
		setLayout(null);
		
		for(int i = 0; i < comps.length; i++)
		{			
			EquipItemComponent comp = comps[i];
			
			add(comp);
			
			comp.setSize(comp.getPreferredSize());
			comp.setLocation(xs[i], ys[i]);
		}
		
		setPreferredSize(new Dimension(255, 360));
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		
	}
}
