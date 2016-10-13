package EditorGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import Commands.RemoveResourceEntity;
import EntityComponent.EntityComponent;
import EntityComponent.GraphicsComponent;
import Game.Entity;
import Game.EntityListener;
import Graphic.Graphic;
import Graphic.GraphicListener;
import Graphic.GraphicsComponentListener;

public class EntitySelectionButton extends SelectionButton
	implements EntityListener, GraphicsComponentListener,
			   GraphicListener
{
	private static BufferedImage NO_GRAPHIC_IMG = noGraphicImage();
	
	private EntityWrapper wrapper;
	
	private GraphicsComponent prevComp;
	
	public EntitySelectionButton(
			GUIResources resources, Entity entity) 
	{
		super(resources, graphicImg(entity));
		
		wrapper = new EntityWrapper(
				entity, EntityResourceType.TEMPLATE);
	
		InputMap inputMap = getInputMap();
		ActionMap actionMap = getActionMap();
		
		RemoveResourceEntity RREcommand = 
				new RemoveResourceEntity(
						resources, entity);
	
		inputMap.put(KeyStroke.getKeyStroke("DELETE"),
					 RREcommand);
		
		actionMap.put(RREcommand, RREcommand);
		
		entity.addEntityListener(this);
		
		if(entity.contains(GraphicsComponent.class))
			listenToCurrCompAndGraphic();
	}
	
	private void listenToCurrCompAndGraphic()
	{
		Entity entity = wrapper.entity;
		
		GraphicsComponent comp = 
				entity.get(GraphicsComponent.class);
		
		comp.addGraphicsComponentListener(this);
		
		comp.getGraphic()
			.addGraphicListener(this);
		
		prevComp = comp;
	}
	
	private static BufferedImage graphicImg(Entity entity)
	{
		BufferedImage graphicImg;
		
		if(entity.contains(GraphicsComponent.class))
		{
			graphicImg = entity.get(GraphicsComponent.class)
							   .getGraphic()
							   .projectedImage(800, 600);
		}
		else
			graphicImg = NO_GRAPHIC_IMG;
		
		return graphicImg;
	}
	
	private static BufferedImage noGraphicImage()
	{
		BufferedImage img = new BufferedImage(
				50, 50, 
				BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = img.getGraphics();
		
		Color color = Color.white;
		
		for(int y = 0; y < 50; y += 10)
		{	
			for(int x = 0; x < 50; x += 10)
				
			{
				g.setColor(color);
				g.fillRect(x, y, 10, 10);
				
				color = color == Color.white ?
						Color.gray :
						Color.white;
			}
			
		}
		
		return img;
	}
	

	@Override
	protected Object getContent() 
	{
		return wrapper;
	}
	
	private void updateIcon()
	{
		Entity entity = wrapper.entity;
		BufferedImage newImg = graphicImg(entity);
		ImageIcon icon = new ImageIcon(newImg);
		
		setIcon(icon);
	}
	
	public Entity getEntity()
	{
		return wrapper.entity;
	}

	@Override
	public <E extends EntityComponent> void componentAdded(
			Entity src, Class<E> c) 
	{
		if(c.equals(GraphicsComponent.class)) 
		{
			if(prevComp != null)
			{
				prevComp.getGraphic()
						.removeGraphicListener(this);
				prevComp.removeGraphicsComponentListener(this);
			}
			
			listenToCurrCompAndGraphic();
			updateIcon();
		}
	}

	@Override
	public <E extends EntityComponent> void componentRemoved(
			Entity src, Class<E> c) 
	{
		if(c.equals(GraphicsComponent.class)) 
		{
			prevComp.getGraphic()
					.removeGraphicListener(this);
			prevComp.removeGraphicsComponentListener(this);
			prevComp = null;
			updateIcon();
		}
	}

	@Override
	public void graphicChanged(
			GraphicsComponent src,
			Graphic newGraphic, Graphic oldGraphic) 
	{
		oldGraphic.removeGraphicListener(this);
		newGraphic.addGraphicListener(this);
		updateIcon();
	}

	@Override
	public void fieldChanged(Graphic src) 
	{
		updateIcon();		
	}

}
