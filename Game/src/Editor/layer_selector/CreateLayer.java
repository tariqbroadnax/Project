package Editor.layer_selector;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JList;

import Graphic.Graphic;
import Utilities.GUIUtils;

public class CreateLayer extends AbstractAction
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "Add16.gif", 
							   largeIconFileName = PATH + "Add24.gif";

	
	private JList<Graphic> list;
	
	public CreateLayer(JList<Graphic> list)
	{
		this.list = list;
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
	}
}
