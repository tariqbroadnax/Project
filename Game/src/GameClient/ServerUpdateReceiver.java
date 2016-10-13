package GameClient;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import GameServer.ServerUpdate;
import Utilities.TimeUtilities;

public class ServerUpdateReceiver 
	implements Runnable
{
	private boolean receiving;
	
	private Thread thread;
	
	private ServerConnection connection;
	
	private Collection<ServerUpdateListener> listeners;
	
	private long netLatency, latencyCount;
	
	private Date lastLatencyUpdate;
	
	private double latency;

	public ServerUpdateReceiver(ServerConnection connection)
	{
		this.connection = connection;
		
		thread = new Thread(this);
		
		receiving = false;
		
		listeners = new LinkedList<ServerUpdateListener>();

		lastLatencyUpdate = new Date();
		
		latency = netLatency = latencyCount = 0;
	}
	
	public void run()
	{		
		while(receiving)
		{
			try {
				ServerUpdate update = 
				(ServerUpdate)
						connection.getObjectInputStream()
								  .readObject();
				
				for(ServerUpdateListener listener : listeners)
					listener.updateReceived(update);
				
				maintainLatency(update.getCreationDate());
				
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void maintainLatency(Date creationDate)
	{
		Date now = new Date();
		
		netLatency += TimeUtilities.between(creationDate, now)
								   .toMillis();
		latencyCount++;
		
		Duration durSinceLastUpdate = 
				TimeUtilities.between(lastLatencyUpdate, now);
		
		if(durSinceLastUpdate.compareTo(Duration.ofSeconds(1)) > 1)
		{
			latency = netLatency * 1.0 / latencyCount;
			netLatency = 0;
			latencyCount = 0;
			lastLatencyUpdate = now;
		}
	}
	
	public void start()
	{
		receiving = true;
		
		thread.start();
	}
	
	public void stop()
	{
		receiving = false;
	}
	
	public void addServerUpdateListener(
			ServerUpdateListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeServerUpdateListener(
			ServerUpdateListener listener)
	{
		listeners.remove(listener);
	}
	
	public boolean isReceiving()
	{
		return receiving;
	}
	
	public double getLatency()
	{
		return latency;
	}
}
