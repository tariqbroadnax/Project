package Stat;

public enum BaseStatType 
{
	STR(1), DEX(1), INT(1), LUK(1);
	
	private int defaultVal;
	
	BaseStatType(int defaultVal)
	{
		this.defaultVal = defaultVal;
	}
	
	public int getDefaultValue()
	{
		return defaultVal;
	}
}
