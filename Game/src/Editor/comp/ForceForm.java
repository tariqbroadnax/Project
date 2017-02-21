package Editor.comp;

import javax.swing.JLabel;

import Editor.DoubleField;
import Movement.Force;

public class ForceForm extends Form
	implements ValueListener
{
	private Force force;
	
	private DoubleField speedFld, dirFld;
	
	public ForceForm()
	{
		this(new Force());
	}
	
	public ForceForm(Force force)
	{
		JLabel speedLbl = new JLabel("Speed"), 
			   dirLbl = new JLabel("Direction");
		
		speedFld = new DoubleField();
		dirFld = new DoubleField();
		
		addComponent(speedLbl, 0, 0, 1);
		addField(speedFld, 1, 0, 1);
		addComponent(dirLbl, 0, 1, 1);
		addField(dirFld, 1, 1, 1);
		
		speedFld.addValueListener(this);
		dirFld.addValueListener(this);
		
		setForce(force);
	}
	
	public void setForce(Force force)
	{
		this.force = force;
		
		updateFields();
	}
	
	public void updateFields()
	{
		double speed = force.getSpeed(),
			   dir = force.getDirection();
		
		speedFld.setValue(speed);
		dirFld.setValue(dir);
	}
	
	private void updateValues()
	{		
		double speed = speedFld.getValue(),
			   dir = dirFld.getValue();
		
		force.setSpeed(speed);
		force.setDirection(dir);
	}
	
	public Force getForce() {
		return (Force) force.clone();
	}

	@Override
	public void valueChanged() {
		updateValues();
		notifyListeners();
	}
}
