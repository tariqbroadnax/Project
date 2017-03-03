package TestEntity;

import Behavior.BehaviorComponent;
import Behavior.PetBehavior;
import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Graphic.ShapeGraphic;
import Maths.Circle2D;
import Movement.MovementComponent;

public class Pet extends Entity
{
	private Entity owner;
	
	public Pet()
	{
		GraphicsComponent gcomp = new GraphicsComponent();
		ShapeGraphic graph = new ShapeGraphic();
		Circle2D.Double circ = new Circle2D.Double(0, 0, 3);
	
		gcomp.setGraphic(graph);
		graph.setShape(circ);
		
		BehaviorComponent behavComp = new BehaviorComponent();
	
		behavComp.addBehavior(new PetBehavior());
	

		add(gcomp, behavComp);
		add(new MovementComponent());		
	}
	
	public void setOwner(Entity owner) {
		this.owner = owner;
	}
	
	public Entity getOwner() {
		return owner;
	}
}
