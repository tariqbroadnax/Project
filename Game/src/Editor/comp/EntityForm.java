package Editor.comp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Point2D;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Entity.Entity;
import EntityComponent.GraphicsComponent;
import EntityComponent.LifetimeComponent;
import Movement.MovementComponent;

public class EntityForm extends Form
	implements ValueListener, ActionListener,
			   FocusListener
{
	private Entity ent;
	
	private JTextField nameFld;
	
	private Point2DForm ptForm;
	
	private JComboBox<String> compBox;
	
	private JButton addBtn, removeBtn;
	
	private AddComponentDialog addDialog;
	
	private Form currCompForm;
	
	private MovementComponentForm moveCompForm;
	private GraphicsComponentForm graphCompForm;
	
	public EntityForm()
	{
		this(new Entity());		
	}
	
	public EntityForm(Entity ent)
	{
		nameFld = new JTextField();
		
		ptForm = new Point2DForm();
		
		addBtn = new JButton("Add");
		removeBtn = new JButton("Remove");
				
		JLabel nameLbl = new JLabel("Name"),
			   compLbl = new JLabel("Component");
		
		compBox = new JComboBox<String>();
		
		addDialog = new AddComponentDialog();
	
		moveCompForm = new MovementComponentForm();
		graphCompForm = new GraphicsComponentForm();
		
		Border border = 
				BorderFactory.createTitledBorder("Location");
			
		ptForm.setBorder(border);
		
		ptForm.addValueListener(this);
		graphCompForm.addValueListener(this);
		moveCompForm.addValueListener(this);
		
		nameFld.addFocusListener(this);
		nameFld.addActionListener(e -> valueChanged());
		
		addBtn.addActionListener(e -> addComponents());
		removeBtn.addActionListener(e -> removeComponent());
				
		compBox.addActionListener(this);
		
		addComponent(nameLbl, 0, 0, 1);
		addField(nameFld, 1, 0, 1);
		addField(ptForm, 0, 1, 2);
		addComponent(addBtn, 0, 2, 1);
		addComponent(removeBtn, 1, 2, 1);
		addComponent(compLbl, 0, 3, 1);
		addComponent(compBox, 1, 3, 1);
	
		setEntity(ent);
	}
	
	public void setEntity(Entity ent) 
	{
		this.ent = ent;
		
		updateFields();
	}
	
	public void updateFields()
	{
		String name = ent.getName();
		
		Point2D.Double loc = ent.getLoc();
		
		nameFld.setText(name);
		ptForm.setValue(loc);	
		
		compBox.removeActionListener(this);
		
		if(ent.contains(GraphicsComponent.class))
		{
			GraphicsComponent gcomp = ent.get(GraphicsComponent.class);
			
			graphCompForm.setGraphicsComponent(gcomp);
		}
		
		if(ent.contains(MovementComponent.class))
		{
			MovementComponent mcomp = ent.get(MovementComponent.class);
			
			moveCompForm.setMovementComponent(mcomp);
		}
		
		updateCompArea();
		
		compBox.addActionListener(this);
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
		else if(comp.equals("Graphics"))
		{
			GraphicsComponent gcomp =
					ent.get(GraphicsComponent.class);
			
			currCompForm = graphCompForm;
			
			graphCompForm.setGraphicsComponent(gcomp);
		}
		else if(comp.equals("Movement"))
		{
			MovementComponent mcomp = 
					ent.get(MovementComponent.class);
		
			currCompForm = moveCompForm;
			
			moveCompForm.setMovementComponent(mcomp);
		}
		
		if(currCompForm != null)
		{
			addField(currCompForm, 0, 4, 2);
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
	
	private void updateValues() 
	{
		String name = nameFld.getText();
		
		Point2D.Double pt = ptForm.getPtValue();
		
		ent.setName(name);
		ent.setLoc(pt);
	}

	@Override
	public void valueChanged() {
		updateValues();
		notifyListeners();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		updateCompForm();
		notifyListeners();
	}

	@Override
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent arg0) {
		valueChanged();
	}
}
