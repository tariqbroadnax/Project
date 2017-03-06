package Modifiers;

import EntityComponent.CombatComponent;
import EntityComponent.StatsComponent;
import Stat.Stats;

public class Damage extends InstantEffect
{
	private double flatAmount, scaleAmount, percAmount;
	
	private double splashRad;
	
	private AttackAttribute atkAttr;

	private ElementAttribute eleAttr;	
	
	public Damage()
	{
		this(1, 1);		
	}
	
	public Damage(double flatAmount, double scaleAmount)
	{
		this.flatAmount = flatAmount;
		this.scaleAmount = scaleAmount;
	
		splashRad = 0;
		
		atkAttr = AttackAttribute.PHYSICAL;
		eleAttr = ElementAttribute.NONE;
	}
	
	public Damage(Damage damage)
	{
		super(damage);
		
		flatAmount = damage.flatAmount;
		scaleAmount = damage.scaleAmount;
		
		splashRad = damage.splashRad;
		
		atkAttr = damage.atkAttr;
		eleAttr = damage.eleAttr;
	}
	
	public void apply()
	{
		src.get(CombatComponent.class)
		   .applyDamage(this);
	}
	
	public void setFlatAmount(double flatAmount) 
	{
		if(flatAmount < 0)
			throw new IllegalArgumentException();

		this.flatAmount = flatAmount;
	}
	
	public void setScaleAmount(double scaleAmount) {
		this.scaleAmount = scaleAmount;
	}
	
	public void setSplashRadius(double splashRad) {
		this.splashRad = splashRad;
	}
	
	public void setAttackAttribute(AttackAttribute atkAttr) {
		this.atkAttr = atkAttr;
	}
	
	public void setElementAttribute(ElementAttribute eleAttr) {
		this.eleAttr = eleAttr;
	}
	
	public double getFlatAmount() {
		return flatAmount;
	}
	
	public double getScaleAmount() {
		return scaleAmount;
	}
	
	public double getSplashRad() {
		return splashRad;
	}
	
	public AttackAttribute getAttackAttribute() {
		return atkAttr;
	}
	
	public ElementAttribute getElementAttribute() {
		return eleAttr;
	}
	
	public Object clone() {
		return new Damage(this);
	}
}
