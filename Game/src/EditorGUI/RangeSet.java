package EditorGUI;

import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

public class RangeSet 
{
	private Collection<Range> available, unavailable;
	
	public RangeSet()
	{
		unavailable = new TreeSet<Range>();
		available = new TreeSet<Range>();
		
		available.add(Range.RANGE_POS);
	}
	
	public Range firstAvailableRange(int len)
	{
		for(Range range : available)
			if(range.length() >= len)
				return range.subRange(len);
		
		return null;
	}
	
	public boolean addRange(Range range)
	{
		for(Range unavailableRange : unavailable)
		{
			if(unavailableRange.intersects(range))
				return false;
		}
		
		Range parent = null;
		for(Range availableRange : available)
		{
			if(availableRange.intersects(range))
				parent = availableRange;
		}
		
		available.remove(parent);
		available.addAll(parent.intersected(range));
		unavailable.add(range);
		
		return true;
	}
	
	public Collection<Range> getAvailableRanges()
	{
		return Collections.unmodifiableCollection(available);
	}
	
	public Collection<Range> getUnavailableRanges()
	{
		return Collections.unmodifiableCollection(unavailable);
	}
}
