package Stat;

import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;

import EntityComponent.Attacker;
import Game.Entity;
import Modifiers.Damage;
import Modifiers.StatModifier;

public class Stats 
	implements BaseStatSupplier, Serializable
{	
	/* TODO
	 * ---------
	 * 1. add combat text
	 * 2. handle no health
	 * 3. handle giving exp
	 */
	
	private transient HashSet<Attacker> attackers;
	
	private BaseStat[] baseStats;
	private CoreStat[] coreStats;
	
	public Stats()
	{
		baseStats = 
				new BaseStat[BaseStatType.values().length];
			
		coreStats =
				new CoreStat[CoreStatType.values().length];
		
		initStats();
		
		attackers = new HashSet<Attacker>();
	}
	
	public Stats(Stats stats)
	{
		this();
		
		for(int i = 0; i < baseStats.length; i++)
			baseStats[i].setBaseValue(
					stats.baseStats[i].getBaseValue());
	
		for(int i = 0; i < coreStats.length; i++)
			coreStats[i].setBaseValue(
					stats.coreStats[i].getBaseValue());
	}
	
	private void initStats()
	{
		for(BaseStatType type : BaseStatType.values())
			baseStats[type.ordinal()] = 
				new BaseStat(type);
		
		for(CoreStatType type : CoreStatType.values())
			coreStats[type.ordinal()] =
				new CoreStat(type, this);
	}
	
	public void recieveDamage(int amount, Entity src)
	{
		
	}
	
	public void update(Duration delta)
	{
		for(Attacker attacker : attackers)
			attacker.update(delta);
		
		attackers.removeIf(a -> 
			a.getElapsedSinceAttack().compareTo(
			Duration.ofSeconds(3)) > 0);
	}
	
	public void applyDamage(Damage damage, Entity target)
	{
		
	}
	
	public void addBaseStatModifier(BaseStatType type, 
			StatModifier modifier)
	{
		baseStats[type.ordinal()].addModifier(modifier);
	}
	
	public void removeBaseStatModifier(BaseStatType type,
			StatModifier modifier)
	{
		baseStats[type.ordinal()].removeModifier(modifier);
	}
	
	public void addCoreStatModifier(CoreStatType type,
			StatModifier modifier)
	{
		coreStats[type.ordinal()].addModifier(modifier);
	}
	
	public void removeCoreStatModifier(CoreStatType type,
			StatModifier modifier)
	{
		coreStats[type.ordinal()].removeModifier(modifier);
	}
	
	public void setBaseStatValue(BaseStatType type, int val)
	{
		baseStats[type.ordinal()].setBaseValue(val);
	}
	
	public void setCoreStatValue(CoreStatType type, int val)
	{
		coreStats[type.ordinal()].setBaseValue(val);
	}
	
	public int getValue(BaseStatType type)
	{
		return baseStats[type.ordinal()].getValue();
	}
	
	public int getValue(CoreStatType type)
	{
		return coreStats[type.ordinal()].getValue();
	}
	
	public Collection<Attacker> getAttackers()
	{
		return attackers;
	}
	
	public String toString()
	{
		String str = super.toString();
		
		for(Stat stat : baseStats)
			str += "\n" + stat.toString();
		
		for(Stat stat : coreStats)
			str += "\n" + stat.toString();
		
		return str;
	}
}
