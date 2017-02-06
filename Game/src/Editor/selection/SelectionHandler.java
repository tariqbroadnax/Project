package Editor.selection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectionHandler 
{
	private List<Object> selection;
	
	private List<SelectionListener> listeners;
	
	private boolean sceneSelection;
	
	public SelectionHandler()
	{
		selection = new ArrayList<Object>();
		
		listeners = new ArrayList<SelectionListener>();
	}
	
	public void setSelection(Object obj, boolean sceneSelection)
	{
		selection.clear();
		selection.add(obj);
		
		this.sceneSelection = sceneSelection;
		
		notifyListeners();
	}
	
	public void setSelections(List<? extends Object> objs, boolean sceneSelection)
	{
		selection.clear();
		selection.addAll(objs);
		
		this.sceneSelection = sceneSelection;

		notifyListeners();
	}
	
	public void removeSelection()
	{
		selection.clear();
		
		notifyListeners();
	}
	
	public boolean onlySelection(Object obj)
	{
		return selection.size() == 1 &&
			   selection.contains(obj);
	}
	
	public boolean onlySelections(List<? extends Object> objs)
	{
		return selection.containsAll(objs) &&
			   selection.size() == objs.size();
	}
	
	public boolean isSelection(Object obj)
	{
		return selection.contains(obj);
	}
	
	public boolean isSelections(List<? extends Object> objs)
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
		{
			if(!obj.getClass().equals(c))
				return false;
		}
		
		return selection.size() > 0;
	}
	
	public void addSelectionListener(
			SelectionListener listener)
	{
		listeners.add(listener);
	}
	
	public void notifySelectionModified()
	{
		for(SelectionListener list : listeners)
			list.selectionModified();
	}
	
	public boolean sceneSelection() {
		return sceneSelection;
	}
	
	public List<Object> getSelection() {
		return Collections.unmodifiableList(selection);
	}
	
	public int selectionCount() {
		return selection.size();
	}
	
	private void notifyListeners()
	{
		for(SelectionListener listener : listeners)
			listener.selectionChanged();
	}
}
