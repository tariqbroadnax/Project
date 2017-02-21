package Editor.comp;

import java.awt.Dialog;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;

import Actions.ActionComponent;
import Entity.Entity;
import EntityComponent.GraphicsComponent;
import EntityComponent.LifetimeComponent;
import EntityComponent.RigidBodyComponent;
import Movement.MovementComponent;

public class AddComponentDialog extends JDialog
{
	private Entity ent;
	
	private JList<String> compList;
	
	private List<String> comps;
	
	private JButton addBtn, closeBtn;
	
	public AddComponentDialog()
	{
		super(null, "Add Component", Dialog.ModalityType.APPLICATION_MODAL);
		
		JLabel instructLbl = new JLabel("Select Components ");
		
		compList = new JList<String>();
		
		addBtn = new JButton("Add");
		closeBtn = new JButton("Close");
		
		comps = new ArrayList<String>();
	
		addBtn.addActionListener(e -> addComponents());
		closeBtn.addActionListener(e -> setVisible(false));
		
		Form form = new Form();
		
		form.addComponent(instructLbl, 0, 0, 1);
		form.addField(compList, 0, 1, 3);
		form.addComponent(addBtn, 1, 2, 1);
		form.addComponent(closeBtn, 2, 2, 1);
		
		add(form);
		
		compList.setModel(new MyListModel());

		setSize(300, 200);
	}
	
	public void setEntity(Entity ent)
	{
		this.ent = ent;
		
		comps.clear();
		
		if(!ent.contains(GraphicsComponent.class))
			comps.add("Graphics");
		
		if(!ent.contains(MovementComponent.class))
			comps.add("Movement");
		
		if(!ent.contains(LifetimeComponent.class))
			comps.add("Lifetime");
		
		if(!ent.contains(RigidBodyComponent.class))
			comps.add("Rigid Body");
		
		if(!ent.contains(ActionComponent.class))
			comps.add("Action");
	}
	
	private void addComponents()
	{
		int[] indices = compList.getSelectedIndices();
		
		for(int i = 0; i < indices.length; i++)
		{
			String str = comps.get(indices[i]);
			
			switch(str)
			{
			case "Graphics":
				ent.add(new GraphicsComponent());
				break;
			case "Movement":
				ent.add(new MovementComponent());
				break;
			case "Lifetime":
				ent.add(new LifetimeComponent());
				break;
			case "Rigid Body":
				ent.add(new RigidBodyComponent());
				break;
			case "Action":
				ent.add(new ActionComponent());
				break;
			}
		}
		
		setVisible(false);
	}

	private class MyListModel extends AbstractListModel<String> 
	{
		@Override
		public String getElementAt(int index) {
			return comps.get(index);
		}

		@Override
		public int getSize() {
			return comps.size();
		}
	}
}
