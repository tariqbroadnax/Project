package Game;

import java.awt.geom.Rectangle2D;
import java.time.Duration;

import Entity.Entity;
import Graphic.Camera;

public class CameraController implements Updatable
{
	private Scene scene;
	
	private Entity player;
	
	private Camera camera;
	
	public CameraController(Scene scene, Entity player, Camera camera)
	{
		this.scene = scene;
		this.player = player;
		this.camera = camera;
	}
	
	@Override
	public void update(Duration delta) 
	{
		camera.setFocus(player.getLoc());
		
		Rectangle2D.Double scnBound = scene.getBound(),
						   bound = camera.normalScreenBound();
		
		double x = player.getLoc().x,
			   y = player.getLoc().y;
		
		x = bound.x < scnBound.x ? 
			scnBound.x + bound.width/2 : x;
		x = bound.x + bound.width > scnBound.x + scnBound.width ?
			scnBound.x + scnBound.width - bound.width/2 : x;
		
		y = bound.y < scnBound.y ? 
				scnBound.y + bound.height/2 : y;
		y = bound.y + bound.height > scnBound.y + scnBound.height ?
			scnBound.y + scnBound.height - bound.height/2 : y;
			
		camera.setFocus(x, y);
	}
}
