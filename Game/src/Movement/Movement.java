package Movement;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Movement implements Cloneable, Serializable
{
	private boolean enabled; 

	private List<Force> forces;
	
	private double defaultSpeed;

	public Movement()
	{
		enabled = true;
		
		forces = new ArrayList<Force>();
		
		defaultSpeed = 30;
	}
	
	public Movement(Movement movement)
	{
		enabled = movement.enabled;
		
		forces = new ArrayList<Force>();

		for(Force force : movement.forces)
			forces.add((Force) force.clone());
	
		defaultSpeed = movement.defaultSpeed;
	}
	
	public void move(Point2D.Double loc, Duration delta)
	{
		if(enabled)
		{
			double netDX = 0, netDY = 0;
			
			double t = delta.toNanos() / 1E9;
	
			for(Force force : forces)
			{				
				double speed = force.getSpeed();
				
				if(speed < 0)
				{
					force.setSpeed(defaultSpeed);
					
					netDX += force.dx(t);
					netDY += force.dy(t);
					
					force.setSpeed(-1);
				}
				else
				{
					netDX += force.dx(t);
					netDY += force.dy(t);
				}				
			}
			
			loc.x += netDX; loc.y += netDY;
		}
	}
	
	public void setDefaultSpeed(double defaultSpeed) {
		this.defaultSpeed = defaultSpeed;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void addForce(Force force) {
		forces.add(force);
	}
	
	public void removeForce(Force force) {
		forces.remove(force);
	}
	
	public List<Force> getForces() {
		return forces;
	}
	
	public Object clone() {
		return new Movement(this);
	}
}
