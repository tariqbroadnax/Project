package Quest;

import java.io.Serializable;

import Entity.Entity;

public interface Reward extends Serializable, Cloneable
{
	public void give(Entity ent);
	
	public Object clone();
}
