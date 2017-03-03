package Modifiers;

import java.io.Serializable;
import java.time.Duration;

import Game.Updatable;

public class Span implements Updatable,
	Serializable, Cloneable 
{
	public final int UNLIMITED = -1;
	
	private long len, elapsed;
	
	public Span()
	{
		len = UNLIMITED;
	}
	
	public Span(long len)
	{
		this.len = len;
	}
	
	public Span(Span span)
	{
		len = span.len;
	}
	
	public void update(Duration delta) {
		len += delta.toMillis();
	}
	
	public void setLength(long len) {
		this.len = len;
	}
	
	public long getLength() {
		return len;
	}
	
	public boolean isFinished() {
		return elapsed >= len;
	}
	
	public void reverse(long len) {
		elapsed -= len;
	}
	
	public void reset() {
		elapsed = 0;
	}
	
	public Object clone() {
		return new Span(this);
	}
}
