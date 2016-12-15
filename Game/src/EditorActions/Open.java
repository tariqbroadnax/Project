package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import EditorGUI.GUIResources;
import EditorGUI.SceneSaveDialog;
import Utilities.GUIUtils;

public class Open extends AbstractAction{

	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "Open16.gif",
							   largeIconFileName =
			PATH + "Open24.gif";
	
	private GUIResources resources;
	
	public Open() 
	{
		super("Open");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Open");
		putValue(LONG_DESCRIPTION, "Open");
		
		putValue(NAME, "Open");
		putValue(ACTION_COMMAND_KEY, "Open");
		putValue(MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				 KeyEvent.VK_O);
		
		KeyStroke keyStroke = KeyStroke.getKeyStroke(
				"control O");
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);	
	}
	
	public void setResources(GUIResources resources)
	{
		this.resources = resources;
	}

	private void open()
	{
		JFileChooser chooser = 
				new JFileChooser();
		
		chooser.setFileSelectionMode(
				JFileChooser.FILES_ONLY);
		
	    FileNameExtensionFilter filter = 
	    		new FileNameExtensionFilter(
	    				"Scene Files", "scn");
	    
	    chooser.setFileFilter(filter);
	    
	    int returnVal = chooser.showOpenDialog(null);

	    if(returnVal == JFileChooser.APPROVE_OPTION)
	    {
	    	File file = chooser.getSelectedFile();
	    	
	    	try {
				resources.loadScene(file.toPath());
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null,
						"Invalid file format");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						e.getMessage());
			}
	    }
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if(!resources.isSceneSaved())
		{
			int reply = JOptionPane.showConfirmDialog(
					null,
					"Do you want to save before exiting this scene?");
		
			if(reply == JOptionPane.CANCEL_OPTION)
				return;
			else if(reply == JOptionPane.NO_OPTION)
				open();
			else
				if(resources.canSave()) {
					try {
						resources.saveScene();
					} catch(IOException ex) {
						JOptionPane.showMessageDialog(null,
								ex.getMessage());
					}
				}
				else
					new SceneSaveDialog(resources)
							.setVisible(true);
				
		}
		else
			open();
	}

}