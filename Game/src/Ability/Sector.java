package Ability;

public class Sector 
{
	private double angle;
	
	private int pivots;
	
	public Sector()
	{
		angle = pivots = 0;
	}
	
	public Sector(double angle, int pivots)
	{
		this.angle = angle;
		this.pivots = pivots;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public void setPivots(int pivots) {
		this.pivots = pivots;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public int getPivots() {
		return pivots;
	}
}
