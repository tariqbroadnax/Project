package Editor.selection;
import java.util.ArrayList;
import java.util.List;

public class SelectionHandler 
{
	private List<Object> selection;
	
	private List<SelectionListener> listeners;
	
	public SelectionHandler()
	{
		selection = null;
		
		listeners = new ArrayList<SelectionListener>();
	}
	
	public void setSelected(Object obj)
	{
		selection.clear();
		selection.add(obj);
		
		notifyListeners();
	}
	
	public void setSelection(List<Object> objs)
	{
		selection.clear();
		selection.addAll(objs);
		
		notifyListeners();
	}
	
	public void removeSelection()
	{
		selection.clear();
		
		notifyListeners();
	}
	
	public boolean onlySelected(Object obj)
	{
		return selection.size() == 1 &&
			   selection.contains(obj);
	}
	
	public boolean onlySelected(List<Object> objs)
	{
		return selection.containsAll(objs) &&
			   selection.size() == objs.size();
	}
	
	public boolean isSelected(Object obj)
	{
		return selection.contains(obj);
	}
	
	public boolean isSelected(List<Object> objs)
	{
		return selection.containsAll(objs);
	}
	
	public boolean selectionExist()
	{
		return !selection.isEmpty();
	}
	
	public boolean instanceSelection(Class<? extends Object> c)
	{
		for(Object obj : selection)
			if(!c.isInstance(obj))
				return false;
		
		return true;
	}
	
	public void addSelectionListener(
			SelectionListener listener)
	{
		listeners.add(listener);
	}
	
	private void notifyListeners()
	{
		for(SelectionListener listener : listeners)
			listener.selectionChanged();
	}
}