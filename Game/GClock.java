package Game;

import java.time.Duration;

public class GClock implements Updatable
{
	private int hour, min;

	private long elapsed;
	
	public GClock()
	{
		elapsed = hour = min = 0;
	}
		
	@Override
	public void update(Duration delta) 
	{
		elapsed += delta.toMillis();
		
		if(elapsed > 1000)
		{
			min++;
			elapsed = 0;
			
			if(min > 60)
			{
				min = 0;
				hour++;
				
				if(hour > 23)
					hour = 0;
			}
			
			// printTime();
		}
	}
	
	private void printTime()
	{
		System.out.printf("GClock - %02d:%02d\n", hour, min);
	}
	
	public int getHour()
	{
		return hour;
	}
	
	public int getMin()
	{
		return min;
	}
}
