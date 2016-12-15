package Modifiers;

public abstract class Modifier
	implements Comparable<Modifier>
{
	protected double factor;
	
	public Modifier(double factor) 
	{
		this.factor = factor;
	}
	
	public Modifier(Modifier mod) 
	{
		factor = mod.factor;
	}
	
	public abstract double modify(double val);
	
	public int compareTo(Modifier mod)
	{
		if(this instanceof AddModifier)
		{
			if(mod instanceof MultModifier)
				return -1;
		}
		else if(this instanceof MultModifier)
		{
			if(mod instanceof AddModifier)
				return 1;
		}
		
		return 0;
	}
	
	public void setFactor(double factor) {
		this.factor = factor;
	}
	
	public double getFactor() {
		return factor;
	}
}
