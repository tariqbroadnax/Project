package Editor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class PlayScene extends AbstractAction
{
	private EditorResources resources;
	
	private boolean playing = false;
	
	public PlayScene(EditorResources resources)
	{
		this.resources = resources;
		
		putValue(NAME, "Play");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(playing)
		{
			resources.stopPlayingScene();
			putValue(NAME, "Play");
		}
		else
		{
			resources.startPlayingScene();
			putValue(NAME, "Stop");
		}
		
		playing = !playing;
	}

}
