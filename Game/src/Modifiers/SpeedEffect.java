package Modifiers;

import java.time.Duration;

import Entity.Entity;
import EntityComponent.Lifetime;
import Movement.MovementComponent;

public class SpeedEffect extends Effect
{
	private AddModifier addMod;
	private MultModifier multMod;
	
	private Lifetime lifetime;
	
	public SpeedEffect()
	{
		addMod = new AddModifier();
		multMod = new MultModifier();
		lifetime = new Lifetime(500);
	}
	
	public SpeedEffect(double addFactor, double multFactor,
				 long lifetime)
	{
		this();
				
		setAddFactor(addFactor);
		setMultFactor(multFactor);
		setLifetime(lifetime);
	}
	
	public SpeedEffect(SpeedEffect effect)
	{
		addMod = new AddModifier(effect.addMod);
		multMod = new MultModifier(effect.multMod);
		lifetime = new Lifetime(effect.lifetime);
	}
	
	@Override
	public void update(Duration delta) {
		lifetime.update(delta);
	}

	@Override
	public void start() 
	{	
		MovementComponent comp = 
				target.get(MovementComponent.class);
		
		comp.addSpeedMod(addMod);
		comp.addSpeedMod(multMod);
	}

	@Override
	public void stop() 
	{
		MovementComponent comp = 
				target.get(MovementComponent.class);
		
		comp.removeSpeedMod(addMod);
		comp.removeSpeedMod(multMod);
	}
	
	public void setAddFactor(double factor)
	{
		addMod.setFactor(factor);
	}
	
	public void setMultFactor(double factor)
	{
		multMod.setFactor(factor);
	}
	
	public void setLifetime(long lifetime) {
		this.lifetime.setRemaining(lifetime);
	}

	@Override
	public boolean isFinished() {
		return lifetime.isLifeOver();
	}

	@Override
	protected Effect _clone() {
		return new SpeedEffect(this);
	}

	@Override
	public boolean canBeApplied(Entity target) {
		// TODO Auto-generated method stub
		return false;
	}
}
