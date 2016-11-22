package Actions;

import java.awt.geom.Point2D;

public interface PlayerMouseInputHandler
{
	public void invoke(Point2D.Double normLoc);
	public void dispose();
}
