package Entity;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import Behaviour.PathingBehaviour;
import EntityComponent.BehaviourComponent;
import EntityComponent.GraphicsComponent;
import EntityComponent.RigidBody;
import EntityComponent.RigidBodyComponent;
import Game.Scene;
import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;
import Maths.Circle2D;
import Maths.Dimension2D;

public class Hole extends Entity
{
	private Point2D.Double transportLoc;
	
	private Ellipse2D.Double ellipse;
		
	private List<Entity> fallingEntities;
	
	private double fallRate;
	
	public Hole()
	{	
		transportLoc = new Point2D.Double(50, 50);
		ellipse = new Ellipse2D.Double(0, 0, 30, 10);
	
		fallingEntities = new LinkedList<Entity>();
		
		fallRate = 10;
		
		add(createGraphicsComponent(),
			createRigidBodyComponent());
	}
	
	@Override
	public void update(Duration delta)
	{
		double fallAmount = fallRate * delta.toMillis() / 1000.0;
		
		fallingEntities.forEach(e -> 
		{ 
			e.move(0, fallAmount);

			if(completelyInsideHole(e))
				moveEntity(e);
		});

		fallingEntities.removeIf(e -> completelyInsideHole(e));
		
		super.update(delta);
	}
	
	private RigidBodyComponent createRigidBodyComponent()
	{
		RigidBodyComponent comp = 
				new RigidBodyComponent();
		
		RigidBody body = new RigidBody();
		
		Circle2D.Double circ = new Circle2D.Double(0, 0, 
				Math.max(ellipse.width/2, ellipse.height/2));
		
		body.addComponent(circ);
		
		comp.setRigidBody(body);
		
		comp.add(e -> checkAndStartFalling(e.collided),
				 ent -> ent instanceof Player);
		
		return comp;
	}

	private GraphicsComponent createGraphicsComponent()
	{
		GraphicsComponent comp =
				new GraphicsComponent();
		
		ShapeGraphic graphic = 
				new HoleGraphic();
		
		comp.setGraphic(graphic);
		
		return comp;
	}

	private void checkAndStartFalling(Entity entity)
	{
		RigidBody body = entity.get(RigidBodyComponent.class)
						 	   .getRigidBody();
		
		if(contains(body))
			startFalling(entity);
	}

	private void startFalling(Entity entity)
	{
		Scene scene = getSceneLoc();
		
		scene.removeEntity(entity);
		
		Point2D.Double loc = getLoc();
		
		entity.setLoc(loc);
		
		checkAndStopPathing(entity);		
		fallingEntities.add(entity);	
	}

	private void checkAndStopPathing(Entity entity)
	{
		BehaviourComponent comp =
					entity.get(BehaviourComponent.class);
			
		if(comp != null)
		{
			PathingBehaviour beh = 
					comp.getBehaviour(PathingBehaviour.class);
		
			if(beh != null)
				beh.clearTargets();
		}
	}

	private boolean completelyInsideHole(Entity entity)
	{
		double minGraphicBoundY = 
				entity.get(GraphicsComponent.class)
					  .getGraphic()
					  .getBound()
					  .getMinY();
		
		return minGraphicBoundY > ellipse.y + ellipse.height;		
	}
	
	private void moveEntity(Entity entity)
	{
		entity.setLoc(transportLoc);
		checkAndStopPathing(entity);
		
		Scene scene = getSceneLoc();
	
		scene.addEntity(entity);
	}

	private boolean contains(RigidBody body)
	{
		Rectangle2D.Double union = body.union();
		
		double maxUnionY = union.getMaxY(),
			   minUnionX = union.getMinX(),
			   maxUnionX = union.getMaxX();
		
		double minHoleX = ellipse.getMinX(),
			   minHoleY = ellipse.getMinY(),
			   maxHoleX = ellipse.getMaxX(),
			   maxHoleY = ellipse.getMaxY();
		
		return minHoleY < maxUnionY && maxUnionY < maxHoleY &&
			   minHoleX < minUnionX && minUnionX < maxHoleX &&
			   minHoleX < maxUnionX && maxUnionX < maxHoleX;
	}
	
	public void setLoc(double x, double y)
	{
		super.setLoc(x, y);
		
		double w = ellipse.getWidth(),
			   h = ellipse.getHeight();
		
		x -= w/2; y -= h/2;
		
		ellipse.setFrame(x, y, w, h);
	}

	public void setTransportLoc(double x, double y) {
		transportLoc.x = x; transportLoc.y = y;
	}
	
	public void setFallRate(double fallRate) {
		this.fallRate = fallRate;
	}
	
	private class HoleGraphic extends ShapeGraphic
	{
		public HoleGraphic()
		{
			super();
			
			setShape(ellipse);
			setPaint(Color.blue);
		}
		
		@Override
		protected void _paint(GraphicsContext gc) 
		{
			super._paint(gc);
			
			Point2D.Double loc = 
					gc.camera.screenLocation(
							ellipse.x, ellipse.y);
			
			Dimension2D.Double size =
					gc.camera.sizeOnScreen(
							ellipse.width, ellipse.height);
			
			Ellipse2D.Double ellipse =
					new Ellipse2D.Double(
							loc.x, loc.y,
							size.width, size.height);
			
			gc.g2d.setClip(ellipse);
			
			for(Entity entity : fallingEntities)
			{
				GraphicsComponent comp =
						entity.get(GraphicsComponent.class);
				
				comp.paint(gc);
			}
			
			gc.g2d.setClip(null);
		}
	}
}
