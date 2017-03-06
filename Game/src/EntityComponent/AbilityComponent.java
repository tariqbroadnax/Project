package EntityComponent;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import Ability.AbilityListener;
import Ability.ActiveAbility;
import Ability.BasicAttack;
import Ability.CastingIndicator;
import Ability.InstantFriendlyUnitAbility;
import Ability.PassiveAbility;
import Ability.PointAbility;
import Ability.TargetUnitAbility;
import Entity.Entity;
import Movement.MovementComponent;
import Movement.MovementListener;

public class AbilityComponent extends EntityComponent
	implements CombatListener, MovementListener
{
	private List<PassiveAbility> passives;
	private List<ActiveAbility> actives;
	private List<PointAbility> points;
	private List<TargetUnitAbility> targets;
	
	private List<AbilityListener> lists;
	
	private List<Class<? extends ActiveAbility>> disabledAbilities;
	
	private ActiveAbility castingAbility;
		
	private CastingIndicator indicator;
	
	private boolean castInterruptedOnAttack;
	
	public AbilityComponent()
	{
		super();
		
		passives = new ArrayList<PassiveAbility>();
		actives = new ArrayList<ActiveAbility>();
		points = new ArrayList<PointAbility>();
		targets = new ArrayList<TargetUnitAbility>();
		
		lists = new ArrayList<AbilityListener>();
		
		castingAbility = null;
		
		indicator = new CastingIndicator();
		
		disabledAbilities = new ArrayList<Class<? extends ActiveAbility>>();
	
		castInterruptedOnAttack = true;
		
		addTargetUnitAbility(new BasicAttack());
		addTargetUnitAbility(new InstantFriendlyUnitAbility());
	}
	
	public AbilityComponent(AbilityComponent comp)
	{
		this();
		
		for(ActiveAbility active : comp.actives)
			actives.add((ActiveAbility)active.clone());
	}
	
	@Override
	public void start()
	{
//		parent.get(CombatComponent.class)
//			  .addCombatListener(this);
		
		parent.get(MovementComponent.class)
			  .addMovementListener(this);
		
		for(ActiveAbility ability : actives)
			ability.start();
		for(ActiveAbility ability : points)
			ability.start();
		for(ActiveAbility ability : targets)
			ability.start();
	}
	
	@Override
	public void stop()
	{
		parent.get(CombatComponent.class)
			  .addCombatListener(this);
		
//		parent.get(MovementComponent.class)
//		  	  .removeMovementListener(this);
	}
	
	@Override
	public void entityAttacked(Entity ent, double damage){}
	{
		tryAndStopCasting();
	}
	
	@Override
	public void movementContinued(MovementComponent src)
	{
		tryAndStopCasting();
	}
	
	private void tryAndStopCasting()
	{
		if(castInterruptedOnAttack && castingAbility != null)
		{
			castingAbility.stopCast();
			
			if(parent.contains(GraphicsComponent.class))
				parent.get(GraphicsComponent.class)
					  .getDecorations()
					  .remove(indicator);
			
			castingAbility = null;
		}
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
		for(ActiveAbility ability : targets)
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
	
	public void castTargetUnitAbility(int i, Entity target)
	{
		TargetUnitAbility ability = targets.get(i);
		
		ability.setTarget(target);
		
		tryAndCast(ability);
	}
	
	public void setCastInterruptedOnAttack(boolean castInterruptedOnAttack) {
		this.castInterruptedOnAttack = castInterruptedOnAttack;
	}
	
	public void setParent(Entity parent)
	{
		super.setParent(parent);
		
		for(ActiveAbility a : actives)
			a.setSrc(parent);
		for(ActiveAbility a : targets)
			a.setSrc(parent);
		for(ActiveAbility a : points)
			a.setSrc(parent);
	}
	
	public boolean isCasting() {
		return castingAbility != null;
	}

	public boolean isCastInterruptedOnAttack() {
		return castInterruptedOnAttack;
	}
	
	public BasicAttack getBasicAttack() {
		return (BasicAttack) targets.get(0);
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
	
	public void addTargetUnitAbility(TargetUnitAbility ability) {
		targets.add(ability);
		ability.setSrc(parent);
	}
	
	public void removeTargetUnitAbility(TargetUnitAbility ability) {
		targets.remove(ability);
		ability.setSrc(null);
	}
	
	public TargetUnitAbility getTargetUnitAbility(int i) {
		return targets.get(i);
	}
	
	private void addCastingIndicator(
			ActiveAbility ability)
	{
		indicator.setActiveAbility(ability);
		
		parent.get(GraphicsComponent.class)
			  .getDecorations()
			  .add(indicator, 0, -15);
	}
	
	private void removeCastingIndicator()
	{
		parent.get(GraphicsComponent.class)
			  .getDecorations()
			  .remove(indicator);
	}

	public void addAbilityListener(AbilityListener list) {
		lists.add(list);
	}

	public void removeAbilityListener(AbilityListener list) {
		lists.remove(list);
	}

	public void addDisabledAbility(Class<? extends ActiveAbility> c) {
		disabledAbilities.add(c);		
	}

	public void removeDisabledAbility(Class<? extends ActiveAbility> c) {
		disabledAbilities.remove(c);
	}
	
	private void readObject(java.io.ObjectInputStream in)
		     throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
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

}
