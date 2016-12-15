package Editor.layer_selector;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Graphic.Graphic;
import Tileset.TileMap;
import Utilities.GUIUtils;

public class OpenProperties extends AbstractAction
	implements ListSelectionListener
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "Properties16.gif", 
							   largeIconFileName = PATH + "Properties24.gif";

	
	private JList<Graphic> list;
	
	public OpenProperties(JList<Graphic> list)
	{
		this.list = list;
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	
		list.addListSelectionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int index = list.getSelectedIndex();
		
		Graphic graph = list.getModel()
							.getElementAt(index);
		
		if(graph instanceof TileMap)
		{
			// open tile map dialog
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
		int index = list.getSelectedIndex();
		
		setEnabled(index != -1);
	}
	
}
