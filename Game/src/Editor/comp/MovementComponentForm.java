package Editor.comp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.border.Border;

import Movement.Movement;
import Movement.MovementComponent;

public class MovementComponentForm extends Form
	implements ValueListener, ActionListener
{
	private MovementComponent comp;
	
	private MovementForm normMoveForm,
						 disMoveForm;
	
	private JCheckBox enabledBox;
		
	public MovementComponentForm()
	{
		this(new MovementComponent());
	}
	
	public MovementComponentForm(MovementComponent comp)
	{
		JLabel enabledLbl = new JLabel("Enabled");
		
		Border normMoveBorder = 
				BorderFactory.createTitledBorder("Normal Movement"),
			   disMoveBorder = 
			    BorderFactory.createTitledBorder("Disabling Movement");
		
		normMoveForm = new MovementForm();
		disMoveForm = new MovementForm();
		
		enabledBox = new JCheckBox();
		
		normMoveForm.setBorder(normMoveBorder);
		disMoveForm.setBorder(disMoveBorder);
		
		addField(normMoveForm, 0, 0, 2);
		addField(disMoveForm, 0, 1, 2);
		addComponent(enabledLbl, 0, 2, 1);
		addComponent(enabledBox, 1, 2, 1);
		
		normMoveForm.addValueListener(this);
		disMoveForm.addValueListener(this);
		enabledBox.addActionListener(this);
		
		setMovementComponent(comp);
	}
	
	public void setMovementComponent(MovementComponent comp)
	{
		this.comp = comp;
		
		Movement normMovement = comp.getNormalMovement(),
				 disMovement = comp.getDisablingMovement();
		
		boolean enabled = comp.isEnabled();
		
		normMoveForm.setMovement(normMovement);
		disMoveForm.setMovement(disMovement);
		enabledBox.setSelected(enabled);
	}
	
	public void updateFields()
	{
		boolean enabled = comp.isEnabled();
	
		enabledBox.setSelected(enabled);
		normMoveForm.updateFields();
		disMoveForm.updateFields();
	}

	@Override
	public void valueChanged() {
		notifyListeners();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		boolean enabled = enabledBox.isSelected();
	
		comp.setEnabled(enabled);
		
		notifyListeners();
	}

}
