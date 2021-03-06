package Graphic;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import Maths.Maths;
import Maths.Vector2D;

public class LayeredGraphic extends Graphic
{
	private ArrayList<Layer> layers;
	
	public LayeredGraphic()
	{
		layers = new ArrayList<Layer>();
	}
	
	public LayeredGraphic(LayeredGraphic graphic)
	{
		layers = new ArrayList<Layer>();
	
		for(Layer layer : graphic.layers)
			layers.add(layer.clone());
	}
	
	public void update(Duration delta)
	{
		for(Layer layers: layers)
			layers.graphic.update(delta);
	}

	@Override
	protected void _paint(GraphicsContext gc) 
	{
		for(Layer layer : layers)
			layer.graphic.paint(gc);
	}
	
	public void add(Graphic graphic) 
	{
		add(graphic, 0, 0);
	}
	
	public void add(Graphic graphic, Vector2D.Double offset) 
	{
		add(graphic, offset.x, offset.y);
	}
	
	public void add(
			Graphic graphic,
			double offsetX, double offsetY) 
	{
		Layer layer = new Layer(graphic, offsetX, offsetY);
		
		layers.add(layer);
	}
	
	public void remove(Graphic graphic)
	{
		layers.removeIf(l -> l.graphic == graphic);
	}

	public void setGraphic(Graphic graphic, int i) {
		layers.get(i).graphic = graphic;
	}
	
	public void setOffset(Vector2D.Double offset, int i) {
		layers.get(i).offset = offset;
	}

	public void setLoc(Point2D.Double loc)
	{
		super.setLoc(loc);	
		
		for(Layer layer : layers)
			layer.setLoc(loc);
	}

	public Graphic getLayer(int layer) {
		return layers.get(layer).graphic;
	}
	
	public Vector2D.Double getLayerOffset(int layer) {
		return layers.get(layer).offset;
	}
	
	@Override
	public Rectangle2D.Double getBound() 
	{	
		Collection<Rectangle2D.Double> bounds = 
				layers.stream()
					   .map(rec -> rec.graphic)
					   .map(graph -> graph.getBound())
					   .filter(bound -> bound != null)
					   .collect(Collectors.toList());
					   
		return Maths.overlappingBound(bounds);
	}
	
	public int size() {
		return layers.size();
	}

	@Override
	public Object clone() {
		return new LayeredGraphic(this);
	}
}
