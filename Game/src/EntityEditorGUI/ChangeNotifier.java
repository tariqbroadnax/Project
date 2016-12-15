package EntityEditorGUI;

import java.util.Collection;

public interface ChangeNotifier
{
	public Collection<ChangeListener> getChangeListeners();
	
	public default void notifyListeners()
	{
		for(ChangeListener list : getChangeListeners())
			list.fieldChanged();
	}
	
	public default void addChangeListener(
			ChangeListener list)
	{
		getChangeListeners().add(list);
	}
	
	public default void removeChangeListener(
			ChangeListener list)
	{
		getChangeListeners().remove(list);
	}
}
