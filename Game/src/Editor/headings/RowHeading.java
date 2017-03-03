package Editor.headings;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import Editor.EditorResources;
import Editor.SceneListener;
import Editor.selection.SelectionHandler;
import Editor.selection.SelectionListener;
import EditorGUI.MouseListener;
import EditorGUI.MouseMotionListener;
import Graphic.Camera;
import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;
import Graphic.TextGraphic;
import Maths.Dimension2D;
import Maths.Maths;
import Tileset.TMCell;
import Tileset.TileMap;

public class RowHeading extends JPanel
	implements SceneListener, MouseListener,
			   MouseMotionListener, SelectionListener
{
	private static int NO_ROW = Integer.MIN_VALUE;
		
	private EditorResources resources;
	
	private int hoveredRow = NO_ROW;
	
	private List<Integer> selectedRows;
	
	public RowHeading(EditorResources resources)
	{
		this.resources = resources;
		
		selectedRows = new ArrayList<Integer>();
		
		resources.addSceneListener(this);
		
		resources.getSelectionHandler()
				 .addSelectionListener(this);
		
		setPreferredSize(new Dimension(25, 600));
		
		setBackground(Color.white);
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Camera camera = resources.getCamera();
		
		TileMap map = resources.scene.getTileMap();
		
		Rectangle2D.Double bound = camera.normalViewBound();
		
		int startRow = map.row(bound.y),
			endRow = map.row(bound.y + bound.height) + 1;
		
		Dimension2D.Double tileSize = map.tileSize();
	
		GraphicsContext gc = new GraphicsContext(
				(Graphics2D)g.create(), camera);

		gc.g2d.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		double shapeWidth = camera.normalWidth(getWidth()),
			   shapeHeight = tileSize.getHeight();
		
		Rectangle2D.Double shape = new Rectangle2D.Double(
				0, 0, shapeWidth, shapeHeight);
		
		ShapeGraphic graph = new ShapeGraphic();
		TextGraphic txt = new TextGraphic();
			
		txt.setCharHeight(6);
		txt.setPaint(Color.black);
		
		graph.setShape(shape);
		graph.setStroke(new BasicStroke(1f));
		
		double x = camera.normalX(0);
		
		for(int row = startRow; row < endRow; row++)
		{
			double y = map.y(row);
			
			graph.setLoc(x + shape.width/2, y + shape.height/2);
			
			if(Maths.len(row) <= 2)
				txt.setCharWidth(3);
			else
				txt.setCharWidth(2);
			
			txt.setLoc(x, y + shape.getHeight());
			txt.setText("" + row);
			
			if(row == hoveredRow || selectedRows.contains(row))
			{
				graph.setFilled(true);
				graph.setPaint(new Color(0,0,255,120));
			}
			else
			{
				graph.setFilled(false);
				graph.setPaint(Color.black);
			}

			graph.paint(gc);
			txt.paint(gc);
		}
		
		gc.g2d.dispose();
	}
	
	@Override
	public void sceneChanged()
	{
		repaint();
	}
	
	@Override
	public void selectionChanged()
	{
		updateSelectedRows();
		repaint();
	}
	
	private void updateSelectedRows()
	{
		TileMap tm = resources.scene.getTileMap();
		
		SelectionHandler handler = resources.getSelectionHandler();
		
		selectedRows.clear();
		
		for(int row = 0; row < tm.rows(); row++)
		{
			List<TMCell> cells = tm.getRowCells(row);
			
			if(handler.isSelections(cells))
				selectedRows.add(row);
		}
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		hoveredRow = NO_ROW;
		
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{		
		checkAndSelect();
		
		repaint();
	}
	
	private void checkAndSelect()
	{
		TileMap tm = resources.scene.getTileMap();
		List<TMCell> cells = tm.getRowCells(hoveredRow);
	
		SelectionHandler handler = resources.getSelectionHandler();
		
		if(handler.onlySelections(cells))
			handler.removeSelection();
		else			
			handler.setSelections(cells, false);
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		Point mouseLoc = e.getPoint();
		
		updateHoveredRow(mouseLoc);
		repaint();
	}
	
	private void updateHoveredRow(Point mouseLoc)
	{
		TileMap tm = resources.scene.getTileMap();
		Camera camera = resources.getCamera();
		
		Point2D.Double normLoc = camera.normalLocation(mouseLoc);
		hoveredRow = tm.row(normLoc.y);
	}
}
