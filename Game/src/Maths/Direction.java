package Maths;

import static java.lang.Math.PI;

import java.awt.geom.Point2D;

public enum Direction 
{
	E(1, 0), NE(1, -1), N(0, -1),
	NW(-1, -1), W(-1, 0), SW(-1, 1),
	S(0, 1), SE(1, 1);
	
	private int x, y;
	
	private Direction(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public boolean contains(Direction dir)
	{
		switch(this)
		{
			case NE: return dir == N || dir == W;
			case NW: return dir == N || dir == W;
			case SE: return dir == S || dir == E;
			case SW: return dir == S || dir == W;
			default: return this == dir;
		}
	}
	public double angle()
	{
		return Math.atan2(y, x);
	}
	
	public boolean isVertical()
	{
		return y != 0;
	}
	
	public boolean isHorizontal()
	{
		return x != 0;
	}
	
	public Direction sum(Direction dir)
	{
		if(dir == null) return this;
		else
			return directionOf(x + dir.x, y + dir.y);
	}
	
	public static Direction getSum(Direction dir1, Direction dir2)
	{
		if(dir1 == null)
			return dir2;
		else
			return dir1.sum(dir2);
			
	}
	
	public Direction difference(Direction dir)
	{
		return directionOf(x - dir.x, y - dir.y);
	}
	
	public Direction directionOf(int x, int y)
	{
		if(x == 1)
		{
			if(y == 1)
				return SE;
			else if(y == 0)
				return E;
			else if(y == -1)
				return NE;
			else return null;
		}
		else if(x == 0)
		{
			if(y == 1)
				return S;
			else if(y == -1)
				return N;
			else return null;
		}
		else if(x == -1)
		{
			if(y == 1)
				return SW;
			else if(y == 0)
				return W;
			else if(y == -1)
				return NW;
			else return null;
		}
		else return null;
	}
	
	public static Direction directionOf(double angle)
	{		
		if(angle < 0)
			return directionOf(angle + 2 * PI);
		else if(angle > 2 * PI)
			return directionOf(angle - 2 * PI);
		else if(angle < PI / 8)
			return E;
		else if(angle < 3 * PI / 8)
			return SE;
		else if(angle < 5 * PI / 8)
			return S;
		else if(angle < 7 * PI / 8)
			return SW;
		else if(angle < 9 * PI / 8)
			return W;
		else if(angle < 11 * PI / 8)
			return NW;
		else if(angle < 13 * PI / 8)
			return N;
		else if(angle < 15 * PI / 8)
			return NE;
		else return E;
	}
	
	public static Direction directionFrom(
			Point2D.Double start, Point2D.Double end)
	{
		double angle = Math.atan2(
				end.y - start.y,
				end.x - start.x);
		
		return directionOf(angle);
	}
}
