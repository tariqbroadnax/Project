package Game;

import java.awt.geom.Point2D;

import Entity.Terrain;
import Maths.Dimension2D;

public class TerrainMap 
{
	private Scene scene;
	
	private Point2D.Double loc;
	private int rows, cols;
	private Dimension2D.Double size;
	
	private Terrain[][] terrain;
	
	public TerrainMap(Scene scene)
	{
		this.scene = scene;
		
		loc = new Point2D.Double(-100, -100);
		rows = cols = 20;
		size = new Dimension2D.Double(10, 10);
	
		terrain = new Terrain[cols][rows];
	
		/*for(int r = 0; r < rows; r+=2)
			for(int c = 0; c < cols; c+=2)
			{
				if(Math.random() < 0.5)
					set(c,r,true);
			}*/
		
		for(int i = 0; i < cols; i++)
		{
			set(i, 0, true);
			set(i, rows - 1, true);
		}
		
		for(int i = 0; i < rows; i++)
		{
			set(0, i, true);
			set(cols - 1, i, true);
		}
	}
	
	public void set(
			int row, int col, boolean occupied)
	{
		if(occupied == (terrain[col][row] != null))
			return;
		
		if(!occupied)
		{
			Terrain terr = terrain[col][row];
			scene.removeEntity(terr);
			terrain[col][row] = null;
		}
		else
		{
			Terrain terr = new Terrain();
			terr.setLoc(loc.x + col * size.width,
						loc.y + row * size.height);
			scene.addEntity(terr);
			terrain[col][row] = terr;
		}
	}
}
