package Utilities;

import java.util.HashMap;
import java.util.Map;

public class Pack<A, B>
{
	public A head;
	public B tail;
	
	public Pack(A head, B tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
	public static Pack<?, ?> pack(Object head, Object tail)
	{
		return new Pack<Object, Object>(head, tail);
	}
	
	public static Map<?, ?> map(Pack...packs)
	{
		HashMap map = new HashMap();
		
		for(Pack pack : packs)
			map.put(pack.head, pack.tail);
		
		return map;
	}
	
	public String toString()
	{
		return "[" + head + "," + tail + "]";
	}
}
