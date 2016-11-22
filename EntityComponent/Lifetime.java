package EntityComponent;

import java.io.Serializable;
import java.time.Duration;

import Game.Updatable;

public class Lifetime 
implements Updatable
{
	public static final long FOREVER = -1;
	
	private long remaining, elapsed;
	
	public Lifetime()
	{
		this(FOREVER);
	}
	
	public Lifetime(Lifetime lifetime)
	{
		this(lifetime.remaining);
	}
	
	public Lifetime(long length)
	{
		remaining = length;
		elapsed = 0;
	}
	
	public void update(Duration delta)
	{
		elapsed += delta.toMillis();
	}
	
	public boolean isLifeOver()
	{
		if(remaining == FOREVER)
			return false;
		else
			return elapsed > 0;
	}
	
	public void end() {
		remaining = 0;
	}
	
	public void setRemaining(long remaining)
	{
		if(remaining < -1)
			throw new IllegalArgumentException();
		
		this.remaining = remaining;
	}
	
	public long getRemaining()
	{
		return remaining;
	}
}
