package Quest;

import java.io.Serializable;

import Entity.Entity;

public interface Requirement extends Serializable, Cloneable 
{
	public boolean isSatisfied(Entity ent);
	
	public Object clone();
}
