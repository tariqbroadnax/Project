package EditorGUI;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class Range 
	implements Comparable<Range>, Iterable<Integer>
{
	public final static Range RANGE_POS = new Range(0, Integer.MAX_VALUE);
	
	public final int start, end;
	
	public Range(int start, int end)
	{
		this.start = start;
		this.end = end;
	}
	
	public boolean intersects(Range range)
	{
		return Math.max(start, range.start) <=
			   Math.min(end, range.end);
	}
	
	public Collection<Range> intersected(Range range)
	{		
		Collection<Range> intersected =
				new LinkedList<Range>();
		
		if(!intersects(range))
		{
			intersected.add(this);
		}
		else
		{
			Range subRange;
			
			if(start < range.start)
			{
				subRange = new Range(start, range.start - 1);
				intersected.add(subRange);
			}
			
			if(end > range.end)
			{
				subRange = new Range(range.end + 1, end);
				intersected.add(subRange);
			}	
		}
		
		return intersected;
	}
	
	public int length()
	{
		return end - start + 1;
	}
	
	public Range subRange(int len)
	{
		return new Range(start, start + len - 1);
	}
	
	public String toString()
	{
		return "[" + start + "," + end + "]";
	}

	@Override
	public int compareTo(Range range) 
	{
		if(start < range.start)
			return -1;
		else if(start == range.start)
		{
			if(end < range.end)
				return -1;
			else if(end == range.end)
				return 0;
			else
				return 1;
		}
		else
			return 1;
		
	}

	@Override
	public Iterator<Integer> iterator() 
	{
		return new IntIterator(start, end);
	}
	
	/* add range
	 * returns all available ranges
	 *  
	 */
	
}
