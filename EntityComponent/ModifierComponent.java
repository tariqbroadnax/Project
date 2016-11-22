package EntityComponent;

import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

import Game.Entity;
import Modifiers.Modifier;

public class ModifierComponent extends EntityComponent
{	
	private Collection<Modifier> modsToDeliver,
								 recievedMods;
		
	public ModifierComponent()
	{
		super();
		
		modsToDeliver = new LinkedList<Modifier>();
		recievedMods = new LinkedList<Modifier>();		
	}
	
	public ModifierComponent(ModifierComponent comp)
	{
		this();
		
		for(Modifier modToDeliver : comp.modsToDeliver)
			modsToDeliver.add((Modifier)modToDeliver.clone());
	}
	
	public void applyModifiers(Entity e)
	{
		for(Modifier mod : modsToDeliver)
		{
			Modifier clone = (Modifier)mod.clone();
			
			clone.setSource(parent);
			
			e.get(ModifierComponent.class)
			 .recieveModifier(clone);
		}
	}

	public void recieveModifier(Modifier mod)
	{
		mod.setTarget(parent);
		recievedMods.add(mod);
	}
	
	public void removeRecievedModifier(Modifier mod)
	{
		if(recievedMods.contains(mod))
		{
			mod.revert();
			recievedMods.remove(mod);
		}
	}
	
	public void addModifierToDeliver(Modifier... mods)
	{
		for(Modifier mod : mods)
			modsToDeliver.add(mod);
	}
	
	public void removeModifierToDeliver(Modifier mod)
	{
		modsToDeliver.remove(mod);
	}
	
	public String toString()
	{
		String str = super.toString();
		
		str += "\nmods to deliver: ";
		for(Modifier mod : modsToDeliver)
			str += '\n' + mod.toString();
		
		str += "\nrecieved mods: ";
		for(Modifier mod : recievedMods)
			str += '\n' + mod.toString();
		
		return str;
	}
	
	public Collection<Modifier> getModifiersToDeliver()
	{
		return modsToDeliver;
	}
	
	@Override
	public void update(Duration delta)
	{
		for(Modifier mod : recievedMods)
		{
			mod.update(delta);
			
			if(mod.isOver())
				mod.revert();
		}
		
		recievedMods.removeIf(mod -> mod.isOver());
	}

	@Override
	protected EntityComponent _clone()
	{
		return new ModifierComponent(this);
	}
}
