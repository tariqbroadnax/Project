package EntityEditorGUI;

import java.util.Collection;

public interface FieldContainer 
{
	public Collection<FieldListener> getFieldListeners();
	
	public default void notifyListeners()
	{
		for(FieldListener list : getFieldListeners())
			list.fieldChanged();
	}
	
	public default void addFieldListener(FieldListener list)
	{
		getFieldListeners().add(list);
	}
	
	public default void removeFieldListener(FieldListener list)
	{
		getFieldListeners().remove(list);
	}
}
