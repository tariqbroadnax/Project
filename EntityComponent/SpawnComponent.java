package EntityComponent;

import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

import Game.Entity;

public class SpawnComponent extends EntityComponent
{
	private transient Collection<Entity> children;
	
	public SpawnComponent()
	{
		children = new LinkedList<Entity>();
	}
	
	public SpawnComponent(SpawnComponent comp)
	{
		this();	
	}
	
	@Override
	public void update(Duration delta){}
	
	public void createChildren(Collection<Entity> entities)
	{
		for(Entity child : children)
		{
			child.get(MetaComponent.class)
				 .setParent(parent);

			entities.add(child);
		}
		
		children.clear();
	}
	
	public void addChildren(Entity...children)
	{
		for(int i = 0; i < children.length; i++)
			this.children.add(children[i]);
	}
	
	public void removeChildren(Entity...children)
	{
		for(int i = 0; i < children.length; i++)
			this.children.remove(children[i]);
	}
	
	@Override
	protected EntityComponent _clone()
	{
		return new SpawnComponent(this);
	}

	public String toString()
	{
		String str = super.toString();
		
		str += "\nchildren:";
		for(Entity child : children)
			str += '\n' + child.toString2();
		
		return str;
	}
}
