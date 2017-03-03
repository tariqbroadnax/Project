package Behavior;

import Actions.Action;
import Entity.Entity;
import Game.Updatable;

public abstract class Behavior implements Updatable
{
	protected Entity src;
	
	protected Action currAction;
	
	public void setSrc(Entity src) {
		this.src = src;
	}
	
	public Entity getSrc() {
		return src;
	}
	
	public Action getCurrentAction() {
		return currAction;
	}
}
