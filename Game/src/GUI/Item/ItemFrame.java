package GUI.Item;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import Entity.Entity;
import Inventory.Inventory;
import Inventory.InventoryComponent;
import Inventory.InventoryListener;

public class ItemFrame extends JInternalFrame
	implements InventoryListener
{
	private Inventory inventory;
	
	public ItemFrame(Entity player)
	{
		super("", false, true, false, false);
		
		inventory = player.get(InventoryComponent.class)
						  .getInventory();
		
		inventory.addInventoryListener(this);
		
		JPanel panel = new JPanel();
		
		JTabbedPane pane = new JTabbedPane();
		
		JList list = new ItemList(player, ItemList.USE);
		
		JScrollPane scroll = new JScrollPane(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scroll.setViewportView(list);
		
		pane.addTab("Use", 
				new JScrollPane(new ItemList(player, ItemList.USE)));
		
		pane.addTab("Equip",
				new JScrollPane(new ItemList(player, ItemList.EQUIP)));
		pane.addTab("Pet",
				new JScrollPane(new ItemList(player, ItemList.PET)));
		pane.addTab("Etc", 
				new JScrollPane(new ItemList(player, ItemList.ETC)));
		

		panel.setLayout(new BorderLayout());
		panel.add(pane, BorderLayout.CENTER);
		
		add(panel);
		
		updateTitle();
		
		setSize(300, 200);
		setBorder(null);
	}

	private void updateTitle()
	{
		int size = inventory.size(),
			cap = inventory.capacity();
			
		setTitle("Item(" + size + "/" + cap + ")");	
	}
	
	@Override
	public void itemAdded() {
		updateTitle();
	}
	
	@Override
	public void itemRemoved() {
		updateTitle();
	}
	
	@Override
	public void itemUsed() {
		updateTitle();
	}
}
