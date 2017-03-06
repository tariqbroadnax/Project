package Ability;

public interface AbilityListener 
{
	public default void castingStarted(
			ActiveAbility ability){}
	
	public default void castingStopped(
			ActiveAbility ability){}
	
	public default void basicAttackStarted() {}
	
	public default void basicAttackStopped() {}
	
	public default void basicAttackWaiting() {}
}
