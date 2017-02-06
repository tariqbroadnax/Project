package GUI;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import Actions.ActionBuffer;
import Actions.ActionComponent;
import Actions.Move;
import EditorGUI.MouseListener;
import Entity.Entity;
import Graphic.Camera;

public class MouseController implements MouseListener
{
	private Entity player;
	
	private Camera camera;
	
	private Move move;
	
	private ActionBuffer buffer;
	
	public MouseController(
			Entity player, Camera camera,
			ActionBuffer buffer)
	{
		this.player = player;
	
		this.camera = camera;
		
		this.buffer = buffer;
		
		move = null;
	}
	
	private void startNewMovement()
	{
		move = new Move();
		
		move.setSpeed(30);

		player.get(ActionComponent.class)
			  .startAction(move);
	}
	
	private void makePlayerMoveToLoc(Point2D.Double loc)
	{
		if(move == null || move.isFinished())
			startNewMovement();
		
		move.setTarget(loc.x, loc.y);
	}
	
	public void mousePressed(MouseEvent e)
	{
		Point loc = e.getPoint();
		
		Point2D.Double normLoc = camera.normalLocation(loc);
	
		// schedule
		buffer.schedule(() -> makePlayerMoveToLoc(normLoc));
	}
}