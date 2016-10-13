package Graphic;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LayeredGraphic extends Graphic
{
	private ArrayList<GraphicLayerRecord> records;
	
	public LayeredGraphic()
	{
		records = new ArrayList<GraphicLayerRecord>();
	}
	
	public LayeredGraphic(LayeredGraphic graphic)
	{
		records = new ArrayList<GraphicLayerRecord>();
	
		for(GraphicLayerRecord record : graphic.records)
			records.add(record.clone());
	}
	
	public void addGraphic(Graphic graphic, Vector2D.Double offset)
	{
		records.add(new GraphicLayerRecord(graphic, offset));
	}
	
	public void setGraphic(Graphic graphic, int i)
	{
		records.get(i).graphic = graphic;
	}
	

	@Override
	protected Graphic _clone()
	{
		return new LayeredGraphic(this);
	}
	
	public void update(Duration delta)
	{
		for(GraphicLayerRecord record : records)
			record.graphic.update(delta);
	}

	@Override
	protected void _paint(GraphicsContext gc) 
	{
		for(GraphicLayerRecord record : records)
		{
			record.graphic.paint(gc);
		}
	}
	
	public void setLoc(Point2D.Double loc)
	{
		super.setLoc(loc);	
		
		for(GraphicLayerRecord record : records)
			record.setLoc(loc);
	}

	@Override
	public void paint(Point screenLoc, Dimension dim, Graphics2D g2d) {}

	@Override
	public Rectangle2D.Double getBound() 
	{	
		Collection<Rectangle2D.Double> bounds = 
				records.stream()
					   .map(rec -> rec.graphic)
					   .map(graph -> graph.getBound())
					   .filter(bound -> bound != null)
					   .collect(Collectors.toList());
					   
		return Maths.Maths.overlappingBound(bounds);
	}
	
	public List<GraphicLayerRecord> getGraphicLayerRecords()
	{
		return Collections.unmodifiableList(records);
	}
}
