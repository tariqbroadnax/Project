package Modifiers;

public class AddModifier extends Modifier
{
	public AddModifier()
	{
		super(0);
	}
	
	public AddModifier(double factor)
	{
		super(factor);
	}
	
	public AddModifier(AddModifier mod)
	{
		super(mod);
	}
	
	@Override
	public double modify(double val) {
		return val + factor;
	}

	@Override
	public Object clone() {
		return new AddModifier(this);
	}
}
