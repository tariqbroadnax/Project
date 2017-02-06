package Editor.comp;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import Editor.ComponentListener;
import Graphic.Animation;
import Graphic.Graphic;
import Graphic.ShapeGraphic;

public class AnimationList extends JList<Graphic>
	implements ComponentListener
{
	private Animation ani;
	
	private AnimationModel model;
	
	public AnimationList()
	{
		this(new Animation());
	}
	
	public AnimationList(Animation ani)
	{		
		ani.addGraphic(new ShapeGraphic());
		
		AnimationRenderer renderer = new AnimationRenderer();
		
		setCellRenderer(renderer);
		
		model = new AnimationModel(ani);
		
		setModel(model);
		
		setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		setVisibleRowCount(-1);				
			
		this.ani = ani;
			
	//	addComponentListener(this);
	}
	
	public void setAnimation(Animation ani) 
	{
		this.ani = ani;
	
		model.setAnimation(ani);
	}
}

class AnimationRenderer implements ListCellRenderer<Graphic>
{
	private GraphicPreview preview = new GraphicPreview();
	
	@Override
	public Component getListCellRendererComponent(
			JList<? extends Graphic> list,
			Graphic val, int index, boolean isSelected,
			boolean cellHasFocus) 
	{
		if(isSelected)
			preview.setBackground(Color.blue);
		else
			preview.setBackground(Color.white);
		
		preview.setGraphic(val);
		
		return preview;
	}
}

class AnimationModel implements ListModel<Graphic>
{
	private Animation ani;
	
	public AnimationModel(Animation ani) {
		this.ani = ani;
	}
	
	public void setAnimation(Animation ani) {
		this.ani = ani;
	}

	@Override
	public Graphic getElementAt(int index) {
		return ani.getGraphic(index);
	}

	@Override
	public int getSize() {
		return ani.size();
	}

	public void addListDataListener(ListDataListener l) {}
	public void removeListDataListener(ListDataListener l) {}
}
