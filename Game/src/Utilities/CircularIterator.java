package Utilities;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public class CircularIterator<E>
	implements Serializable
{
	private Collection<E> coll;
	
	private transient Iterator<E> iter;
	
	private transient int position = 0;
	
	public CircularIterator(Collection<E> coll)
	{
		this.coll = coll;	
		
		iter = coll.iterator();
		
		position = 0;
	}
	
	public CircularIterator(Collection<E> coll, int position)
	{
		this(coll);
		
		while(this.position < position)
			next();
	}
	
	public void reset()
	{
		iter = coll.iterator();
		position = 0;
	}
	
	public E next()
	{
		if(coll.size() == 0)
			return null;
		else if(iter.hasNext())
		{
			position++;

			return iter.next();
		}
		else
		{
			iter = coll.iterator();
			position = 0;
			return next();
		}
	}
	
	public int getPosition()
	{
		return position;
	}
	
	private void writeObject(java.io.ObjectOutputStream out)
		     throws IOException
    {
		out.defaultWriteObject();
		out.writeInt(position);
	}
	
	private void readObject(java.io.ObjectInputStream in)
		     throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		iter = coll.iterator();
		this.position = 0;
		int position = in.readInt();
		
		while(this.position < position)
			next();				
	}
}
