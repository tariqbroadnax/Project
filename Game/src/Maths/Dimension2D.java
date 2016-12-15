package Maths;

import java.awt.Dimension;
import java.io.Serializable;

public abstract class Dimension2D extends java.awt.geom.Dimension2D
	implements Serializable
{
	public static class Double extends Dimension2D
	{
		public double width, height;
		
		public Double()
		{
			width = height = 0;
		}
		
		public Double(double width, double height)
		{
			this.width = width;
			this.height = height;
		}
		@Override
		public double getHeight() {
			return height;
		}

		@Override
		public double getWidth() {
			return width;
		}

		@Override
		public void setSize(double width, double height) {
			this.width = width;
			this.height = height;
		}
		
		public String toString()
		{
			return "width: " + width + " height: " + height;
		}
		
		public Dimension ceil()
		{
			return new Dimension(
					(int)Math.ceil(width),
					(int)Math.ceil(height));
		}
		
	}
	
}
