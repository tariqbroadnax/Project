package Editor;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import Graphic.Graphic;
import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;
import Maths.Dimension2D;
import Tileset.Tile;
import Tileset.TileMap;
import Utilities.Collections2;

public class TileTransferHandler 
	extends SelectionTransferHandler
{
	private Tile tile;
	
	private EditorResources resources;
			
	public TileTransferHandler(EditorResources resources) 
	{
		super(resources);
	
		this.resources = resources;
	}

	@Override
	public boolean canImport(Object obj) 
	{
		return obj instanceof Tile;
	}

	@Override
	public void setSelection(Object obj) {
		tile = (Tile) obj;
	}

	@Override
	public void importSelection(Point2D.Double normLoc) 
	{
		List<Graphic> graphics = 
				resources.scene.graphicsAtLocation(normLoc);
		
		TileMap tm = (TileMap) Collections2.findFirst(
				graphics, g -> g instanceof TileMap);
		
		if(tm != null)
		{
			int row = tm.row(normLoc.y),
				col = tm.col(normLoc.x);
			
			if(0 <= row && row < tm.rows && 
			   0 <= col && col < tm.cols)
				tm.set(row, col, tile);
		}
	}

	@Override
	public void paintSelection(GraphicsContext gc,
							   Point2D.Double normLoc) 
	{		
		List<Graphic> graphics = 
				resources.scene.graphicsAtLocation(normLoc);
		
		TileMap tm = (TileMap) Collections2.findFirst(
				graphics, g -> g instanceof TileMap);
		
		if(tm != null)
		{
			int row = tm.row(normLoc.y),
					col = tm.col(normLoc.x);
				
				if(!(0 <= row && row < tm.rows && 
				     0 <= col && col < tm.cols))
					return;
				
				double x = tm.x(col), y = tm.y(row);

				Dimension2D.Double tileSize = tm.tileSize();
			
				Rectangle2D.Double tileBound =
						new Rectangle2D.Double(
								0, 0, tileSize.width, 
									  tileSize.height);
			
				ShapeGraphic shape = new ShapeGraphic();
				
				shape.setLoc(x + tileSize.width/2, 
							 y + tileSize.height/2);
				
				shape.setShape(tileBound);
				
				Color selectionColor = new Color(0, 0, 255, 120);
				shape.setPaint(selectionColor);
				
				shape.paint(gc);
		}
	}
}