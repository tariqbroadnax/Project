package Editor.comp;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

import EntityComponent.BodyType;
import EntityComponent.RigidBody;
import EntityComponent.RigidBodyComponent;

public class RigidBodyComponentForm extends Form
	implements ValueListener
{
	private RigidBodyComponent comp;
	
	private RigidBodyForm bodyForm;
	
	private BodyTypeBox typeBox;
	
	public RigidBodyComponentForm()
	{
		this(new RigidBodyComponent());
	}
	
	public RigidBodyComponentForm(RigidBodyComponent comp)
	{
		Border bodyBorder = 
				BorderFactory.createTitledBorder("Rigid Body");
		
		JLabel typeLbl = new JLabel("Body Type");
		
		bodyForm = new RigidBodyForm();
		
		typeBox = new BodyTypeBox();
		
		bodyForm.setBorder(bodyBorder);
		
		addComponent(typeLbl, 0, 0, 1);
		addComponent(typeBox, 1, 0, 1);
		addField(bodyForm, 0, 1, 3);
		
		bodyForm.addValueListener(this);
		typeBox.addValueListener(this);
		
		setRigidBodyComponent(comp);
	}
	
	public void setRigidBodyComponent(RigidBodyComponent comp)
	{
		this.comp = comp;
		
		updateFields();
	}
	
	public void updateFields() 
	{
		RigidBody body = comp.getRigidBody();
		
		BodyType type = comp.getBodyType();
		
		bodyForm.setRigidBody(body);
		
		typeBox.setBodyType(type);
	}
	
	private void updateValues()
	{
		BodyType type = typeBox.getBodyType();

		comp.setBodyType(type);
	}
	
	public void valueChanged() 
	{
		updateValues();
		notifyListeners();
	}
}
