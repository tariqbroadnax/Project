package Quest;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import Entity.Entity;
import Entity.EntityListener;
import EntityComponent.GraphicsComponent;
import Graphic.ShapeGraphic;
import Maths.Circle;

public class TravelTask extends Task
	implements EntityListener
{
	private Entity ent, debugEnt;
	
	private Circle zone;

	public TravelTask()
	{
		zone = new Circle(0, 0, 20);
	}
	
	@Override
	public void start(Entity ent) 
	{		
		this.ent = ent;
		
		Point2D.Double entLoc = ent.getLoc();
		
		// DEBUG ---
		debugEnt = new Entity();
		
		Ellipse2D.Double ellipse = new Ellipse2D.Double(
				zone.x - zone.radius,
				zone.y - zone.radius,
				2 * zone.radius, 2 * zone.radius);
		
		ShapeGraphic graph = new ShapeGraphic();
		
		graph.setShape(ellipse);
		
		GraphicsComponent comp = new GraphicsComponent();
		
		comp.setGraphic(graph);
		
		debugEnt.add(comp);
		debugEnt.setLoc(zone.x, zone.y);
		
		ent.getSceneLoc()
		   .addEntityNow(debugEnt);
		// ---------
		
		if(zone.contains(entLoc.x, entLoc.y))
			taskCompleted();
		else
			ent.addEntityListener(this);
	}
	
	public void setTargetCenter(double x, double y)
	{
		zone.x = x; zone.y = y;
	}
	
	public void setTargetCenter(Point2D.Double target) {
		zone.x = target.x; zone.y = target.y;
	}
	
	public void setTargetRadius(double radius) {
		zone.radius = radius;
	}
	
	public void locationChanged(Point2D.Double prevLoc,
							    Point2D.Double newLoc)
	{						
		if(zone.contains(newLoc.x, newLoc.y))
		{
			ent.removeEntityListeners(this);
			ent.getSceneLoc()
			   .removeEntity(debugEnt);
			
			taskCompleted();
		}
	}
}