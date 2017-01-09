package Editor.comp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import Editor.DoubleField;
import Movement.Movement;

public class MovementForm extends Form
	implements ValueListener, ActionListener
{
	private Movement movement;
	
	private DoubleField speedFld, dirFld;
	
	private JCheckBox enabledBox;
	
	private List<ValueListener> listeners;
	
	public MovementForm()
	{
		this(new Movement());
	}
	
	public MovementForm(Movement movement)
	{
		JLabel speedLbl = new JLabel("Speed "),
			   dirLbl = new JLabel("Direction "),
			   enabledLbl = new JLabel("Enabled");
		
		speedFld = new DoubleField();
		dirFld = new DoubleField();
		
		enabledBox = new JCheckBox();
	
		listeners = new ArrayList<ValueListener>();

		addComponent(speedLbl, 0, 0, 1);
		addField(speedFld, 1, 0, 1);
		addComponent(dirLbl, 0, 1, 1);
		addField(dirFld, 1, 1, 1);
		addComponent(enabledLbl, 0, 2, 1);
		addComponent(enabledBox, 1, 2, 1);	
		
		speedFld.addValueListener(this);
		dirFld.addValueListener(this);
		enabledBox.addActionListener(this);
		
		setMovement(movement);
	}
	
	public void setMovement(Movement movement)
	{
		this.movement = movement;
	
		updateFields();
	}
	
	public void updateFields()
	{
		double speed = movement.getSpeed(),
				   dir = movement.getDirection();
			
		boolean enabled = movement.isEnabled();
		
		speedFld.setValue(speed);
		dirFld.setValue(dir);
		enabledBox.setSelected(enabled);
	}
	
	private void updateAndNotify()
	{
		updateValues();
		notifyListeners();
	}
	
	private void updateValues()
	{
		double speed = speedFld.getValue(),
			   dir = dirFld.getValue();
		
		boolean enabled = enabledBox.isSelected();
		
		movement.setSpeed(speed);
		movement.setDirection(dir);
		movement.setEnabled(enabled);
	}
	
	private void notifyListeners()
	{
		for(ValueListener listener : listeners)
			listener.valueChanged();
	}
	
	public void addValueListener(ValueListener listener) {
		listeners.add(listener);
	}
	
	public void removeValueListener(ValueListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void valueChanged() {
		updateAndNotify();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updateAndNotify();
	}
}