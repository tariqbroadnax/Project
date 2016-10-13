package Modifiers;

import EntityComponent.AbilityComponent;

public class Silence extends Modifier
{
	public Silence()
	{
		super();
	}
	
	public Silence(Silence mod)
	{
		super(mod);
	}
	
	@Override
	protected void apply()
	{
		target.get(AbilityComponent.class)
		  	  .setAllActivesEnabled(false);
	}
	
	@Override
	public void revert()
	{
		target.get(AbilityComponent.class)
		  	  .setAllActivesEnabled(true);
	}

	@Override
	protected Object _clone()
	{
		return new Silence(this);
	}

}
