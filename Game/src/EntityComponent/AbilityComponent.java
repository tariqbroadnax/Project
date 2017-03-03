package EntityComponent;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import Ability.AbilityListener;
import Ability.ActiveAbility;
import Ability.CastingIndicator;
import Ability.PassiveAbility;
import Ability.PointAbility;
import Entity.Entity;

public class AbilityComponent extends EntityComponent
{
	private ArrayList<PassiveAbility> passives;
	private ArrayList<ActiveAbility> actives;
	private ArrayList<PointAbility> points;
	
	private ArrayList<AbilityListener> lists;
		
	private ActiveAbility castingAbility;
	
	private Root castRoot;
	
	private CastingIndicator indicator;
	
	private List<Class<? extends ActiveAbility>> disabledAbilities;
	
	public AbilityComponent()
	{
		super();
		
		passives = new ArrayList<PassiveAbility>();
		actives = new ArrayList<ActiveAbility>();
		points = new ArrayList<PointAbility>();
		
		lists = new ArrayList<AbilityListener>();
		
		castingAbility = null;
		
		indicator = new CastingIndicator();
		
		disabledAbilities = new ArrayList<Class<? extends ActiveAbility>>();
	}
	
	public AbilityComponent(AbilityComponent comp)
	{
		this();
		
		for(ActiveAbility active : comp.actives)
			actives.add((ActiveAbility)active.clone());
	}
	
	@Override
	public void update(Duration delta)
	{				
		if(castingAbility != null)
		{
			if(!castingAbility.isCasting())
			{
				castingAbility = null;
			
				if(parent.contains(GraphicsComponent.class))
					parent.get(GraphicsComponent.class)
						  .getDecorations()
						  .remove(indicator);
			}
		}
		
		
		for(ActiveAbility ability : actives)
			ability.update(delta);
		for(ActiveAbility ability : points)
			ability.update(delta);
	}
	
	public void castActiveAbility(int i) 
	{
		ActiveAbility ability = actives.get(i);
		
		tryAndCast(ability);
	}
	
	private void tryAndCast(ActiveAbility ability)
	{
		if(castingAbility == null && ability.canBeCast())
		{
			castingAbility = ability;
			
			ability.cast();
			
			if(parent.contains(GraphicsComponent.class))
			{
				parent.get(GraphicsComponent.class)
					  .getDecorations()
					  .add(indicator, 0, -10);
				
				indicator.setActiveAbility(ability);	
			}
		}
	}
	
	public void castPointAbility(int i, Point2D.Double loc)
	{
		PointAbility ability = points.get(i);
		
		ability.setTarget(loc);
		
		tryAndCast(ability);
	}
	
	public void addPassiveAbility(PassiveAbility ability) {
		passives.add(ability);
		ability.setSrc(parent);
	}
	
	public void removePassiveAbility(PassiveAbility ability) {
		passives.remove(ability);
		ability.setSrc(null);
	}

	public void addActiveAbility(ActiveAbility ability) {
		actives.add(ability);
		ability.setSrc(parent);
	}
	
	public void removeActiveAbility(ActiveAbility ability) {
		actives.remove(ability);
		ability.setSrc(null);
	}
	
	public void addPointAbility(PointAbility ability) {
		points.add(ability);
		ability.setSrc(parent);
	}
	
	public void removePointAbility(PointAbility ability) {
		points.remove(ability);
		ability.setSrc(null);
	}
	
	private void addCastRoot()
	{
		parent.get(EffectComponent.class)
		  	  .add(castRoot);
	}
	
	private void addCastingIndicator(
			ActiveAbility ability)
	{
		indicator.setActiveAbility(ability);
		
		parent.get(GraphicsComponent.class)
			  .getDecorations()
			  .add(indicator, 0, -15);
	}
		
	private void removeCastRoot()
	{
		parent.get(EffectComponent.class)
			  .remove(castRoot);
	}
	
	private void removeCastingIndicator()
	{
		parent.get(GraphicsComponent.class)
			  .getDecorations()
			  .remove(indicator);
	}
	
	public boolean isCasting() {
		return castingAbility != null;
	}

	public void addAbilityListener(AbilityListener list) {
		lists.add(list);
	}

	public void removeAbilityListener(AbilityListener list) {
		lists.remove(list);
	}

	public void setParent(Entity parent)
	{
		super.setParent(parent);
		
		for(ActiveAbility a : actives)
			a.setSrc(parent);
		
		//castRoot.setTarget(parent);
	}
	
	public void notifyEntityKilled(Entity ent) 
	{
		List<AbilityListener> copy = 
				new ArrayList<AbilityListener>(lists);
	
		for(AbilityListener list : copy)
			list.entityKilled(ent);
	}

	private void readObject(java.io.ObjectInputStream in)
		     throws IOException, ClassNotFoundException
     {
		in.defaultReadObject();
						
		castRoot = new Root();		
     }
	 
	@Override
	protected EntityComponent _clone() {
		return new AbilityComponent(this);
	}
		
	public String toString()
	{
		String str = super.toString();
		
		str += "\nActives: ";
		for(ActiveAbility active : actives)
			str += '\n' + active.toString();
		
		return str;
	}

	public void addDisabledAbility(Class<? extends ActiveAbility> c) {
		// TODO Auto-generated method stub
		
	}

	public void removeDisabledAbility(Class<? extends ActiveAbility> c) {
		// TODO Auto-generated method stub
		
	}
}
