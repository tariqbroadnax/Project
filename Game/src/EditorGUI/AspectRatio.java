package EditorGUI;

import java.awt.Dimension;

public class AspectRatio 
{
	private double ratio;
	
	public AspectRatio(Dimension dim)
	{
		ratio = 1.0 * dim.width / dim.height;
	}
		
	public Dimension dimensionOfWidth(int width)
	{
		int height = (int)(width / ratio);
		
		return new Dimension(width, height);
	}
	
	public Dimension dimensionOfHeight(int height)
	{
		int width = (int)(height * ratio);
		
		return new Dimension(width, height);
	}
	
	public Dimension smallestValidDimension(
			Dimension dim,
			int minReqWidth, int minReqHeight)
	{
		if(dim.width < minReqWidth && 
		   dim.height < minReqHeight)
		{
			if(ratio < 1)
				return dimensionOfWidth(minReqWidth);
			else
				return dimensionOfHeight(minReqHeight);
		}
		else if(dim.width < minReqWidth)
		{
			return dimensionOfWidth(minReqWidth);
		}
		else if(dim.height < minReqHeight)
		{
			return dimensionOfHeight(minReqHeight);
		}
		else 
			return dim;
	}
}
