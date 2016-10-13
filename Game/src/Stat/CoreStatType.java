package Stat;

import static Stat.BaseStatType.*;

public enum CoreStatType 
{
	HP(100), MP(100),
	HP_REGEN(1), MP_REGEN(1),
	ATK_SPEED(1), SPEED(50),
	PHYS_ATK(1), MA_ATK(1),
	CRIT(5);
	
	private int defaultVal;
	
	CoreStatType(int defaultVal)
	{
		this.defaultVal = defaultVal;
	}
	
	public int getDefaultValue()
	{
		return defaultVal;
	}
	
	public double dependentValue(
			BaseStatSupplier supplier)
	{
		return 0;
	}
}
