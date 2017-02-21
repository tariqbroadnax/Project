package Editor.comp;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import Movement.Movement;
import Movement.MovementComponent;

public class MovementComponentForm extends Form
	implements ValueListener
{
	private MovementComponent comp;
	
	private MovementForm moveForm;
			
	public MovementComponentForm()
	{
		this(new MovementComponent());
	}
	
	public MovementComponentForm(MovementComponent comp)
	{
		Border moveBorder = 
				BorderFactory.createTitledBorder("Movement");
		
		moveForm = new MovementForm();
		
		moveForm.setBorder(moveBorder);
		
		addField(moveForm, 0, 0, 1);
		
		moveForm.addValueListener(this);
		
		setMovementComponent(comp);
	}
	
	public void setMovementComponent(MovementComponent comp)
	{
		this.comp = comp;
		
		Movement movement = comp.getMovement();
		
		moveForm.setMovement(movement);
	}
	
	public void updateFields()
	{
		moveForm.updateFields();
	}

	@Override
	public void valueChanged() 
	{
		notifyListeners();
	}
}
