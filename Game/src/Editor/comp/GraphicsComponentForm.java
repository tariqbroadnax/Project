package Editor.comp;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import EntityComponent.GraphicsComponent;
import Graphic.Graphic;

public class GraphicsComponentForm extends Form
	implements ValueListener
{
	private GraphicsComponent comp;
	
	private GraphicFormer former;
	
	public GraphicsComponentForm()
	{
		this(new GraphicsComponent());
	}
	
	public GraphicsComponentForm(GraphicsComponent comp)
	{
		Border border = BorderFactory.createTitledBorder("Graphic");
		
		former = new GraphicFormer();
		
		former.setBorder(border);
		former.addValueListener(this);
		
		addField(former, 0, 0, 1);		
	
		setGraphicsComponent(comp);
	}
	
	public void setGraphicsComponent(GraphicsComponent comp)
	{
		this.comp = comp;
		
		updateFields();
	}
	
	public void updateFields()
	{
		Graphic graph = comp.getGraphic();
		
		former.setValue(graph);
	}
	
	private void updateValues()
	{
		Graphic graph = former.getValue();
		
		comp.setGraphic(graph); 
	}

	@Override
	public void valueChanged() {
		updateValues();
		notifyListeners();
	}
}
