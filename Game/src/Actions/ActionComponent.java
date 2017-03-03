package Actions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import EntityComponent.EntityComponent;

// this components starts and performs actions
public class ActionComponent extends EntityComponent
{
	private List<Action> actions;
	
	private List<Class<? extends Action>> disabledActions;
	
	public ActionComponent()
	{
		actions = new ArrayList<Action>();
		
		disabledActions = new ArrayList<Class<? extends Action>>();
	}
		
	@Override
	public void update(Duration delta) 
	{
		for(Action action : actions)
		{
			action.update(delta);
			
			if(action.isFinished())
				action.dispose();
		}
		
		actions.removeIf(action -> action.isFinished());		
	}
	
	public void startAction(Action action) 
	{			
		if(enabled && !disabledActions.contains(action.getClass()))
		{
			action.setActor(parent);
		
			actions.add(action);				
		}
	}
	
	public void stopAction(Action action) 
	{		
		if(actions.remove(action))
			action.dispose();
	}
	
	public void stopAll()
	{
		for(Action action : actions)
			action.dispose();
		
		actions.clear();
	}
	
	public boolean contains(Action action)
	{
		return actions.contains(action);
	}

	@Override
	protected EntityComponent _clone() {
		return new ActionComponent();
	}

	public void addDisabledAction(Class<? extends Action> c) {
		disabledActions.add(c);
	}

	public void removeDisabledAction(Class<? extends Action> c) {
		disabledActions.remove(c);
	}
}
