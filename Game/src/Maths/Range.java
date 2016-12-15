package Maths;

public class Range 
{
	public static class Long extends Range
	{
		private long min, max;
		
		public Long()
		{
			this(0, 100);
		}
		
		public Long(long min, long max)
		{
			this.min = java.lang.Long.MIN_VALUE;
			this.max = java.lang.Long.MAX_VALUE;
			
			setMin(min);
			setMax(max);
		}
		
		public long rand() {
			return (long)(min + Math.random() * (max - min));
		}
		
		public void setMin(long min) 
		{
			if(min > max)
				throw new IllegalArgumentException();
			this.min = min;
		}
		
		public void setMax(long max)
		{
			if(max < min)
				throw new IllegalArgumentException();
			this.max = max;
		}
	}
	
	public static class Double extends Range
	{
		private double min, max;
		
		public Double()
		{
			this(0, 1);
		}
		
		public Double(double min, double max) 
		{
			setMin(min);
			setMax(max);
		}
		
		public boolean contains(double n) {
			return min < n && n < max;
		}
		
		public double rand() {
			return min + Math.random() * (max - min);
		}
		
		public static double rand(double min, double max) 
		{
			return min + Math.random() * (max - min);
		}
		
		public void setMin(double min) 
		{
			if(min > max)
				throw new IllegalArgumentException();
			
			this.min = min;
		}
		
		public void setMax(double max)
		{
			if(max < min)
				throw new IllegalArgumentException();
			
			this.max = max;
		}
	}
}
