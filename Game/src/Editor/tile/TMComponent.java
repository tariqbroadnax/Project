package Editor.tile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import Editor.ComponentListener;
import Editor.EditorResources;
import Editor.SceneEditor;
import Editor.selection.SelectionHandler;
import Editor.selection.SelectionListener;
import Graphic.Camera;
import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;
import Maths.Dimension2D;
import Tileset.TMCell;
import Tileset.TileMap;

public class TMComponent extends JPanel
	implements ComponentListener, SelectionListener
{
	private EditorResources resources;
		
	public TMComponent(
			EditorResources resources, SceneEditor editor)
	{
		this.resources = resources;
		
		setOpaque(false);
		
		editor.addComponentListener(this);
		
		resources.getSelectionHandler()
				 .addSelectionListener(this);
	}
	
	public void paintComponent(Graphics g)
	{		
		TileMap tm = resources.scene.getTileMap();
		
		SelectionHandler handler = resources.getSelectionHandler();
		
		Camera camera = resources.getCamera();
		
		GraphicsContext gc = new GraphicsContext(
				g.create(), camera);
		
		Dimension2D.Double tileSize = tm.tileSize();
		Rectangle2D.Double tileBound = new Rectangle2D.Double(
				0, 0, tileSize.width, tileSize.height);
		ShapeGraphic graph = new ShapeGraphic();

		graph.setShape(tileBound);
		graph.setPaint(new Color(0,0,255,120));
		
		for(Object obj : handler.getSelection())
		{
			if(obj instanceof TMCell)
			{
				TMCell cell = (TMCell) obj;
				double x = tm.x(cell.col),
					   y = tm.y(cell.row);
				
				graph.setLoc(x + tileSize.width/2,
							 y + tileSize.height/2);
				
				graph.paint(gc);
			}
		}
	}
	
	@Override
	public void selectionChanged()
	{
		repaint();
	}
	
	@Override
	public void componentResized(ComponentEvent e)
	{
		Dimension size = e.getComponent()
						  .getSize();
		
		setSize(size);
	}
}
