package Editor.comp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.Border;

import Graphic.Animation;
import Graphic.Graphic;
import Graphic.LayeredGraphic;
import Graphic.LineGraphic;
import Graphic.ShapeGraphic;
import Graphic.Sprite;
import Graphic.TextGraphic;

public class GraphicForm extends Form
	implements ValueListener, ActionListener
{
	private Graphic graph;
	
	private Form currForm;
	
	private Point2DForm ptForm;
	
	private ShapeGraphicForm shapeGraphForm;
	private TextGraphicForm textGraphForm;
	private SpriteForm spriteForm;
	private LineGraphicForm lineGraphForm;
	
	private JComboBox<String> graphBox;
	private JComboBox<Integer> zBox;
	
	private JCheckBox visBox;

	private GraphicPreview preview;
	
	public GraphicForm()
	{
		this(new ShapeGraphic());
	}
	
	public GraphicForm(Graphic graph)
	{		
		JLabel visLbl = new JLabel("Visible"),
			   zLbl = new JLabel("Z"),
			   graphLbl = new JLabel("Graphic");
		
		ptForm = new Point2DForm();
		
		visBox = new JCheckBox();
		
		shapeGraphForm = new ShapeGraphicForm();
		textGraphForm = new TextGraphicForm();
		spriteForm = new SpriteForm();
		lineGraphForm = new LineGraphicForm();
		
		Integer[] zs = {0, 1, 2, 3, 4, 5};
		
		zBox = new JComboBox<Integer>(zs);
		
		String[] graphStrs = 
			{"Sprite", "Shape", "Text", "Line",
			 "Animation", "Layered"};
		
		graphBox = new JComboBox<String>(graphStrs);
		
		ShapeGraphicForm form = new ShapeGraphicForm();
		
		preview = new GraphicPreview();

		Border border = BorderFactory.createLineBorder(Color.black);
				
		form.setBorder(border);
		
		addField(ptForm, 0, 0, 2);
		addComponent(visLbl, 0, 1, 1);
		addComponent(visBox, 1, 1, 1);
		addComponent(zLbl, 0, 2, 1);
		addComponent(zBox, 1, 2, 1);
		addComponent(graphLbl, 0, 3, 1);
		addComponent(graphBox, 1, 3, 1);
		addField(preview, 0, 5, 2);
		
		ptForm.addValueListener(this);
		shapeGraphForm.addValueListener(this);
		textGraphForm.addValueListener(this);
		spriteForm.addValueListener(this);
		lineGraphForm.addValueListener(this);
		graphBox.addActionListener(this);
		zBox.addActionListener(this);
		visBox.addActionListener(this);		
		
		setGraphic(graph);
	}
	
	public void setGraphic(Graphic graph)
	{
		this.graph = graph;
		
		updateFields();
		
		preview.setGraphic(graph);
	}
	
	public Graphic getGraphic()
	{
		return graph;
	}
	
	public void updateFields()
	{
		graphBox.removeActionListener(this);
		zBox.removeActionListener(this);
		
		if(currForm != null)
			remove(currForm);
		
		currForm = null;
		String item = null;
		
		if(graph instanceof TextGraphic)
		{
			currForm = textGraphForm;
			textGraphForm.setTextGraphic((TextGraphic)graph);
			item = "Text";
		}
		else if(graph instanceof Sprite)
		{
			item = "Sprite";
			spriteForm.setSprite((Sprite) graph);
			currForm = spriteForm;
		}
		else if(graph instanceof ShapeGraphic)
		{
			currForm = shapeGraphForm;
			shapeGraphForm.setShapeGraphic((ShapeGraphic)graph);
			item = "Shape";
		}
		else if(graph instanceof LineGraphic)
		{
			currForm = lineGraphForm;
			lineGraphForm.setValue((LineGraphic) graph);
			item = "Line";
		}
		else if(graph instanceof Animation)
			item = "Animation";
		else if(graph instanceof LayeredGraphic)
			item = "Layered";
		
		graphBox.setSelectedItem(item);
		
		if(currForm != null)
			addField(currForm, 0, 4, 2);			
		
		int z = graph.getLayer();
	
		boolean visible = graph.isVisible();
	
		Point2D.Double loc = graph.getLoc();
				
		zBox.setSelectedItem(z);
		visBox.setSelected(visible);
		ptForm.setValue(loc);
		
		graphBox.addActionListener(this);
		zBox.addActionListener(this);
		
		revalidate();
		repaint();
	}
	
	private void updateValues()
	{
		Form lastForm = currForm;
		
		String graphType = (String) graphBox.getSelectedItem();
		
		switch(graphType)
		{
		case "Shape":
			currForm = shapeGraphForm;
			graph = shapeGraphForm.getShapeGraphic();
			break;
		case "Text":
			currForm = textGraphForm;
			graph = textGraphForm.getTextGraphic();
			break;
		case "Sprite":
			currForm = spriteForm;
			graph = spriteForm.getSprite();
			break;
		case "Line":
			currForm = lineGraphForm;
			graph = lineGraphForm.getValue();
			break;
		default:
			currForm = null;
		}
		
		if(lastForm != currForm)
		{
			if(lastForm != null)
				remove(lastForm);
		
			if(currForm != null)
				addField(currForm, 0, 4, 2);			
			
			revalidate();
			repaint();
		}
				
		int z = (Integer) zBox.getSelectedItem();
		
		boolean visible = visBox.isSelected();
		
		double x = ptForm.getXValue(),
			   y = ptForm.getYValue();
		
		graph.setLayer(z);
		graph.setVisible(visible);
		graph.setLoc(x, y);
		
		preview.setGraphic(graph);
	}
	
	public Point2DForm getPtForm() {
		return ptForm;
	}

	@Override
	public void valueChanged() {
		updateValues();
		notifyListeners();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		updateValues();
		notifyListeners();
	}
}
