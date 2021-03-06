package Editor.comp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.Border;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Movement.Force;
import Movement.Movement;

public class MovementForm extends Form
	implements ValueListener, ActionListener,
			   ListSelectionListener
{
	private Movement movement;
		
	private JCheckBox enabledBox;
	
	private JButton addBtn, removeBtn;
	
	private JList<Force> list;
	
	private ForceForm form;
	
	public MovementForm()
	{
		this(new Movement());
	}
	
	public MovementForm(Movement movement)
	{
		Border forceBorder = BorderFactory.createTitledBorder("Forces");
		
		JLabel enabledLbl = new JLabel("Enabled");

		enabledBox = new JCheckBox();

		addBtn = new JButton("Add");
		removeBtn = new JButton("Remove");

		list = new JList<Force>();
		
		form = new ForceForm();
		
		list.setBorder(forceBorder);
			
		addComponent(enabledLbl, 0, 0, 1);
		addField(enabledBox, 1, 0, 1);
		addComponent(addBtn, 0, 1, 1);
		addComponent(removeBtn, 1, 1, 1);
		addComponent(new JScrollPane(list), 0, 2, 2);
		addField(form, 0, 3, 2);
		
		addBtn.addActionListener(e -> addForce());
		removeBtn.addActionListener(e -> removeForce());
		
		list.addListSelectionListener(this);
		
		enabledBox.addActionListener(this);	
		
		form.addValueListener(this);
		
		setMovement(movement);
		
		removeBtn.setEnabled(false);
		form.setVisible(false);
	}
	
	public void setMovement(Movement movement)
	{
		this.movement = movement;
	
		if(movement.getForces().size() == 0)
			form.setVisible(false);
		else
		{
			Force force = list.getSelectedValue();
			
			form.setForce(force);
			form.setVisible(true);
		}
		updateFields();
	}
	
	private void addForce()
	{
		Force force = new Force();
		
		movement.addForce(force);
		
		list.setModel(new MyModel());
		
		int size = list.getModel().getSize();

		list.setSelectedIndex(size - 1);

		removeBtn.setEnabled(true);
		form.setVisible(true);
	}
	
	private void removeForce()
	{
		int index = list.getSelectedIndex();
		
		Force force = list.getSelectedValue();
		
		movement.removeForce(force);
		
		int size = list.getModel().getSize();
		
		if(index == size)
			list.setSelectedIndex(index - 1);
		else
			list.setSelectedIndex(index);
		
		if(size == 0)
		{
			removeBtn.setEnabled(false);
			form.setVisible(false);
		}
		
		list.repaint();
	}
	
	public void updateFields()
	{
		boolean enabled = movement.isEnabled();
		
		enabledBox.setSelected(enabled);
		
		repaint();
	}
	
	private void updateAndNotify()
	{
		updateValues();
		notifyListeners();
	}
	
	private void updateValues()
	{
		boolean enabled = enabledBox.isSelected();
		
		movement.setEnabled(enabled);
		
		List<Force> forces = movement.getForces();
		
		if(!forces.isEmpty())
		{
			int index = list.getSelectedIndex();
			
			Force force = list.getSelectedValue();
			
			movement.getForces()
					.set(index, force);
		}
		
		repaint();
	}
	
	@Override
	public void valueChanged() {
		updateAndNotify();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updateAndNotify();
	}
	
	private class MyModel implements ListModel<Force>
	{
		@Override
		public Force getElementAt(int index) {
			return movement.getForces()
						   .get(index);
		}

		@Override
		public int getSize() 
		{
			return movement.getForces()
						   .size();
		}

		public void addListDataListener(ListDataListener l) {}
		public void removeListDataListener(ListDataListener l) {}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) 
	{			
		Force force = list.getSelectedValue();
		
		if(force != null)
			form.setForce(force);
	}
}
