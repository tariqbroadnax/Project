package Actions;

import Entity.Entity;
import Game.Updatable;

public interface Action extends Updatable
{	
	public void setActor(Entity actor);
		
	public void dispose();
	
	public boolean isFinished();
}