package Game;

import java.awt.Color;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import Graphic.GraphicsContext;
import Maths.Circle2D;

public class LightMap implements Serializable
{	
	private Color ambientColor;
	
	private Collection<Light> lights;
	
	private double t;
	
	public LightMap()
	{
		ambientColor = new Color(0, 0, 0, 120);
	
		lights = new LinkedList<Light>();
		
		lights.add(new Light());		
	}
	
	public void setAmbientVisibility(double visibilityPercentage)
	{
		if(visibilityPercentage < 0 || 
		   visibilityPercentage > 100)
			return;
		
		ambientColor = new Color(0, 0, 0,
				255 - (int)(255 * visibilityPercentage));
	}
	
	public void paint(GraphicsContext gc)
	{		
		Point2D.Double screenFocus = gc.screenLoc(gc.focus);
		
		Rectangle2D.Double bound =
				new Rectangle2D.Double(
					    screenFocus.x - gc.screenDim.width / 2.0,
					    screenFocus.y - gc.screenDim.height / 2.0,
						gc.screenDim.width, gc.screenDim.height);
		
		Area area = new Area(bound);
		for(Light light : lights)
		{
			Circle2D.Double lightBound =
					light.screenBound(gc);
			
			if(Maths.Maths.overlaps(lightBound, bound))
			{
				area.subtract(new Area(lightBound));
				light.paint(gc, ambientColor);
			}
		}
		
		gc.g2d.setColor(ambientColor);
		
		gc.g2d.fill(area);		
	}
}
