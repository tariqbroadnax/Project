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
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import EditorGUI.MouseMotionListener;
import Game.Entity;
import Game.Player;
import Game.Scene;
import Graphic.Camera;

public class MouseIconUpdater
	implements MouseMotionListener
{	
	private Scene scene;
	private Camera camera;
	
	private Cursor defaultCursor, attackCursor;
	
	private Timer timer;
	
	private Point pt;
	private Component comp;
	
	public MouseIconUpdater(Scene scene, Camera camera)
	{
		this.scene = scene;
		this.camera = camera;
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("swordicon.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	
		attackCursor = toolkit.createCustomCursor(
				img, new Point(), "");
		defaultCursor = Cursor.getDefaultCursor();
	
		timer = new Timer(150, e -> updateMouseIcon());
		
		timer.start();
	}
	
	private void updateMouseIcon()
	{
		if(pt == null) return;
		
		Point2D.Double normLoc = 
				camera.normalLocation(pt);
		
		List<Entity> entities = 
				scene.entitiesVisibleAtLocation(normLoc);

		if(entities.size() > 0 &&
		   !(entities.get(0) instanceof Player))
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
