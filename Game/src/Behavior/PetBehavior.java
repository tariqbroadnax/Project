package Behavior;

import java.time.Duration;

import Actions.Follow;
import Entity.Entity;
import TestEntity.Pet;

public class PetBehavior extends Behavior
{
	private Follow follow;
	
	public PetBehavior()
	{
		follow = new Follow();
	}
	
	@Override
	public void update(Duration delta) 
	{
		Pet pet = (Pet) src;
	
		Entity owner = pet.getOwner();
		
		if(owner == null)
			currAction = null;
		else
		{
			follow.setTarget(owner);
						
			currAction = follow;
		}
	}
	
	public void setSrc(Entity src)
	{
		super.setSrc(src);
		
		follow.setActor(src);
	}	
}
