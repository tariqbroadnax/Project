package Maths;

public class Range
{
	public static class Double
	{
		private double left, right;
		
		private boolean leftIncl, rightIncl;
		
		public Double(double left, double right)
		{
			this.left = left;
			this.right = right;
			
			leftIncl = rightIncl = false;
		}
		
		public boolean contains(double num)
		{
			if(leftIncl && rightIncl)
				return left <= num && num <= right;
			else if(leftIncl)
				return left <= num && num < right;
			else
				return left < num && num < right;
		}
	}
}
