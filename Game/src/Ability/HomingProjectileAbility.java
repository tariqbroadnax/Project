package Ability;

public class HomingProjectileAbility 
	extends TargetUnitAbility
{	
	private HomingProjectile proj;
	
	public HomingProjectileAbility()
	{
		proj = new HomingProjectile();
		
		setRange(50);
		setCooldown(1000);
		setCastTime(500);
	}
	
	public void activate()
	{
		super.activate();
		
		HomingProjectile proj = (HomingProjectile) this.proj.clone();
		
		proj.setSource(src);
		proj.setTarget(target);
		
		src.getSceneLoc()
		   .addEntity(proj);
	}
}
