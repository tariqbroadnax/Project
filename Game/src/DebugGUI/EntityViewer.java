package DebugGUI;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.Timer;

import EntityComponent.GraphicsComponent;
import Game.Entity;
import Movement.MovementComponent;

public class EntityViewer extends Viewer
{	
	private Timer timer;
			
	public EntityViewer(Entity entity)
	{
		super();
		
		timer = new Timer(250, e -> repaint());
		
		model.addFieldRecord(
				"Entity ID", () -> "" + entity.getEntityID());
		
		model.addFieldRecord(
				"x", () -> "" + entity.getLoc().x);
		
		model.addFieldRecord(
				"y", () -> "" + entity.getLoc().y);
		
		model.addFieldRecord(
				"Location Scene ID", () -> "" + entity.getLocationSceneID());
		
		if(entity.contains(MovementComponent.class))
		{	
			add(new JLabel("MovementComponent"));
			add(new MovementCompViewer(entity));
		
		}
		if(entity.contains(GraphicsComponent.class))
		{
			add(new JLabel("GraphicsComponent"));
			add(new GraphicsCompViewer(entity));


		}
		
		setPreferredSize(new Dimension(400, 400));
		timer.start();
	}
}

