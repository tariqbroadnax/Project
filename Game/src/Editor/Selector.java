package Editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Editor.tile.OpenTileset;
import Editor.tile.TSModel;
import Editor.tile.TileSelector;
import Tileset.Tileset;

public class Selector extends JPanel
	implements ListSelectionListener
{	
	private JList<Tileset> list;
	
	private TileSelector selector;

	public Selector(EditorResources resources)
	{
		JToolBar toolbar = new JToolBar();
		
		selector = new TileSelector();
		
		list = new JList<Tileset>();

		toolbar.add(new OpenTileset(list));
		
		toolbar.setFloatable(false);
		
		list.setModel(new TSModel());
		list.setCellRenderer(new MyRenderer());
		list.addListSelectionListener(this);
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0; gbc.gridy = 0;
		gbc.weightx = 1; gbc.weighty = 0;
		gbc.fill = gbc.NONE;
		gbc.anchor = gbc.LINE_START;
		
		add(toolbar, gbc);
	
		gbc.gridx = 0; gbc.gridy = 1;
		gbc.weightx = 1; gbc.weighty = 1;
		gbc.fill = gbc.BOTH;
		gbc.anchor = gbc.LINE_START;
		
		add(new JScrollPane(list), gbc);
		
		gbc.gridx = 0; gbc.gridy = 2;
		gbc.weightx = 1; gbc.weighty = 1;
		gbc.fill = gbc.BOTH;
		gbc.anchor = gbc.LINE_START;
		
		add(new JScrollPane(selector), gbc);	
		
		List<Tileset> tss = Editor.RESOURCES.getEditorAssets()
								            .getTilesets();
		
		if(tss.size() > 0)
		{
			Tileset ts = tss.get(0);
			
			list.setSelectedValue(ts, true);
		}
	}
	
	private class MyRenderer implements ListCellRenderer<Tileset>
	{
		private JLabel label = new JLabel();
		
		@Override
		public Component getListCellRendererComponent(
				JList<? extends Tileset> list,
				Tileset val, int index,
				boolean isSelected, boolean focused) 
		{
			String fileName = val.getFile()
								 .getName();
			
			label.setOpaque(true);
			label.setText(fileName);
			
			if(isSelected)
				label.setBackground(Color.pink);
			else
				label.setBackground(Color.white);
			
			return label;
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
		Tileset ts = list.getSelectedValue();
		
		selector.setTileset(ts);
	}
}
