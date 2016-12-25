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

import javax.swing.JPanel;

import Editor.EditorResources;
import Editor.SceneListener;
import EditorGUI.MouseListener;
import EditorGUI.MouseMotionListener;
import Graphic.Camera;
import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;
import Graphic.TextGraphic;
import Maths.Dimension2D;
import Tileset.TileMap;

public class ColumnHeading extends JPanel
	implements SceneListener, MouseListener,
			   MouseMotionListener
{
	private Camera camera;
	
	private EditorResources resources;
	
	private int hoveredCol = -1;
	
	public ColumnHeading(EditorResources resources, Camera camera)
	{
		this.resources = resources;
		this.camera = camera;
		
		resources.addSceneListener(this);
		
		setPreferredSize(new Dimension(800, 25));
	
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setBackground(Color.white);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		TileMap map = resources.scene.getTileMap();
		
		Dimension2D.Double tileSize = map.tileSize();
	
		GraphicsContext gc = new GraphicsContext(
				(Graphics2D)g.create(), camera);

		gc.g2d.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		double shapeWidth = tileSize.getWidth(),
			   shapeHeight = camera.normalHeight(getHeight());
		
		Rectangle2D.Double shape = new Rectangle2D.Double(
				0, 0, shapeWidth, shapeHeight);
		
		
		ShapeGraphic graph = new ShapeGraphic();
		TextGraphic txt = new TextGraphic();
				
		txt.setPaint(Color.black);
		txt.setHeight(5);
		
		graph.setShape(shape);
		graph.setStroke(new BasicStroke(1f));
		
		
		double y = camera.normalY(0);
		
		Rectangle2D.Double bound = camera.normalViewBound();
		
		int startCol = map.col(bound.x),
			endCol = map.col(bound.x + bound.width) + 1;
		
		for(int col = startCol; col < endCol; col++)
		{
			double x = map.x(col);
			graph.setLoc(x + shape.width/2, y + shape.height/2);
			txt.setLoc(x, y + shape.getHeight());
			txt.setText("" + col);
			
			if(col == hoveredCol)
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
	public void mouseExited(MouseEvent e)
	{
		hoveredCol = -1;
		
		repaint();
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		TileMap tm = resources.scene.getTileMap();
		
		Point mouseLoc = e.getPoint();
		Point2D.Double normLoc = camera.normalLocation(mouseLoc);
		hoveredCol = tm.col(normLoc.x);
		
		repaint();
	}
}