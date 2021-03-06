package Editor.comp;

import java.awt.geom.Rectangle2D;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import EntityComponent.Limb;
import EntityComponent.RigidBody;
import Maths.Circle2D;

public class RigidBodyForm extends Form
	implements ValueListener, ListSelectionListener
{
	private RigidBody body;
	
	private JButton addRectBtn, addCircBtn,
					removeBtn;
	
	private JList<Limb> list;
	
	private LimbForm limbForm;
	
	public RigidBodyForm()
	{
		this(new RigidBody());
	}
	
	public RigidBodyForm(RigidBody body)
	{	
		addRectBtn = new JButton("Add Rectangle");
		addCircBtn = new JButton("Add Circle");
		removeBtn = new JButton("Remove");
		
		list = new JList<Limb>();
	
		limbForm = new LimbForm();
		
		list.setModel(new MyListModel());
		
		addComponent(addRectBtn, 0, 0, 1);
		addComponent(addCircBtn, 0, 1, 1);
		addComponent(removeBtn, 0, 2, 1);
		addComponent(new JScrollPane(list), 0, 3, 1);
		addField(limbForm, 0, 4, 1);
	
		addRectBtn.addActionListener(e -> addRectangleLimb());
		addCircBtn.addActionListener(e -> addCircleLimb());
		removeBtn.addActionListener(e -> removeSelectedLimb());
		list.addListSelectionListener(this);
		limbForm.addValueListener(this);
		
		setRigidBody(body);
	}
	
	public void setRigidBody(RigidBody body)
	{
		this.body = body;
		
		updateFields();
	}
	
	public void updateFields()
	{
		int index = list.getSelectedIndex();
		
		if(-1 < index && index < body.getLimbs().size())
		{
			Limb limb = list.getSelectedValue();
			
			limbForm.setLimb(limb);
			limbForm.setVisible(true);
			
			removeBtn.setEnabled(true);
		}
		else
		{
			limbForm.setVisible(false);
			
			removeBtn.setEnabled(false);
		}
		
		revalidate();
		repaint();
	}
	
	private void addRectangleLimb()
	{
		Rectangle2D.Double rect = 
				new Rectangle2D.Double(0, 0, 10, 10);
	
		Limb limb = new Limb(rect);
		
		addLimb(limb);
	}
	
	private void addCircleLimb()
	{
		Circle2D.Double circ = new Circle2D.Double(0, 0, 5);
		
		Limb limb = new Limb(circ);
		
		addLimb(limb);
	}
	
	private void addLimb(Limb limb)
	{
		body.addLimb(limb);
		
		list.setSelectedValue(limb, true);
		
		updateFields();
	}
	
	private void removeSelectedLimb()
	{
		Limb limb = list.getSelectedValue();
		
		body.removeLimb(limb);
		
		updateFields();
	}
	
	@Override
	public void valueChanged() {
		notifyListeners();
	}
	

	@Override
	public void valueChanged(ListSelectionEvent e) {
		updateFields();
	}
	
	private class MyListModel extends AbstractListModel<Limb>
	{
		@Override
		public Limb getElementAt(int index) {
			return body.getLimbs()
					   .get(index);
		}

		@Override
		public int getSize() {
			return body.getLimbs()
					   .size();
		}	
	}
}
