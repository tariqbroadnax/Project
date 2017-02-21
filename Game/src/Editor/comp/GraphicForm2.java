package Editor.comp;

import Graphic.Animation;
import Graphic.Graphic;
import Graphic.LineGraphic;
import Graphic.ShapeGraphic;
import Graphic.Sprite;
import Graphic.TextGraphic;

public class GraphicForm2 extends Form
	implements ValueListener
{	
	private Form form;
	
	public GraphicForm2()
	{
		this(new ShapeGraphic());
	}
	
	public GraphicForm2(Graphic graph)
	{	
		setValue(graph);
	}
	
	public void updateFields()
	{
		form.updateFields();
	}
	
	public void setValue(Graphic graph)
	{
		removeAll();
		
		if(graph == null)
			return;
		
		if(graph instanceof ShapeGraphic)
		{
			form = new ShapeGraphicForm(
					(ShapeGraphic) graph);
		}
		else if(graph instanceof TextGraphic)
		{
			form = new TextGraphicForm(
					(TextGraphic) graph);
		}
		else if(graph instanceof Sprite)
		{
			form = new SpriteForm(
					(Sprite) graph);
		}
		else if(graph instanceof LineGraphic)
		{
			form = new LineGraphicForm(
					(LineGraphic) graph);
		}
		else if(graph instanceof Animation)
		{
			form = new AnimationForm(
					(Animation) graph);
		}
		else
		{
			form = null;
		}
		
		addField(form, 0, 0, 1);
		
		form.addValueListener(this);
		
		revalidate();
		repaint();
	}

	public Graphic getValue()
	{
		if(form instanceof ShapeGraphicForm)
		{
			return ((ShapeGraphicForm)form).getShapeGraphic();
		}
		else if(form instanceof TextGraphicForm)
		{
			return ((TextGraphicForm)form).getTextGraphic();
		}
		else if(form instanceof SpriteForm)
		{
			return ((SpriteForm)form).getSprite();
		}
		else if(form instanceof LineGraphicForm)
		{
			return ((LineGraphicForm)form).getValue();
		}
		else if(form instanceof AnimationForm)
		{
			return ((AnimationForm)form).getValue();
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public void valueChanged() {
		notifyListeners();
	}
}
