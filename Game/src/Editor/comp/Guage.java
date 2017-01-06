package Editor.comp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import Graphic.Camera;
import Graphic.GraphicsContext;
import Graphic.LineGraphic;
import Graphic.ShapeGraphic;
import Graphic.TextGraphic;
import Maths.Circle2D;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Guage extends JComponent
{
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		Dimension size = getSize();
		
		Camera camera = new Camera();
		
		camera.setTargetAspectRatio(size.width * 1.0 / size.height);
		camera.setScreenDimension(size);
		
		GraphicsContext gc = new GraphicsContext(g, camera);

		gc.g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		Circle2D.Double circ = new Circle2D.Double(0, 0, 50);
		
		ShapeGraphic shapeGraph = new ShapeGraphic();
		
		shapeGraph.setShape(circ);
		shapeGraph.setPaint(Color.black);
		shapeGraph.setFilled(true);
		
		shapeGraph.paint(gc);
		
		LineGraphic lineGraph = new LineGraphic();
		
		lineGraph.setPaint(Color.white);
	
		for(double angle = 0; angle < 2 * Math.PI; angle += Math.PI/8)
		{
			lineGraph.setPt1(50 * cos(angle), 50 * sin(angle));
			lineGraph.setPt2(40 * cos(angle), 40 * sin(angle));
			
			lineGraph.paint(gc);
		}
		
		TextGraphic txt = new TextGraphic();
		
		txt.setPaint(Color.white);
		txt.setText("Hello World");
		
		txt.paint(gc);
	}
}
