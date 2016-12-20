package Editor;

public interface SceneListener 
{
	public default void sceneChanged(){};
	
	public default void sceneLoaded(){};
}
