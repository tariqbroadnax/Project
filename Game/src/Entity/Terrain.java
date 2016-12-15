package Entity;

import CollisionResponses.Repel;
import EntityComponent.GraphicsComponent;
import EntityComponent.RigidBodyComponent;
import Graphic.Sprite;

public class Terrain extends Entity
{
	public Terrain()
	{
		super();
		
		add(new GraphicsComponent());
		
		RigidBodyComponent comp =
				new RigidBodyComponent();
		
		comp.add(new Repel(),
				 target -> !(target instanceof Terrain ||
						 	 target instanceof Fire));
		
		add(comp);
	}
	
	private GraphicsComponent createGraphicsComponent()
	{
		GraphicsComponent comp =
				new GraphicsComponent();
		
		Sprite graphic = new Sprite(
				"electr_mach.png", 10.0, 20.0);
		
		comp.setGraphic(graphic);
		
		return comp;
	}
}
