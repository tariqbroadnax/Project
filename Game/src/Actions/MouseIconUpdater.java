package Actions;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import javax.imageio.ImageIO;

import EditorGUI.MouseMotionListener;
import Entity.Entity;
import Entity.Monster;
import Entity.NPC;
import Game.GameResources;
import Game.Scene;
import Game.Updatable;
import Graphic.Camera;

public class MouseIconUpdater
	implements MouseMotionListener,
			   Updatable
{	
	private Scene scene;
	private Camera camera;
	
	private Cursor defaultCursor, attackCursor, chatCursor;
		
	private Point pt;
	private Component comp;
	
	public MouseIconUpdater(GameResources resources)
	{
		this.scene = resources.scene;
		this.camera = resources.camera;
		
		resources.updater.addUpdatable(this);
		
		BufferedImage img = null, img2 = null;
		try {
			img = ImageIO.read(new File("swordicon.png"));
			img2 = ImageIO.read(new File("chat.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	
		attackCursor = toolkit.createCustomCursor(
				img, new Point(), "");
		chatCursor = toolkit.createCustomCursor(
				img2, new Point(), "");
		
		defaultCursor = Cursor.getDefaultCursor();
	}
	
	public void update(Duration delta)
	{
		updateMouseIcon();
	}
	
	private void updateMouseIcon()
	{
		if(pt == null) return;
		
		Point2D.Double normLoc = 
				camera.normalLocation(pt);
		
		List<Entity> entities = 
				scene.entitiesVisibleAtLocation(normLoc);

		if(entities.isEmpty()) {
			comp.setCursor(defaultCursor);
			return;
		}		
		
		Entity entity = entities.get(0);
		
		if(entity instanceof NPC)
			comp.setCursor(chatCursor);
		else if(entity instanceof Monster)
			comp.setCursor(attackCursor);
		else
			comp.setCursor(defaultCursor);
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		pt = e.getPoint();
		
		if(comp == null)
			comp = e.getComponent();
	}
}
