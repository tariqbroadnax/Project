package Game;

import static Utilities.MathUtilities.flat;
import static Utilities.TimeUtilities.periodOf;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class Updater implements Runnable
{	
	public final static long FREQUENCY_UNBOUNDED = -1;
	
	private final static int GREATER = 1,
					  		 EQUAL = 0,
					  		 LESS = -1;
	
	private boolean running = false;
	
	private Collection<Updatable> updatables;
	
	private long targetFrequency, actualFrequency, updates;
	
	private Duration targetPeriod,
					 delta,
					 sleep, makeUpSleep;
	
	private long start, end,
				 lastUpdate,
				 lastMaintanance;

	private Thread thread;
	
	public Updater()
	{
		this(FREQUENCY_UNBOUNDED);
	}
	
	public Updater(long targetFrequency)
	{
		updatables = new LinkedList<Updatable>();
		
		thread = new Thread(this);
				
		setTargetFrequency(targetFrequency);
	}

	public void start()
	{
		if(running || thread.isAlive()) return;
		
		running = true;
		
		lastUpdate = lastMaintanance = System.nanoTime();
		makeUpSleep = Duration.ZERO;
		
		thread.start();
	}
	
	public void stop()
	{
		running = false;
	}

	@Override
	public void run()
	{
		while(running)
		{
			start = System.nanoTime();
			
			long s = System.nanoTime();
			updateAll();
			
			end = System.nanoTime();
			
			delta = Duration.ofNanos(end - start);	
			
			findSleep();
			sleep();
			maintainActualFrequency();
		}
	}
	
	private void updateAll()
	{
		long now = System.nanoTime();
		
		Duration delta = 
				Duration.ofNanos(now - lastUpdate);
				
		for(Updatable u : updatables)
			u.update(delta);
		
		lastUpdate = now;
	}
	
	private void findSleep()
	{	
		switch(flat(targetPeriod.compareTo(delta)))
		{
			case GREATER:
				sleep = targetPeriod.minus(delta);
				
				considerMakeUpSleep();
				break;
			
			case EQUAL:
				sleep = Duration.ZERO;
				break;
				
			case LESS:
				sleep = Duration.ZERO;
				makeUpSleep = 
						makeUpSleep.plus(delta.minus(targetPeriod));
				break;
		}		
	}
	
	private void considerMakeUpSleep()
	{
		switch(flat(makeUpSleep.compareTo(sleep)))
		{
			case GREATER:
				makeUpSleep = makeUpSleep.minus(sleep);
				sleep = Duration.ZERO;
				break;
			
			case LESS:
				sleep = sleep.minus(makeUpSleep);
				makeUpSleep = Duration.ZERO;
				break;
			
			case EQUAL:
				sleep = makeUpSleep = Duration.ZERO;
				break;
		}	
	}
	
	private void sleep()
	{
		try {
			if(sleep.equals(Duration.ZERO)) return;
		
			TimeUnit.NANOSECONDS.sleep(sleep.toNanos());
		} catch (InterruptedException e) {} // should not happen
	}
	
	private void maintainActualFrequency()
	{
		long now = System.nanoTime();
		updates++;
		Duration delta = 
				Duration.ofNanos(now - lastMaintanance);
	
		if(flat(delta.compareTo(Duration.ofSeconds(1))) == GREATER)
		{
			lastMaintanance = now;
			makeUpSleep = Duration.ZERO;
			actualFrequency = updates;
			updates = 0;
			
			//System.out.println(actualFrequency);
		}
	}
	
	public void addUpdatable(Updatable... us)
	{
		for(Updatable u : us)
			updatables.add(u);
	}
	
	public void removeUpdatable(Updatable u)
	{
		updatables.remove(u);
	}
	
	public void setTargetFrequency(long frequency)
	{
		this.targetFrequency = 
				frequency < -1 ? 1 : frequency;
		
		targetPeriod = 
				frequency == FREQUENCY_UNBOUNDED ?
					Duration.ZERO : periodOf(targetFrequency);		
	}
	
	public double getTargetFrequency()
	{
		return targetFrequency;
	}
	
	public double getActualFrequency()
	{
		return actualFrequency;
	}
}
