package Ability;

import java.time.Duration;
import java.util.EnumSet;

import EntityComponent.EffectComponent;
import Modifiers.Effect;

public class StackAbility extends PassiveAbility
	implements AbilityListener
{
	private int stacks,
				maxStacks;
	
	private long falloffTime,
				 elapsed;
	
	private EnumSet<Event> events;
	
	public StackAbility()
	{
		stacks = 0;
		maxStacks = 3;
		
		falloffTime = 500;
		elapsed = 0;
	
		events = EnumSet.noneOf(Event.class);
	}
	
	public void update(Duration delta)
	{
		if(stacks == 0)
			return;
		
		elapsed += delta.toMillis();
		
		if(elapsed > falloffTime)
		{
			elapsed -= falloffTime;
			removeStack();
		}
	}
	
	public void addStack()
	{
		if(stacks < maxStacks)
		{
			stacks++;
			
			EffectComponent comp = 
					src.get(EffectComponent.class);
			
			for(Effect eff : effects)
				comp.add(eff);
		}
	}
	
	public void removeStack()
	{
		stacks--;
		
		if(stacks == 0)
			elapsed = 0;
	}
	
	public void setFalloffTime(long falloffTime) {
		this.falloffTime = falloffTime;
	}
	
	public void setMaxStacks(int maxStacks) {
		this.maxStacks = maxStacks;
	}

	@Override
	public void abilityCasted(ActiveAbility ability) 
	{
		if(events.contains(Event.CASTING))
			addStack();
	}
}
