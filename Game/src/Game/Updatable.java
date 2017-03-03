package Game;

import java.time.Duration;

public interface Updatable
{
	public void update(Duration delta);
	
	public default void start(){}
	
	public default void stop() {}
}
