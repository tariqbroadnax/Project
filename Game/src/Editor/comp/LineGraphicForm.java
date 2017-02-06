package Editor.comp;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import Editor.comp.ColorField;
import Editor.comp.Form;
import Editor.comp.Point2DForm;
import Editor.comp.ValueListener;
import Graphic.LineGraphic;

public class LineGraphicForm extends Form
	implements ValueListener
{
	private LineGraphic graph;
	
	private Point2DForm pt1Form,
						pt2Form;
	
	private ColorField colorFld;
	
	public LineGraphicForm()
	{
		this(new LineGraphic());
	}
	
	public LineGraphicForm(LineGraphic graph)
	{
		JLabel colorLbl = new JLabel("Color");
		
		pt1Form = new Point2DForm();
		pt2Form = new Point2DForm();
		
		colorFld = new ColorField();

		addField(pt1Form, 0, 0, 2);
		addField(pt2Form, 0, 1, 2);
		addComponent(colorLbl, 0, 2, 1);
		addField(colorFld, 1, 2, 1);
		
		pt1Form.addValueListener(this);
		pt2Form.addValueListener(this);
		colorFld.addValueListener(this);
		
		setValue(graph);
	}
	
	public void setValue(LineGraphic graph)
	{
		this.graph = graph;
		
		updateFields();
	}
	
	public LineGraphic getValue()
	{
		LineGraphic graph = new LineGraphic();
		
		Point2D.Double pt1 = pt1Form.getPtValue(),
					   pt2 = pt2Form.getPtValue();
	
		Color color = colorFld.getValue();
		
		graph.setPt1(pt1);
		graph.setPt2(pt2);
		graph.setPaint(color);

		return graph;
	}
	
	public void updateFields()
	{
		Point2D.Double pt1 = graph.getPt1(),
					   pt2 = graph.getPt2();
	
		Color color = (Color) graph.getPaint();
		
		pt1Form.setValue(pt1);
		pt2Form.setValue(pt2);
		colorFld.setValue(color);
	}
	
	private void updateValues()
	{
		Point2D.Double pt1 = pt1Form.getPtValue(),
					   pt2 = pt2Form.getPtValue();
		
		Color color = colorFld.getValue();
		
		graph.setPt1(pt1);
		graph.setPt2(pt2);
		graph.setPaint(color);
	}
	
	@Override
	public void valueChanged() 
	{
		updateValues();
		notifyListeners();
	}
}
