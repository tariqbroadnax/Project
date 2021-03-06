package GUI;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Dialogue.Branch;
import Dialogue.Dialogue;

public class BranchList extends JList<Branch>
	implements ListSelectionListener
{
	private DialogueArea area;
	
	private List<Branch> branches;
	
	public BranchList(DialogueArea area)
	{
		this.area = area;
		branches = new ArrayList<Branch>();
		
		setModel(new MyModel());
		setCellRenderer(new MyRenderer());
		
		addListSelectionListener(this);
	}
	
	public void setBranches(List<Branch> branches)
	{
		this.branches = branches;
		
		repaint();
	}
	
	private class MyModel implements ListModel<Branch>
	{
		@Override
		public Branch getElementAt(int index) {
			return branches.get(index);
		}

		@Override
		public int getSize() {
			return branches.size();
		}

		public void addListDataListener(ListDataListener e) {}
		public void removeListDataListener(ListDataListener e) {}
	}
	
	private class MyRenderer implements ListCellRenderer<Branch>
	{
		private JButton btn;	
		
		MyRenderer()
		{
			btn = new JButton();
			
			btn.setBorderPainted(false);
			btn.setContentAreaFilled(false);
			btn.setHorizontalAlignment(SwingConstants.LEFT);
		}
		
		@Override
		public Component getListCellRendererComponent(
				JList<? extends Branch> list, Branch val,
				int index,
				boolean isSelected, boolean cellHasFocus) 
		{		
			btn.setText(val.getTransition());
			
			return btn;
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
		Dialogue dialogue = getSelectedValue()
							.getDialogue();
	
		area.setDialogue(dialogue);
	}
}
