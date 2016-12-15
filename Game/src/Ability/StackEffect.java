package Ability;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import Entity.Entity;
import Modifiers.Effect;

public class StackEffect extends Effect
{
	private int stacks,
				targetStacks;
	
	private long falloffTime,
				 elapsed;
	
	private List<Effect> effects;
	
	public StackEffect()
	{
		stacks = 1;
		targetStacks = 3;
		falloffTime = 500;
		elapsed = 0;
		effects = new ArrayList<Effect>();
	}
	
	public StackEffect(StackEffect effect)
	{
		stacks = 0;
		targetStacks = effect.targetStacks;
		falloffTime = effect.falloffTime;
		elapsed = 0;
		effects = new ArrayList<Effect>();

		for(Effect eff : effect.effects)
			this.effects.add((Effect)eff.clone());
		
	}

	@Override
	public void update(Duration delta) {
		elapsed += delta.toMillis();
		
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFinished() {
		return stacks == 0;
	}
	
	public void add(Effect eff) {
		effects.add(eff);
	}
	
	public void remove(Effect eff){
		effects.remove(eff);
	}

	@Override
	protected Effect _clone() {
		return new StackEffect(this);
	}

	@Override
	public boolean canBeApplied(Entity target) {
		// TODO Auto-generated method stub
		return false;
	}
}
