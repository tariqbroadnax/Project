package Modifiers;

import java.time.Duration;

import Game.Updatable;

public abstract class GradualEffect extends Effect
	implements Updatable
{
	private Span lifespan;

	protected InstantEffect effect;

	public GradualEffect() 
	{
		super();
		
		lifespan = new Span(1000);
	}
	
	public GradualEffect(GradualEffect effect) 
	{
		super(effect);
		
		lifespan = (Span) effect.lifespan.clone();
		
		this.effect = (InstantEffect) effect.effect.clone();
	}
	
	@Override
	public void start() {
		effect.apply();
	}

	@Override
	public void stop() {
		effect.unapply();
	}
	
	@Override
	public void update(Duration delta) {
		lifespan.isFinished();
	}
	
	public boolean isFinished() {
		return lifespan.isFinished();
	}
	
	public void setInstantEffect(InstantEffect effect) {
		this.effect = effect;
	}
	
	public void setLifetimeLength(long len) {
		lifespan.setLength(len);
	}
	
	public InstantEffect getInstantEffect() {
		return effect;
	}
	
	public long getLifespanLength() {
		return lifespan.getLength();
	}
	
}
