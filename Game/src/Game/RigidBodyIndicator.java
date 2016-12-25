package Game;

import java.awt.Color;
import java.awt.geom.Rectangle2D.Double;
import java.awt.geom.RectangularShape;
import java.io.Serializable;

import EntityComponent.RigidBody;
import Graphic.Graphic;
import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;

public class RigidBodyIndicator extends Graphic
	implements Serializable
{
	private RigidBody body;
	private ShapeGraphic graph;
	
	public RigidBodyIndicator()
	{
		body = null;
		graph = new ShapeGraphic();
	
		graph.setPaint(Color.yellow);
	}

	@Override
	protected void _paint(GraphicsContext gc) 
	{
		if(body == null) return;
		
		for(RectangularShape shape : body.getComponents())
		{
			graph.setLoc(shape.getCenterX(),
						 shape.getCenterY());
			
			graph.setShape(shape);
			
			graph.paint(gc);
		}
	}
	
	public void setRigidBody(RigidBody body) {
		this.body = body;
	}
	
	// FIXME
	@Override
	public Double getBound() 
	{
		return null;
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return null;
	}
}
