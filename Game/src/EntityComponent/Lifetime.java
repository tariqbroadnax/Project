package EntityComponent;

import java.io.Serializable;
import java.time.Duration;

import Game.Updatable;

public class Lifetime 
implements Updatable
{
	public static final Duration INFINITE = null;
	
	private Duration len,
					 elapsed;
	
	public Lifetime()
	{
		len = INFINITE;
		elapsed = Duration.ZERO;
	}
	
	public Lifetime(Lifetime lifetime)
	{
		len = lifetime.len;
		elapsed = Duration.ZERO;
	}
	
	public void update(Duration delta)
	{
		elapsed = elapsed.plus(delta);
	}
	
	public boolean isLifeOver()
	{
		if(len == INFINITE)
			return false;
		else
			return elapsed.compareTo(len) >=  0;
	}
	
	public void setLength(Duration len)
	{
		this.len = len;
	}
	
	public Duration getLength()
	{
		return len;
	}
	
	public String toString()
	{
		return super.toString() + 
				" len: " + (len == INFINITE ? "INFINITE" : len.toMillis()) +
				" elapsed: " + elapsed.toMillis();
	}
}
