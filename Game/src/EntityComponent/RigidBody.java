package EntityComponent;

import static Utilities.ShapeUtilities.collides;
import static Utilities.ShapeUtilities.moveShape;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import Maths.Circle2D;
import Maths.Vector2D;
import Utilities.Pack;

public class RigidBody 
	implements Cloneable, Serializable
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
	
	public void addComponent(
			Vector2D.Double shift,
			RectangularShape comp)
	{
		// moveShape(shift.getMoved(loc), comp);
		shifts.add(shift);
		comps.add(comp);
	}
	
	public void addComponent(
			RectangularShape comp)
	{
		addComponent(new Vector2D.Double(), comp);
	}
	
	public void setLoc(Point2D.Double loc)
	{
		this.loc = loc;
		
		Iterator<Vector2D.Double> shiftsIter = shifts.iterator();
		Iterator<RectangularShape> compsIter = comps.iterator();
		
		while(shiftsIter.hasNext())
		{
			RectangularShape comp = compsIter.next();
			
			Vector2D.Double shift = new Vector2D.Double( 
					shiftsIter.next());
			
			shift.x -= comp.getWidth() / 2;
			shift.y -= comp.getHeight() / 2;
						
			Point2D.Double newLoc = shift.getMoved(loc);
			
			moveShape(newLoc, comp);
		}
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
	
	public Pack<RectangularShape, RectangularShape> 
		collidesWith(Circle2D.Double circ)
	{
		for(RectangularShape myComp : comps)
		{
			if(myComp instanceof Circle2D.Double) {
				//System.out.println(collides(myComp, circ) + " " + circ + " " + myComp);
			}
			
			if(collides(myComp, circ))
			{
				return new Pack<RectangularShape, RectangularShape>(
									myComp, circ);	
			}
		}
		
		return null;
	}
	
	public Rectangle2D.Double union()
	{
		if(comps.size() == 0)
			return null;
		
		double minX = Double.POSITIVE_INFINITY,
			   minY = minX,
			   maxX = Double.NEGATIVE_INFINITY,
			   maxY = maxX;
		
		for(RectangularShape comp : comps)
		{
			double minX2 = comp.getMinX(),
				   minY2 = comp.getMinY(),
				   maxX2 = comp.getMaxX(),
				   maxY2 = comp.getMaxY();
			
			minX = minX < minX2 ? minX : minX2;
			minY = minY < minY2 ? minY : minY2;
			maxX = maxX > maxX2 ? maxX : maxX2;
			maxY = maxY > maxY2 ? maxY : maxY2;
		}
		
		return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
	}
	
	public Collection<RectangularShape> getComponents()
	{
		return comps;
	}
	
	public Object clone()
	{
		return new RigidBody(this);
	}
	
	public String toString()
	{
		String str = super.toString();
		
		for(RectangularShape comp : comps)
			str += "\n" + comp.toString();
		
		return str;
	}
}
