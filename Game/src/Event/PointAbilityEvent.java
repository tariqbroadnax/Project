package Event;

import Ability.PointAbility;
import Game.Game;

public abstract class PointAbilityEvent extends Event
{	
	protected PointAbility ability;
	
	public PointAbilityEvent(Game game) {
		super(game);
	}
	
	public void setPointAbility(PointAbility ability) {
		this.ability = ability;
	}
}