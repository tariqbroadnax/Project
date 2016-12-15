package Editor.layer_selector;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.ListModel;

import EditorGUI.MouseListener;
import Graphic.Graphic;

public class ToggleLayerVisibility 
	implements MouseListener
{
	private JList<Graphic> parent;
	
	public ToggleLayerVisibility(
			JList<Graphic> parent)
	{
		this.parent = parent;
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		ListModel<Graphic> model = parent.getModel();
		
		Point p = e.getPoint();
		int index = parent.locationToIndex(p);
		int size = model.getSize();
		
		
		if(index < size)
		{
			Graphic graph = model.getElementAt(index);
			graph.setVisible(!graph.isVisible());
			parent.repaint();
		}
	}

}
