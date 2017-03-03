package Modifiers;

public class MultModifier extends Modifier
{	
	public MultModifier()
	{
		super(1);
	}
	
	public MultModifier(double factor)
	{
		super(factor);
	}
	
	public MultModifier(MultModifier mod)
	{
		super(mod);
	}
	
	@Override
	public double modify(double val) {
		return factor * val;
	}

	@Override
	public Object clone() {
		return new MultModifier(this);
	}
}
