package Ability;

public abstract class FriendlyUnitAbility extends TargetUnitAbility
{
	public FriendlyUnitAbility() {}
	
	public FriendlyUnitAbility(FriendlyUnitAbility ability) {
		super(ability);
	}
	
	public boolean canBeCast() {
		return super.canBeCast() && target == src;
	}
}
