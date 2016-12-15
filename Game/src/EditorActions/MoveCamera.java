package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;

import javax.swing.AbstractAction;

import Graphic.Camera;
import Maths.Vector2D;

public class MoveCamera extends AbstractAction
{	
	private Camera camera;

	private Vector2D.Double offset;

	public MoveCamera(
			Camera camera, 
			double direction, double magnitude)
	{
		this.camera = camera;
		
		offset = Vector2D.Double.polarVector(
				direction, magnitude);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Point2D.Double cameraFocus = camera.getFocus(),
				 	   newFocus = 
				 	   		offset.getMoved(cameraFocus);
		
		camera.setFocus(newFocus);
	}
}
