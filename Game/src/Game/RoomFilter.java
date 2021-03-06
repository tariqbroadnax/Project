package Game;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;

public class RoomFilter 
{
	private List<Rectangle2D.Double> rooms;
	
	private Scene scene;
	
	public RoomFilter(Scene scene)
	{
		this.scene = scene;
		
		rooms = new ArrayList<Rectangle2D.Double>();
	}
	
	public void addRoom(Rectangle2D.Double room)
	{
		rooms.add(room);
	}
	
	public void paint(GraphicsContext gc)
	{
		Point2D.Double playerLoc = scene.getGame()
										.getPlayer()
										.getLoc();
		
		Rectangle2D.Double currRoom = null;
		for(Rectangle2D.Double room : rooms)
			if(room.contains(playerLoc))
			{
				currRoom = room;
				break;
			}
		
		Rectangle2D.Double normScrBound = gc.camera.normalScreenBound();
		
		ShapeGraphic graph = new ShapeGraphic();
		
		graph.setPaint(Color.black);
		if(currRoom == null)
		{
			for(Rectangle2D.Double room : rooms)
			{
				graph.setShape(room);
				graph.paint(gc);
			}
		}
		else
		{
			Rectangle r = gc.camera.boundOnScreen(currRoom),
					  r2 = gc.camera.screenBound();
									
			Area area = new Area(r),
				 area2 = new Area(r2);
			
			area2.subtract(area);
			
			gc.g2d.setClip(area2);
			
			graph.setShape(normScrBound);

			graph.paint(gc);
			
			gc.g2d.setClip(null);
			
		}
	}
}
