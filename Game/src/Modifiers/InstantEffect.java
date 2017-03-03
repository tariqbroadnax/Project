package Modifiers;

public abstract class InstantEffect extends Effect
{
	public InstantEffect() {} 
	
	public InstantEffect(Effect effect) {
		super(effect);
	}

	public abstract void apply();
	
	public void unapply(){}
}
