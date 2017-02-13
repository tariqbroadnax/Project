package Ability;

public class TargetAbility extends ActiveAbility
{
	protected double range;
	
	public TargetAbility()
	{
		super();
		
		range = 500;
		
		indicator = new CircleRangeIndicator(range);
	}
	
	public TargetAbility(TargetAbility ability)
	{
		super(ability);
		
		range = ability.range;
	
		indicator = new CircleRangeIndicator(range);
	}
	
	public void setRange(double range) {
		this.range = range;
	}
	
	public double getRange() {
		return range;
	}

	@Override
	public Object clone() {
		return new TargetAbility(this);
	}
}
