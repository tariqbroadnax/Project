package EditorGUI;

public class ImageID 
{
	public final int val;
	
	public ImageID(int val)
	{
		this.val = val;
	}
	
	public boolean equals(Object o)
	{
		if(o instanceof ImageID)
			return val == ((ImageID)o).val;
		return false;
	}	
}
