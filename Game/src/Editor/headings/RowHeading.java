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

public class RowHeading extends JPanel
	implements SceneListener, MouseListener, MouseMotionListener
{
	private Camera camera;
	
	private EditorResources resources;
	
	private int hoveredRow = -1;
	
	public RowHeading(EditorResources resources, Camera camera)
	{
		this.resources = resources;
		this.camera = camera;
		
		resources.addSceneListener(this);
		
		setPreferredSize(new Dimension(25, 600));
		
		setBackground(Color.white);
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
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
				
		txt.setHeight(5);
		txt.setPaint(Color.black);
		
		graph.setShape(shape);
		graph.setStroke(new BasicStroke(1f));
		
		double x = camera.normalX(0);
		
		for(int row = startRow; row < endRow; row++)
		{
			double y = map.y(row);
			graph.setLoc(x + shape.width/2, y + shape.height/2);
			txt.setLoc(x, y + shape.getHeight());
			txt.setText("" + row);
			
			if(row == hoveredRow)
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
		hoveredRow = -1;
		
		repaint();
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		TileMap tm = resources.scene.getTileMap();
		
		Point mouseLoc = e.getPoint();
		Point2D.Double normLoc = camera.normalLocation(mouseLoc);
		hoveredRow = tm.row(normLoc.y);
		
		repaint();
	}
}