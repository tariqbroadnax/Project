package EditorGUI;

import java.util.List;

public interface SelectionRule 
{
	public boolean validSelection(
			Object obj, List<Object> objs);
}
