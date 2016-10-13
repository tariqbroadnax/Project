package Graphic;

import java.time.Duration;
import java.util.HashMap;

import EntityComponent.EntityComponent;
import EntityComponent.GraphicsComponent;
import Game.Entity;
import Maths.Direction;
import Movement.MovementComponent;
import Movement.MovementListener;

public class BeingGraphicsComponent extends GraphicsComponent
	implements MovementListener
{
	private HashMap<Direction, AnimatedGraphic> movingGraphics,
												nonMovingGraphics;
	
	
	public BeingGraphicsComponent()
	{	
		super();
		
		movingGraphics = new HashMap<Direction, AnimatedGraphic>();
		nonMovingGraphics = new HashMap<Direction, AnimatedGraphic>();
	
		movingGraphics.put(Direction.E, ImageGraphic.animation(
				Duration.ofMillis(125), 0, 7));
		
		nonMovingGraphics.put(Direction.E, ImageGraphic.animation(
				Duration.ofMillis(250), 0));
		
		movingGraphics.put(Direction.N, ImageGraphic.animation(
				Duration.ofMillis(125), 8, 15));
		
		nonMovingGraphics.put(Direction.N, ImageGraphic.animation(
				Duration.ofMillis(250), 8));
	
		movingGraphics.put(Direction.NE, ImageGraphic.animation(
				Duration.ofMillis(125), 16, 23));
		
		nonMovingGraphics.put(Direction.NE, ImageGraphic.animation(
				Duration.ofMillis(250), 16));
		
		movingGraphics.put(Direction.NW, ImageGraphic.animation(
				Duration.ofMillis(125), 24, 31));
		
		nonMovingGraphics.put(Direction.NW, ImageGraphic.animation(
				Duration.ofMillis(125), 24));
		
		movingGraphics.put(Direction.S, ImageGraphic.animation(
				Duration.ofMillis(125), 32, 39));
		
		nonMovingGraphics.put(Direction.S, ImageGraphic.animation(
				Duration.ofMillis(125), 32));
		
		movingGraphics.put(Direction.SE, ImageGraphic.animation(
				Duration.ofMillis(125), 40, 47));
		
		nonMovingGraphics.put(Direction.SE, ImageGraphic.animation(
				Duration.ofMillis(250), 40));
		
		movingGraphics.put(Direction.SW, ImageGraphic.animation(
				Duration.ofMillis(125), 48, 55));
		
		nonMovingGraphics.put(Direction.SW, ImageGraphic.animation(
				Duration.ofMillis(250), 48));
		
		movingGraphics.put(Direction.W, ImageGraphic.animation(
				Duration.ofMillis(125), 56, 63));
		
		nonMovingGraphics.put(Direction.W, ImageGraphic.animation(
				Duration.ofMillis(250), 56));
		
		LayeredGraphic graph = new LayeredGraphic();
		
		graph.addGraphic(
				nonMovingGraphics.get(Direction.N),
				new Vector2D.Double(-5, -5));
		
		graphic = graph;
	}
	
	public BeingGraphicsComponent(BeingGraphicsComponent comp)
	{
		super(comp);
		
		movingGraphics = new HashMap<Direction, AnimatedGraphic>();
		nonMovingGraphics = new HashMap<Direction, AnimatedGraphic>();
		
		for(Direction dir : Direction.values())
		{			
			movingGraphics.put(
					dir,
					(AnimatedGraphic)
					comp.movingGraphics.get(dir).clone());
			
			nonMovingGraphics.put(
					dir, 
					(AnimatedGraphic)
					comp.nonMovingGraphics.get(dir).clone());
		}
	}
	
	@Override
	public void movementStarted(MovementComponent src)
	{
		updateGraphic(src, movingGraphics);
	}
	
	@Override
	public void movementStopped(MovementComponent src)
	{
		updateGraphic(src, nonMovingGraphics);
	}
	
	@Override
	public void directionChanged(MovementComponent src)
	{
		updateGraphic(src, movingGraphics);
	}
	
	private void updateGraphic(
			MovementComponent src,
			HashMap<Direction, AnimatedGraphic> graphs)
	{
		Direction dir = src.getDirection();
		
		AnimatedGraphic graph = graphs.get(dir);
		
		graph.reset();
		
		((LayeredGraphic)graphic).setGraphic(graph, 0);
	}
		
	public void setParent(Entity parent)
	{
		super.setParent(parent);
		
		parent.get(MovementComponent.class)
			  .addMovementListener(this);		
	}
	
	@Override
	protected EntityComponent _clone()
	{
		return new BeingGraphicsComponent(this);
	}
	
}
