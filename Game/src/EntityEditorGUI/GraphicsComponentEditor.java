package EntityEditorGUI;

import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.LINE_END;
import static java.awt.GridBagConstraints.LINE_START;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import EntityComponent.GraphicsComponent;
import Graphic.AnimatedGraphic;
import Graphic.Graphic;
import Graphic.LayeredGraphic;
import Graphic.LineGraphic;
import Graphic.ShapeGraphic;
import Graphic.TextGraphic;

public class GraphicsComponentEditor extends JPanel
{
	private JLabel graphicLabel;
	
	private JComboBox<GraphicType> graphicBox;
	
	private GraphicEditor graphicEditor;
	
	private GraphicsComponent comp;
	
	private GraphicType currType;
	
	public GraphicsComponentEditor()
	{
		this(new GraphicsComponent());
	}
	
	public GraphicsComponentEditor(GraphicsComponent comp)
	{
		this.comp = comp;

		graphicLabel = new JLabel("Graphic: ");
 		
		GraphicType[] values = GraphicType.values();
		
		graphicBox = new JComboBox<GraphicType>(values);
		
		graphicEditor = new ShapeGraphicEditor();
		
		Border border = 
				BorderFactory.createLineBorder(
						Color.black);
		
		graphicEditor.setBorder(border);
		
		setLayout(new GridBagLayout());
	
		addComponents();				
		
		graphicBox.addActionListener(e -> selectGraphicType());
		
		setGraphicsComponent(comp);
	}
	
	public void setGraphicsComponent(GraphicsComponent comp)
	{
		this.comp = comp;
		
		Graphic graphic = comp.getGraphic();
				
		currType = GraphicType.typeOfGraphic(graphic);
		
		remove(graphicEditor);

		graphicEditor = currType.graphicEditor();

		graphicEditor.setGraphic(graphic);
	
		graphicBox.setSelectedItem(currType);
	
		addGraphicEditor();
		revalidate();
		repaint();
	}
	
	private void selectGraphicType()
	{
		GraphicType prevType = currType;
		
		currType = (GraphicType)
				graphicBox.getSelectedItem();
		
		if(prevType.equals(currType))
			return;
		
		graphicBox.setSelectedItem(currType);

		remove(graphicEditor);

		graphicEditor = currType.graphicEditor();

		Graphic newGraphic = graphicEditor.getGraphic();
		
		comp.setGraphic(newGraphic);
		
		addGraphicEditor();
		revalidate();
		repaint();
	}
	
	private void addComponents()
	{
		GridBagConstraints c = 
				new GridBagConstraints();
		
		c.fill = HORIZONTAL;
		
		c.insets.set(5, 5, 5, 5);
		
		c.weightx = 0;
		c.anchor = LINE_END;
		c.gridx = 0; c.gridy = 0;
		add(graphicLabel, c);
		
		c.weightx = 1;
		c.anchor = LINE_START;
		c.gridx = 1; c.gridy = 0;
		add(graphicBox, c);
		
		c.weightx = 1;
		c.gridwidth = 2;
		c.anchor = CENTER;
		c.gridx = 0; c.gridy = 1;
		add(graphicEditor, c);
	}
	
	private void addGraphicEditor()
	{
		GridBagConstraints c = 
				new GridBagConstraints();
		
		c.fill = HORIZONTAL;
		
		c.insets.set(5, 5, 5, 5);
		
		c.weightx = 1;
		c.gridwidth = 2;
		c.anchor = CENTER;
		c.gridx = 0; c.gridy = 1;
		add(graphicEditor, c);
	}
}
