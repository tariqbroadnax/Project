package EntityComponent;

import Graphic.Graphic;
import Graphic.GraphicsContext;

public class TemporaryGraphic extends Graphic
{
	private Graphic graphic;
	
	private int remainingFrames;
	
	public TemporaryGraphic(Graphic graphic, int remainingFrames)
	{
		this.graphic = graphic;
		
		this.remainingFrames = remainingFrames > 0 ?
				remainingFrames : 0;
	}

	@Override
	protected void _paint(GraphicsContext gc)
	{
		remainingFrames = remainingFrames > 0 ?
				remainingFrames : 0;
		
		graphic.paint(gc);
	}
	
	public int getRemainingFrames()
	{
		return remainingFrames;
	}

	@Override
	protected Graphic _clone()
	{
		return null;
	}
}
