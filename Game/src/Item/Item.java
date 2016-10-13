package Item;

import java.awt.Color;

import EntityComponent.BodyType;
import EntityComponent.CollisionEvent;
import EntityComponent.CollisionResponse;
import EntityComponent.GraphicsComponent;
import EntityComponent.ModifierComponent;
import EntityComponent.RigidBodyComponent;
import EntityComponent.StatsComponent;
import Game.Entity;
import Graphic.LayeredGraphic;
import Graphic.ShapeGraphic;
import Graphic.Vector2D;
import Maths.Circle2D;

public abstract class Item extends Entity
	implements CollisionResponse
{	
	public Item()
	{
		super();
		
		add(new StatsComponent(),
			new ModifierComponent(),
			createRigidBodyComponent(),
			createGraphicsComponent());			
	}
	
	public Item(Item item)
	{
		super(item);		
	}
	
	private GraphicsComponent createGraphicsComponent()
	{
		GraphicsComponent comp = 
				new GraphicsComponent();
		
		LayeredGraphic layers =
				new LayeredGraphic();
		
		ShapeGraphic graphic =
				new ShapeGraphic();
		
		graphic.setShape(new Circle2D.Double(0, 0, 5));
		graphic.setPaint(Color.BLUE);
		
		layers.addGraphic(graphic, new Vector2D.Double(-2.5, -2.5));
		
		comp.setGraphic(layers);
		
		return comp;
	}
	
	private RigidBodyComponent createRigidBodyComponent()
	{
		RigidBodyComponent comp =
				new RigidBodyComponent();
		
		comp.getRigidBody()
			.addComponent(
					new Vector2D.Double(-2.5, -2.5),
					new Circle2D.Double(0, 0, 5));
		
		comp.addCollisionResponses(
				BodyType.BEING, this);
		
		return comp;
	}
	
	/* Requirements
	 * - should be viewable on ground
	 * -> should have a corresponding entity
	 * 
	 */
	
	private void getPickedUp(Entity e)
	{	
		leaveSpace();
		_getPickedUp(e);
	}
	
	protected abstract void _getPickedUp(Entity e);

	public void enterSpace()
	{
		get(RigidBodyComponent.class)
			.setEnabled(true);
	
		get(GraphicsComponent.class)
			.setEnabled(true);
	}
	
	public void leaveSpace()
	{
		get(RigidBodyComponent.class)
			.setEnabled(false);
	
		get(GraphicsComponent.class)
			.setEnabled(false);
	}
	
	@Override
	public Object clone()
	{
		return _clone();
	}
	
	public abstract Object _clone();
	
	public void collisionOccurred(CollisionEvent e)
	{
		((Item)e.collider).getPickedUp(e.collided);
	}
}
