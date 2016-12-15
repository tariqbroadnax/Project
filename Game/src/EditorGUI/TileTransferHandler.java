package EditorGUI;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import DataStructure.Tile;
import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;
import Graphic.TileMap2;

public class TileTransferHandler
	implements SelectionTransferHandler
{	
	private Collection<Tile> tiles;
	
	private GUIResources resources;
	
	private SceneEditorPane pane;
	
	public TileTransferHandler(
			GUIResources resources,
			SceneEditorPane pane) 
	{
		this.resources = resources;
		
		this.pane = pane;
		
		tiles = new LinkedList<Tile>();
	}
	
	@Override
	public boolean canImport(List<Object> selection) 
	{	
		if(selection.size() == 0) 
			return false;
		
		for(Object o : selection)
			if(!(o instanceof Tile))
				return false;
		
		return true;
	}

	@Override
	public void setSelection(List<Object> selection) 
	{
		tiles.clear();
		
		for(Object o : selection)
			tiles.add((Tile)o);
	}

	@Override
	public void importSelection() 
	{
		TileMap2 tileMap = resources.getScene()
									.getTileMap();
				
		Point2D.Double normLoc = 
				pane.normalMouseLocation();
		
		if(normLoc == null)
			return;
		
		int row = tileMap.row(normLoc.y),
			col = tileMap.col(normLoc.x);
			
		Iterator<Tile> iter = tiles.iterator();
		
		Tile tile = iter.next(); 
		
		tileMap.set(col, row, tile);		
	}

	@Override
	public void paintSelection(GraphicsContext gc) 
	{
		TileMap2 tileMap = resources.getScene()
				 					.getTileMap();
		
		Point2D.Double normLoc = 
				pane.normalMouseLocation();
		
		if(normLoc == null)
			return;
		
		int row = tileMap.row(normLoc.y),
			col = tileMap.col(normLoc.x);
		
		ShapeGraphic graphic = new ShapeGraphic();
		
		Rectangle2D.Double bound =
				tileMap.tileBound(row, col);
		
		graphic.setShape(bound);
		graphic.setLoc(bound.x, bound.y);

		graphic.paint(gc);

		Iterator<Tile> iter = tiles.iterator();
		
		Tile prevTile = iter.next(); 
		while(iter.hasNext()) 
		{
			Tile tile = iter.next();
			
			row += tile.row - prevTile.row;
			col += tile.col - prevTile.col;
			
			bound = tileMap.tileBound(row, col);
			
			graphic.setShape(bound);
			graphic.setLoc(bound.x, bound.y);
			
			graphic.paint(gc);
									
			prevTile = tile;
		}
	}
}
