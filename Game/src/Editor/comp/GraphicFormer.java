package Editor.comp;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import Graphic.Animation;
import Graphic.Graphic;
import Graphic.LayeredGraphic;
import Graphic.LineGraphic;
import Graphic.ShapeGraphic;
import Graphic.Sprite;
import Graphic.TextGraphic;

public class GraphicFormer extends Form
	implements ItemListener, ValueListener
{
	private GraphicChooser chooser;
	
	private GraphicForm2 form;
	
	public GraphicFormer()
	{
		this(new ShapeGraphic());
	}
	
	public GraphicFormer(Graphic graph)
	{
		Border border = BorderFactory.createLineBorder(Color.black);
		
		chooser = new GraphicChooser();
		
		form = new GraphicForm2();
		
		form.setBorder(border);
		
		addComponent(chooser, 0, 0, 1);
		addField(form, 0, 1, 1);
		
		chooser.addItemListener(this);
		form.addValueListener(this);
		
		setValue(graph);
	}
	
	public void setValue(Graphic graph)
	{
		chooser.removeItemListener(this);
		
		if(graph != null)
			chooser.setSelectedItem(graph.getClass());
		
		form.setValue(graph);
		
		chooser.addItemListener(this);
	}
	
	public Graphic getValue() {
		return form.getValue();
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		Object o = chooser.getSelectedItem();
		
		Graphic graph;
		if(o.equals(Sprite.class))
			graph = new Sprite();
		else if(o.equals(ShapeGraphic.class))
			graph = new ShapeGraphic();
		else if(o.equals(TextGraphic.class))
			graph = new TextGraphic();
		else if(o.equals(LineGraphic.class))
			graph = new LineGraphic();
		else if(o.equals(Animation.class))
			graph = new Animation();
		else if(o.equals(LayeredGraphic.class))
			graph = new LayeredGraphic();
		else
			graph = null;
		
		form.setValue(graph);
		
		notifyListeners();
	}

	@Override
	public void valueChanged() {
		notifyListeners();
	}
}
