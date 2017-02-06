package Actions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import EntityComponent.EntityComponent;

// this components starts and performs actions
public class ActionComponent extends EntityComponent
{
	private List<Action> actions;
	
	public ActionComponent()
	{
		actions = new ArrayList<Action>();
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
		action.setActor(parent);
		
		actions.add(action);
	}
	
	public void stopAction(Action action) 
	{		
		if(actions.remove(action))
			action.dispose();
	}

	@Override
	protected EntityComponent _clone() {
		return null;
	}
}
