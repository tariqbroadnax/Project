package Quest;

import java.util.List;

import Dialogue.Dialogue;

public class DialogueTask extends Task
{
	private Dialogue d;
	
	public DialogueTask(){}
	
	public DialogueTask(Dialogue d)
	{
		this.d = d;
	}
	
	public DialogueTask(DialogueTask task)
	{
		d = task.d;
	}

	@Override
	public void start() {}

	@Override
	public boolean isFinished() 
	{
		List<Dialogue> completed = null; // FIXME decided where to put this
		
		return completed.contains(d);
	}

	@Override
	public Object clone() {
		return new DialogueTask(this);
	}
}
