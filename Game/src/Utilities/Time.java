package Utilities;

import java.time.Duration;
import java.util.Date;

public class Time
{
	private Time(){}
	
	public static Duration between(Date start, Date end)
	{
		return Duration.between(start.toInstant(), end.toInstant());
	}
	
	public static Duration periodOf(long frequency)
	{
		return Duration.ofMillis(1000 / frequency);
	}
	
	public static boolean greaterOrEqual(Duration dur1, Duration dur2)
	{
		return dur1.compareTo(dur2) >= 0;
	}
}
