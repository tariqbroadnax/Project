package Movement;

import java.io.Serializable;

public class Force
	implements Cloneable, Serializable
{
	private double speed, dir;
	
	public Force() 
	{
		speed = -1; dir = 0;
	}
	
	public Force(double speed, double dir)
	{
		this.speed = speed;
		this.dir = dir;
	}
	
	public Force(Force force)
	{
		speed = force.speed;
		dir = force.dir;
	}

	public double dx(double t) {	
		return speed * Math.cos(dir) * t;
	}
	
	public double dy(double t) {
		return speed * Math.sin(dir) * t;
	}
	
	public void setDirection(double dir) {
		this.dir = dir;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getDirection() {
		return dir;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public Object clone() {
		return new Force(this);
	}
}
