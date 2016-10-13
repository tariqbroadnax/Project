package EntityManager;

import java.io.Serializable;

import Game.SceneResources;
import Game.Updatable;

public abstract class EntityManager implements Updatable, Serializable
{
	protected final SceneResources resources;
	
	public EntityManager(SceneResources resources)
	{
		this.resources = resources;
	}
}
