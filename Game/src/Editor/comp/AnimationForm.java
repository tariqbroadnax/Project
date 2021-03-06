package Editor.comp;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Editor.ComponentListener;
import Editor.IntField;
import Graphic.Animation;
import Graphic.Graphic;
import Graphic.ShapeGraphic;

public class AnimationForm extends Form
	implements ValueListener, ComponentListener,
			   ListSelectionListener
{
	private Animation ani;
	
	private JButton addBtn, removeBtn;
	
	private AnimationList aniList;

	private GraphicFormer former;
	
	private IntField delayFld;
	
	public AnimationForm()
	{
		this(new Animation());
	}
	
	public AnimationForm(Animation ani)
	{
		JLabel delayLbl = new JLabel("Delay");
				
		delayFld = new IntField();
		
		JToolBar toolbar = new JToolBar();
		
		addBtn = new JButton("Add");
		removeBtn = new JButton("Remove");
				
		aniList = new AnimationList();

		former = new GraphicFormer();
		
		toolbar.add(addBtn);
		toolbar.add(removeBtn);
		
		addComponent(delayLbl, 0, 0, 1);
		addField(delayFld, 1, 0, 1);
		addField(toolbar, 0, 1, 2);
		addField(aniList, 0, 2, 2);
		addField(former, 0, 3, 2);
		
		aniList.addListSelectionListener(this);
		addBtn.addActionListener(e -> addGraphic());
		removeBtn.addActionListener(e -> removeGraphic());
	
		former.addValueListener(this);
		delayFld.addValueListener(this);

		setValue(ani);		
	}
	
	public void updateFields()
	{
		aniList.setAnimation(ani);

		if(ani.size() > 0)
		{
			int index = aniList.getSelectedIndex();
			
			if(index == -1)
			{
				aniList.setSelectedIndex(0);
				index = 0;
			}
			
			Graphic graph = ani.getGraphic(index);
			
			former.setValue(graph);
		}
		
		int delay = (int) ani.getDelay();
				
		delayFld.setValue(delay);
	}
	
	public void setValue(Animation ani) 
	{
		this.ani = ani;

		updateFields();
		
		updateComponents();
	}
	
	public Animation getValue()
	{
		return (Animation) ani.clone();
	}
	
	private void addGraphic()
	{
		int size = ani.size();
		
		Graphic graph;
		if(size == 0)
		{
			graph = new ShapeGraphic();
		}
		else
		{
			graph = (Graphic) ani.getGraphic(size - 1)
								 .clone();
		}
		
		ani.addGraphic(graph);
		
		aniList.setSelectedIndex(size);
		
		updateComponents();
		
		notifyListeners();
		
		repaint();		
	}
	
	private void removeGraphic()
	{
		Graphic graph = aniList.getSelectedValue();

		int index = aniList.getSelectedIndex();
		
		ani.removeGraphic(graph);

		if(ani.size() > index - 1)
			aniList.setSelectedIndex(index - 1);
		
		updateComponents();

		notifyListeners();
		
		repaint();
	}
	
	private void updateComponents()
	{		
		aniList.setVisibleRowCount(-1);	
		
		if(ani.size() == 0)
			former.setVisible(false);
		else
			former.setVisible(true);
		
		Graphic graph;
		if(!(ani.size() == 0 || 
			 aniList.isSelectionEmpty()))
		{
			graph = aniList.getSelectedValue();
		}
		else
			graph = null;
		
		if(graph == null)
			removeBtn.setEnabled(false);
		else
			removeBtn.setEnabled(true);
	
		former.setValue(graph);
		
		repaint();
	}
	
	private void updateValues()
	{
		Graphic graph = former.getValue();
		
		int index = aniList.getSelectedIndex();
		
		int delay = delayFld.getValue();
		
		ani.setGraphic(index, graph);
		ani.setDelay(delay);
	}
	
	@Override
	public void valueChanged() 
	{
		updateValues();
		notifyListeners();
		repaint();
	}

	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
		updateComponents();
	}
}
