package Modifiers;

import java.time.Duration;

import Entity.Entity;
import Entity.Fire;
import EntityComponent.LifetimeComponent;

public class WaterEffect extends Effect
{	
	@Override
	public void update(Duration delta) {}

	@Override
	public void start() {
		target.get(LifetimeComponent.class)
			  .getLifetime().end();
	}

	@Override
	public void stop() {}

	@Override
	public boolean canBeApplied(Entity target) {
		return target instanceof Fire;
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	protected Effect _clone() {
		return new WaterEffect();
	}

}
