package Editor.comp;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import EntityComponent.RigidBody;
import EntityComponent.RigidBodyComponent;

public class RigidBodyComponentForm extends Form
	implements ValueListener
{
	private RigidBodyComponent comp;
	
	private RigidBodyForm bodyForm;
	
	public RigidBodyComponentForm()
	{
		this(new RigidBodyComponent());
	}
	
	public RigidBodyComponentForm(RigidBodyComponent comp)
	{
		Border bodyBorder = 
				BorderFactory.createTitledBorder("Rigid Body");
		
		bodyForm = new RigidBodyForm();
		
		bodyForm.setBorder(bodyBorder);
		
		addField(bodyForm, 0, 0, 1);
		
		bodyForm.addValueListener(this);
		
		setRigidBodyComponent(comp);
	}
	
	public void setRigidBodyComponent(RigidBodyComponent comp)
	{
		this.comp = comp;
		
		RigidBody body = comp.getRigidBody();
	
		bodyForm.setRigidBody(body);
	}
	
	public void updateFields() {
		bodyForm.updateFields();
	}
	
	public void valueChanged() {
		notifyListeners();
	}
}
