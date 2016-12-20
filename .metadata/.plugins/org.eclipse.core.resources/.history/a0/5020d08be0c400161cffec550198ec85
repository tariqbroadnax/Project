package Utilities;

import java.util.Collection;
import java.util.function.Predicate;

public class Collections2 
{
	private Collections2(){}
	
	public static <E> E findFirst(
			Collection<E> coll, Predicate<E> pred)
	{
		for(E e : coll)
			if(pred.test(e))
				return e;
		return null;
	}
	
	public static <E> boolean contains(
			Collection<E> coll, Predicate<E> pred)
	{
		for(E e : coll)
			if(pred.test(e))
				return true;
		return false;
	}
	
	public static <E> void addAll(Collection<E> coll , E... members)
	{
		for(E member : members)
			coll.add(member);
	}
}
