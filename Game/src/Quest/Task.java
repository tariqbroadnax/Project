package Quest;

import java.io.Serializable;

import Entity.Entity;

public abstract class Task implements Serializable, Cloneable
{
	protected Entity tasker;	
	
	public abstract void start();
	
	public abstract boolean isFinished();
	
	public void stop(){}
	
	public void setTasker(Entity tasker) {
		this.tasker = tasker;
	}
	
	public abstract Object clone();
}