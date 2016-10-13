package Movement;

public interface MovementListener 
{
	default public void movementStarted(
			MovementComponent src){}
	
	default public void movementStopped(
			MovementComponent src){}
	
	default public void directionChanged(
			MovementComponent src){}
}
