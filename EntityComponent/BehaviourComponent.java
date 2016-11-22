package EntityComponent;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

import Behaviour.Behaviour;
import Game.Entity;

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
