package Stat;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import Modifiers.StatModifier;

public abstract class Stat 
implements Serializable
{		
	private Collection<StatModifier> modifiers;
	
	protected int baseVal;
	
	public Stat()
	{				
		modifiers = new LinkedList<StatModifier>();
	}
	
	public void addModifier(StatModifier modifier)
	{
		modifiers.add(modifier);
	}
	
	public void removeModifier(StatModifier modifier)
	{
		modifiers.remove(modifier);
	}
	
	public void setBaseValue(int baseVal)
	{
		this.baseVal = baseVal;
	}
	
	public int getBaseValue()
	{
		return baseVal;
	}
	
	public int getValue()
	{
		return modifiedValue(initValue());
	}
	
	protected abstract double initValue();
	
	protected int modifiedValue(double val)
	{
		double modifiedVal = val;
		
		for(StatModifier modifier : modifiers)
			modifiedVal = 
				modifier.modify(modifiedVal);
		
		return (int)modifiedVal;
	}
}
