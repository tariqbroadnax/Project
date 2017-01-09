package Editor.comp;

import java.awt.geom.Point2D;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.Border;

import Entity.Entity;
import EntityComponent.GraphicsComponent;
import EntityComponent.LifetimeComponent;
import Movement.MovementComponent;

public class EntityForm extends Form
{
	private Entity ent;
	
	private Point2DForm ptForm;
	
	private JComboBox<String> compBox;
	
	private JButton addBtn, removeBtn;
	
	private AddComponentDialog addDialog;
	
	private Form currCompForm;
	
	private MovementComponentForm moveCompForm;
	
	public EntityForm()
	{
		this(new Entity());		
	}
	
	public EntityForm(Entity ent)
	{
		ptForm = new Point2DForm();
		
		addBtn = new JButton("Add");
		removeBtn = new JButton("Remove");
				
		JLabel compLbl = new JLabel("Component");
		
		compBox = new JComboBox<String>();
		
		addDialog = new AddComponentDialog();
	
		moveCompForm = new MovementComponentForm();
		
		Border border = 
				BorderFactory.createTitledBorder("Location");
			
		ptForm.setBorder(border);
		ptForm.addValueListener(() -> updateLoc());
		
		addBtn.addActionListener(e -> addComponents());
		removeBtn.addActionListener(e -> removeComponent());
				
		compBox.addActionListener(e -> updateCompForm());
		
		addField(ptForm, 0, 0, 2);
		addComponent(addBtn, 0, 1, 1);
		addComponent(removeBtn, 1, 1, 1);
		addComponent(compLbl, 0, 2, 1);
		addComponent(compBox, 1, 2, 1);
	
		setEntity(ent);
	}
	
	public void setEntity(Entity ent) 
	{
		this.ent = ent;
		
		Point2D.Double loc = ent.getLoc();
		
		ptForm.setValue(loc);	
		
		updateCompArea();
	}
	
	private void addComponents()
	{
		addDialog.setEntity(ent);
		addDialog.setVisible(true);
	
		updateCompArea();		
	}
	
	private void removeComponent()
	{
		String comp = (String) compBox.getSelectedItem();
	
		if(comp.equals("Graphics"))
			ent.remove(GraphicsComponent.class);
		else if(comp.equals("Movement"))
			ent.remove(MovementComponent.class);
		else if(comp.equals("Lifetime"))
			ent.remove(LifetimeComponent.class);
		
		updateCompArea();
	}
	
	private void updateCompArea()
	{
		updateBox();
		
		if(compBox.getItemCount() == 3)
			addBtn.setEnabled(false);
		else
			addBtn.setEnabled(true);
		
		if(compBox.getItemCount() == 0)
			removeBtn.setEnabled(false);
		else
			removeBtn.setEnabled(true);
		
		updateCompForm();
	}
	
	private void updateCompForm()
	{
		if(currCompForm != null)
			remove(currCompForm);
		
		String comp = (String) compBox.getSelectedItem();
		
		if(comp == null)
			currCompForm = null;
		else if(comp.equals("Movement"))
		{
			MovementComponent mcomp = 
					ent.get(MovementComponent.class);
		
			currCompForm = moveCompForm;
			
			moveCompForm.setMovementComponent(mcomp);
		}
		
		if(currCompForm != null)
		{
			addField(currCompForm, 0, 3, 2);
			revalidate();
			repaint();
		}
	}
	
	private void updateBox()
	{
		Vector<String> vector = new Vector<String>();
		
		if(ent.contains(GraphicsComponent.class))
			vector.add("Graphics");
		if(ent.contains(MovementComponent.class))
			vector.add("Movement");
		if(ent.contains(LifetimeComponent.class))
			vector.add("Lifetime");
		
		compBox.removeAllItems();
		
		for(String str : vector)
			compBox.addItem(str);
	}
	
	private void updateLoc() 
	{
		Point2D.Double pt = ptForm.getPtValue();
		
		ent.setLoc(pt);
	}
}
