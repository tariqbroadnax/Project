package Utilities;

import java.util.Collection;

public class CollectionUtilities
{
	public static <E> void addAll(Collection<E> coll , E... members)
	{
		for(E member : members)
			coll.add(member);
	}
}
