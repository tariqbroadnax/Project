package DebugGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTable;

public class Viewer extends JPanel
{
	private JTable table;
	
	protected ViewerModel model;

	private Collection<Viewer> subViewers;
	
	public Viewer()
	{
		table = new JTable();
		model = new ViewerModel();
		
		table.setModel(model);
		
		setBackground(Color.white);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(table);
		
		subViewers = new LinkedList<Viewer>();
	}
	
	protected void add(Viewer viewer)
	{
		subViewers.add(viewer);
		
		super.add(viewer);
				
		viewer.setPreferredSize(
				new Dimension(
						getPreferredSize().width - 10,
						viewer.getPreferredSize().height));
	}
	
	public void setPreferredSize(Dimension dim)
	{
		super.setPreferredSize(dim);
		
		for(Viewer viewer : subViewers)
		{	
			viewer.setPreferredSize(
					new Dimension(
							getPreferredSize().width - 10,
							viewer.getPreferredSize().height));
		}
	}
	
	
}
