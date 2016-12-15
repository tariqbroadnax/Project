package EditorGUI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class SelectionManager extends Observable
{
	private ArrayList<Object> selectedObjs;

	private Collection<SelectionRule> rules;
	
	public SelectionManager()
	{
		selectedObjs = new ArrayList<Object>();
	
		rules = new LinkedList<SelectionRule>();
	}
	
	public void select(Object obj)
	{		
		if(!validSelection(obj))
			unselectAll();
		
		selectedObjs.add(obj);

		setChanged();
		notifyObservers();
	}
	
	public void unselect(Object obj)
	{
		selectedObjs.remove(obj);
		
		setChanged();
		notifyObservers();
	}
	
	public void unselectAll()
	{
		selectedObjs.clear();
		
		setChanged();
		notifyObservers();
	}
	
	private boolean validSelection(Object o)
	{
		boolean validSelection = true;
		
		Iterator<SelectionRule> iter =
				rules.iterator();
		
		while(iter.hasNext() && validSelection)
		{
			SelectionRule rule = iter.next();
			
			validSelection &= 
					rule.validSelection(o, selectedObjs);
		}
		
		return validSelection;
	}
	
	public boolean isSelected(Object obj) {
		return selectedObjs.contains(obj);
	}
	
	public boolean objectSelected() {
		return selectedObjs.size() > 0;
	}

	public void addSelectionRule(SelectionRule rule) {
		rules.add(rule);
	}

	public void removeSelectionRule(SelectionRule rule) {
		rules.remove(rule);
	}	
	
	public List<Object> getSelectedObjects() {
		return Collections.unmodifiableList(
				selectedObjs);
	}
}
