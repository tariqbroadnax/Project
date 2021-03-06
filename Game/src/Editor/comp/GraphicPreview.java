package Editor.comp;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import Graphic.Camera;
import Graphic.Graphic;
import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;
import Maths.Dimension2D;

public class GraphicPreview extends JPanel
{
	private Graphic graph;
	
	private final int MAX_SIDE_LEN = 100;
	
	private double zoom;
	
	public GraphicPreview()
	{
		graph = new ShapeGraphic();

		updatePrefSize();	
	}

	private void updatePrefSize()
	{
		Camera camera = new Camera();
		
		Dimension2D.Double size = graph.getSize();
		Dimension prefSize = camera.sizeOnScreen(size);
		
		if(prefSize.width > MAX_SIDE_LEN ||
		   prefSize.height > MAX_SIDE_LEN)
		{
			zoom = prefSize.width > prefSize.height ?
					MAX_SIDE_LEN * 1.0 / prefSize.width :
					MAX_SIDE_LEN * 1.0 / prefSize.height;
		
			prefSize.width *= zoom;
			prefSize.height *= zoom;
		}
		else
			zoom = 1;
		
		prefSize.width += 5;
		prefSize.height += 5; // insets
		
		setPreferredSize(prefSize);	
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Camera camera = new Camera();
		
		camera.setZoom(zoom);
		
		Dimension size = getSize();
		
		Point2D.Double graphLoc = graph.getLoc();
		
		Dimension2D.Double normSize = camera.normalSize(size);
		
		camera.setFocusTL(graphLoc.x - normSize.width/2,
						  graphLoc.y - normSize.height/2);
		
		GraphicsContext gc = new GraphicsContext(g, camera);
	
		graph.paint(gc);
	}
	
	public void setGraphic(Graphic graph) 
	{
		this.graph = graph;
		
		updatePrefSize();
		
		revalidate();
		repaint();
	}
}
