package Modifiers;

import java.time.Duration;

public class TickEffect extends GradualEffect
{
	private Span nextTick;
		
	public TickEffect()
	{
		nextTick = new Span(250);
	}
	
	public TickEffect(TickEffect effect)
	{
		nextTick = (Span) effect.nextTick.clone();
	} 
	
	@Override
	public void update(Duration delta) 
	{
		super.update(delta);
		
		nextTick.update(delta);

		long len = nextTick.getLength();

		while(nextTick.isFinished())
		{
			effect.apply();

			nextTick.reverse(len);
		}
	}
	
	public void setTickDelay(long delay) {
		nextTick.setLength(delay);
	}
	
	public long getTickDelay() {
		return nextTick.getLength();
	}
	
	public Object clone() {
		return new TickEffect(this);
	}
}
