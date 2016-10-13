package EntityComponent;

import static Utilities.ShapeUtilities.collides;
import static Utilities.ShapeUtilities.moveShape;

import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import Graphic.Vector2D;
import Utilities.Pack;

public class RigidBody
{
	private Point2D.Double loc;
	
	private Collection<Vector2D.Double> shifts;
	private Collection<RectangularShape> comps;
	
	public RigidBody()
	{
		loc = new Point2D.Double();
		
		shifts = new LinkedList<Vector2D.Double>();
		comps = new LinkedList<RectangularShape>();
	}
	
	public RigidBody(RigidBody body)
	{
		loc = new Point2D.Double(
				body.loc.x, body.loc.y);
		
		shifts = new LinkedList<Vector2D.Double>();
		comps = new LinkedList<RectangularShape>();
		
		for(Vector2D.Double shift : body.shifts)
			shifts.add(new Vector2D.Double(shift));
		
		for(RectangularShape comp : body.comps)
			comps.add((RectangularShape)comp.clone());
	}
	
	public void addComponent(Vector2D.Double shift, RectangularShape comp)
	{
		moveShape(shift.getMoved(loc), comp);
		
		shifts.add(shift);
		comps.add(comp);
	}
	
	public void setLoc(Point2D.Double loc)
	{
		this.loc = loc;
		
		Iterator<Vector2D.Double> shiftsIter = shifts.iterator();
		Iterator<RectangularShape> compsIter = comps.iterator();
		
		while(shiftsIter.hasNext())
			moveShape(shiftsIter.next().getMoved(loc), compsIter.next());
	
	}
	
	public Point2D.Double getCenter()
	{
		if(comps.size() == 0)
			return loc;
		
		double xs = 0, ys = 0;
		
		int size = 0;
		
		for(RectangularShape comp : comps)
		{
			xs += comp.getCenterX();
			ys += comp.getCenterY();
			size++;
		}
		
		return new Point2D.Double(xs / size, ys / size);
	}
	
	// returns first collision found 
	public Pack<RectangularShape, RectangularShape> 
			collidesWith(RigidBody rigidBody)
	{
		for(RectangularShape myComp : comps)
		{
			for(RectangularShape otherComp : rigidBody.getComponents())
				if(collides(myComp, otherComp))
					return new Pack<RectangularShape, RectangularShape>(
									myComp, otherComp);	
		}
		
		return null;
	}
	
	public Collection<RectangularShape> getComponents()
	{
		return comps;
	}
	
	public String toString()
	{
		String str = super.toString();
		
		for(RectangularShape comp : comps)
			str += "\n" + comp.toString();
		
		return str;
	}
}
