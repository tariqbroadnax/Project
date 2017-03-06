package Movement;

import static java.lang.Math.PI;

public enum Cardinal 
{
	NORTH, SOUTH, EAST, WEST;
	
	public static Cardinal angleToCardinal(
			double angle)
	{
		while(angle < 0)
			angle += Math.PI * 2;
		
		if(angle < PI / 4)
			return EAST;
		else if(angle < 3 * PI / 4)
			return SOUTH;
		else if(angle < 5 * PI / 4)
			return WEST;
		else if(angle < 7 * PI / 4)
			return NORTH;
		else
			return EAST;
	}
}
