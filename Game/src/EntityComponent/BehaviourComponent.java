package EntityComponent;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

import Behaviour.Behaviour;
import Entity.Entity;

public class BehaviourComponent extends EntityComponent
{
	private Collection<Behaviour> behaviours;
	
	public BehaviourComponent() {
		behaviours = new ArrayList<Behaviour>();
	}
	
	@Override
	public void update(Duration delta) {
		for(Behaviour b : behaviours)
			b.update(delta);
	}
	
	public void addBehaviour(Behaviour behaviour) {
		behaviours.add(behaviour);
		if(parent != null)
			behaviour.setSrc(parent);
	}
	
	public void removeBehaviour(Behaviour behaviour) {
		behaviours.add(behaviour);
	}
	
	public <E extends Behaviour> E getBehaviour(
			Class<E> c)
	{
		for(Behaviour beh : behaviours)
			if(beh.getClass().equals(c))
				return (E) beh;
		return null;
	}
	
	public Collection<Behaviour> getBehaviours() {
		return behaviours;
	}
	
	public void setParent(Entity parent) {
		super.setParent(parent);
		for(Behaviour b : behaviours)
			b.setSrc(parent);
	}

	@Override
	protected EntityComponent _clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
