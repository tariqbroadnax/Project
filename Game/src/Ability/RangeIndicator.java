package Ability;

import java.awt.geom.Point2D;

import Graphic.Graphic;

public abstract class RangeIndicator 
{
	protected Graphic graphic;
	
	public abstract void notifyMouseLoc(
			Point2D.Double normLoc);
	
	public Graphic getGraphic() {
		return graphic;
	}
}
