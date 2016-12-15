package EditorGUI;

import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;

import Entity.Entity;
import EntityComponent.EntityComponent;
import EntityComponent.GraphicsComponent;
import Game.EntityListener;

public class EntitySelectionButton extends SelectionButton
	implements EntityListener, Observer
{	
	private Entity entity;
		
	public EntitySelectionButton(
			GUIResources resources, Entity entity) 
	{
		super(resources);
		
		this.entity = entity;
		
		entity.addEntityListener(this);
		entity.get(GraphicsComponent.class)
		  	  .addObserver(this);
		
		setOpaque(false);
		setContentAreaFilled(false);
		
		updateIcon();
	}
	
	public void removeNotify()
	{
		super.removeNotify();
		
		entity.removeEntityListener(this);
		
		entity.get(GraphicsComponent.class)
			  .deleteObserver(this);
	}
	
	
	private BufferedImage graphicImage()
	{
		BufferedImage graphicImg = 
				entity.get(GraphicsComponent.class)
					  .getGraphic()
					  .projectedImage(600, 600);
		
		return graphicImg;
	}
	
	@Override
	protected Object getContent() 
	{
		return entity;
	}
	
	private void updateIcon()
	{
		BufferedImage graphicImg = graphicImage();
		ImageIcon icon = new ImageIcon(graphicImg);
		
		setIcon(icon);
	}

	private void updateObserver(
			GraphicsComponent oldComp,
			GraphicsComponent newComp) 
	{
		oldComp.deleteObserver(this);
		newComp.addObserver(this);
	}
	
	public Entity getEntity()
	{
		return entity;
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		updateIcon();
	}
	
	@Override
	public void componentAdded(
			Entity src, EntityComponent comp) {}

	@Override
	public void componentRemoved(
			Entity src, EntityComponent comp) {}

	@Override
	public void componentReplaced(
			Entity src, EntityComponent oldComp,
						EntityComponent newComp) 
	{
		if(newComp instanceof GraphicsComponent)
		{
			updateObserver((GraphicsComponent)oldComp,
						   (GraphicsComponent)newComp);	
			updateIcon();
		}
	}
}
