package Modifiers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import Entity.Entity;
import EntityComponent.CombatComponent;
import EntityComponent.CombatListener;
import EntityComponent.EffectComponent;

public class Bleed extends Effect 
	implements CombatListener
{
	private double rate;
	
	private Damage dot;

	private List<Damage> dots;
	
	public Bleed()
	{
		rate = .15;
		
		dot = new Damage();
		
		dots = new ArrayList<Damage>();
	}
	
	public Bleed(Bleed bleed)
	{
		rate = bleed.rate;
		
		dot = (Damage) dot.clone();
	
		dots = new ArrayList<Damage>();
	}
	
	@Override
	public void update(Duration delta) 
	{
		dots.removeIf(d -> d.isFinished());
	}
	
	public void start()
	{
		CombatComponent comp = target.get(CombatComponent.class);
		
		comp.addCombatListener(this);
	}
	
	public void stop()
	{
		CombatComponent comp = target.get(CombatComponent.class);
		
		comp.removeCombatListener(this);
	}
	
	@Override
	public void entityAttacked(Entity ent, double damage)
	{
		if(Math.random() < rate)
		{
			EffectComponent comp = ent.get(EffectComponent.class);
			
			boolean hasDot = false;
			for(Damage dot : dots)
			{
				if(comp.contains(dot))
				{
					hasDot = true;
					dot.reset();
				}
			}
			
			if(!hasDot)
			{
				Damage dot = (Damage) this.dot.clone();
				
				comp.add(dot);
				dots.add(dot);
			}
		}
	}
	
	public void setDot(Damage dot) {
		this.dot = dot;
	}
	
	public Damage getDot() {
		return dot;
	}

	@Override
	protected Effect _clone() {
		return new Bleed(this);
	}
}