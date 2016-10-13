package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import EntityComponent.Inventory;
import EntityComponent.InventoryComponent;
import GameClient.GameClientResources;
import Item.EquipItem;
import Item.UseItem;

public class InventoryFrame extends JInternalFrame
{	
	private JTabbedPane tabbedPane;
	
	private JScrollPane equipScrollPane,
						useScrollPane,
						etcScrollPane;
	
	private InventoryTable equipTable,
						   useTable,
						   etcTable;
	
	private GameClientResources resources;
	
	public InventoryFrame(GameClientResources resources)
	{
		super("Item Inventory", false, true);
		
		tabbedPane = new JTabbedPane();
		
		equipTable = new InventoryTable(EquipItem.class);
		
		useTable = new InventoryTable(UseItem.class);
		
		etcTable = new InventoryTable(UseItem.class);
		
		equipScrollPane = new JScrollPane(
				equipTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		useScrollPane = new JScrollPane(
				useTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		etcScrollPane = new JScrollPane(
				etcTable,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		tabbedPane.addTab("EQUIP", equipScrollPane);
		tabbedPane.addTab("USE", useScrollPane);
		tabbedPane.addTab("ETC", etcScrollPane);
			
		add(tabbedPane);		
	}
	
	public void paint(Graphics g)
	{
		Inventory inventory = 
				resources.getPlayerRecord()
						 .getPlayer(resources.getScene())
						 .get(InventoryComponent.class)
						 .getInventory();
		
		super.paint(g);
	}
	
	public void setSize(int width, int height)
	{
		super.setSize(width, height);		
		
		Dimension dim = useScrollPane.getViewport()
								  .getSize();
			
		for(JTable table : Arrays.asList(
				equipTable, useTable, etcTable))
			table.setPreferredSize(new Dimension(dim.width, dim.height));
		
	}
}
