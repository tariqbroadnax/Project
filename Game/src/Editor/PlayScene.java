package Editor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Utilities.GUIUtils;

public class PlayScene extends AbstractAction
{
	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\media\\";
	
	public static final String smallIconFileName1 =
			PATH + "Play16.gif",
							   smallIconFileName2 =
			PATH + "Stop16.gif";
			
	private EditorResources resources;
	
	private boolean playing = false;
	
	private ImageIcon smallIcon1, smallIcon2;
	
	public PlayScene(EditorResources resources)
	{
		this.resources = resources;
		
		putValue(NAME, "Play");
		
		smallIcon1 = GUIUtils.ImageIcon(smallIconFileName1);
		smallIcon2 = GUIUtils.ImageIcon(smallIconFileName2);
		
		putValue(SMALL_ICON, smallIcon1);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(playing)
		{
			resources.stopPlayingScene();
			putValue(NAME, "Play");
			putValue(SMALL_ICON, smallIcon1);
		}
		else
		{
			resources.startPlayingScene();
			putValue(NAME, "Stop");
			putValue(SMALL_ICON, smallIcon2);
		}
		
		playing = !playing;
	}
}
