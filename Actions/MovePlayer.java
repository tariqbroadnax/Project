package Actions;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.List;

import Behaviour.BasicAttackBehaviour;
import Behaviour.PathingBehaviour;
import Game.Entity;
import Game.GameResources;
import Game.Player;
import Game.Scene;
import Graphic.Camera;

public class MovePlayer extends SyncGameAction
	implements PlayerMouseInputHandler
{
	private Scene scene;
	private Camera camera;
	
	private BasicAttackBehaviour attack;
	private PathingBehaviour pathing;
	
	private Point pt;
	
	public MovePlayer(GameResources resources) 
	{
		super(resources);
		
		pathing = 
				resources.player.getPathingBehaviour();
		attack =
				resources.player.getAttackBehaviour();
		
		scene = resources.scene;
		camera = resources.camera;		
	}

	@Override
	public void invoke() 
	{
		Point2D.Double normLoc =
				camera.normalLocation(pt);
		
		invoke(normLoc);
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		super.mousePressed(e);
		pt = e.getPoint();		
	}

	@Override
	public void invoke(Double normLoc)
	{
		List<Entity> entities = 
				scene.entitiesVisibleAtLocation(normLoc);

		if(!entities.isEmpty())
		{
			int i = 0;
			
			while(i < entities.size() &&
				  entities.get(i) instanceof Player)
				i++;
	
			if(i < entities.size())
				attack.setTarget(entities.get(i));
			
			if(pathing.hasTargets())
				pathing.clearTargets();
		}
		else
		{
			pathing.setTarget(normLoc);
			
			if(attack.hasTarget())
				attack.removeTarget();
		}
	}

	@Override
	public void dispose() {}
}
