package Editor;

public interface SceneListener 
{
	public default void sceneChanged(){};
	
	public default void sceneLoaded(){};
	
	public default void sceneSaved(){};
	
	public default void scenePlayerStarted(){}
	
	public default void scenePlayerStopped(){}
}
