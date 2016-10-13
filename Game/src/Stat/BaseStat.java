package Stat;

public class BaseStat extends Stat
{
	private BaseStatType type;
	
	public BaseStat(BaseStatType type)
	{
		super();
		
		this.type = type;
	}

	@Override
	protected double initValue() 
	{
		return baseVal;
	}
	
}
