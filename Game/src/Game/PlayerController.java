package Game;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.time.Duration;

import Actions.ActionBuffer;
import EditorGUI.MouseListener;
import EditorGUI.MouseMotionListener;
import Entity.Entity;
import EntityComponent.AbilityComponent;
import Graphic.Camera;
import Movement.Force;
import Movement.MovementComponent;

public class PlayerController implements KeyListener, MouseListener, MouseMotionListener,
	Updatable
{
	private Entity player;
	
	private Camera camera;
	
	private Force force;
	
	private ActionBuffer buffer;
	
	private enum VertDir {UP, DOWN, NONE}
	private enum HorzDir {LEFT, RIGHT, NONE}
	
	private VertDir vert = VertDir.NONE;
	private HorzDir horz = HorzDir.NONE;
	
	private Point p;
	
	public PlayerController(
			Entity player, Camera camera,
			Updater updater)
	{
		this.player = player;
		
		this.camera = camera;
		
		force = new Force();
		
		updater.addUpdatable(this);
		
		player.get(MovementComponent.class)
			  .getMovement()
			  .addForce(force);
	}
	
	public void update(Duration delta)
	{
		double pi = Math.PI;
		
		double x = horz == HorzDir.LEFT ? -1 :
		           horz == HorzDir.RIGHT ? 1 : 0,
		       y = vert == VertDir.UP ? -1 :
		    	   vert == VertDir.DOWN ? 1 : 0;
		
		if(x == 0 && y == 0)
		{
			force.setSpeed(0);
		}
		else
		{
			force.setSpeed(-1);
			
			double angle = Math.atan2(y, x);
			
			force.setDirection(angle);
		}
		
		if(p != null)
		{
			Point2D.Double loc = camera.normalLocation(p);
			
			player.get(AbilityComponent.class)
				  .castPointAbility(0, loc);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		p = e.getPoint();
	}
	
	public void mouseDragged(MouseEvent e)
	{
		p = e.getPoint();	
	}
	
	public void mouseReleased(MouseEvent e)
	{
		p = null;
	}
		
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar() == 'w')
			vert = VertDir.UP;
		else if(e.getKeyChar() == 's')
			vert = VertDir.DOWN;
		else if(e.getKeyChar() == 'a')
			horz = HorzDir.LEFT;
		else if(e.getKeyChar() == 'd')
			horz = HorzDir.RIGHT;
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyChar() == 'w' && vert == VertDir.UP ||
		   e.getKeyChar() == 's' && vert == VertDir.DOWN)
			vert = VertDir.NONE;
		
		if(e.getKeyChar() == 'a' && horz == HorzDir.LEFT ||
		   e.getKeyChar() == 'd' && horz == HorzDir.RIGHT)
			horz = HorzDir.NONE;		
	}

	@Override
	public void keyTyped(KeyEvent e) {}
}
