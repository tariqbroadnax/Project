package Actions;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import Game.GameResources;
import Graphic.Camera;

public class PlayerMouseInputManager 
	extends SyncGameAction
{
	private Point2D.Double normLoc;
	private Camera camera;
	private PlayerMouseInputHandler handler;

	private MovePlayer movePlayer;
	
	public PlayerMouseInputManager(
			GameResources resources) 
	{
		super(resources);
		
		camera = resources.camera;
		handler = movePlayer = new MovePlayer(resources);
	}

	@Override
	public void invoke() 
	{
		handler.invoke(normLoc);
		handler.dispose();		
		handler = movePlayer;
	}

	public void setHandler(
			PlayerMouseInputHandler handler)
	{
		if(this.handler != null)
			this.handler.dispose();
		
		this.handler = handler;
	}
	
	public void mousePressed(MouseEvent e) 
	{
		super.mousePressed(e);
	
		Point loc = e.getPoint();
		normLoc = camera.normalLocation(loc);
	}
}
