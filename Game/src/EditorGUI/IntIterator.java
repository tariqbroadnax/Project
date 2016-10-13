package EditorGUI;

import java.util.Iterator;

public class IntIterator implements Iterator<Integer>
{
	public int curr, end;
	
	public IntIterator(int start, int end)
	{
		curr = start;
		this.end = end;
		
	}
	@Override
	public boolean hasNext() {
		return curr <= end;
	}

	@Override
	public Integer next() 
	{
		return curr++;
	}

}
