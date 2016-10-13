package Stat;

public class CoreStat extends Stat
{
	private CoreStatType type;
	
	private BaseStatSupplier supplier;

	public CoreStat(CoreStatType type, 
			BaseStatSupplier supplier)
	{
		super();
		
		this.type = type;
		
		this.supplier = supplier;
		
		baseVal = type.getDefaultValue();
	}
	
	@Override
	protected double initValue() 
	{
		return type.dependentValue(supplier) + baseVal;
	}
	
}
