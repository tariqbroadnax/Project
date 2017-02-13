package Event;

import Game.Game;

public abstract class Event implements Runnable
{
	protected Game game;

	public Event(Game game)
	{
		this.game = game;
	}
	
	public void setGame(Game game) {
		this.game = game;
	}
}
