package Maths;

public class Circle 
{
	public double x, y, radius;
	
	public Circle()
	{
		x = y = 0;
		radius = 1;
	}
	
	public Circle(double x, double y, double radius)
	{
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public boolean contains(double x, double y)
	{
		double dx = this.x - x,
			   dy = this.y - y;
		
		return dx * dx + dy * dy < radius * radius;
	}
	
	public String toString()
	{
		return "Circle: [" + x + "," + y + "," + radius + "]"; 
	}
	
}
