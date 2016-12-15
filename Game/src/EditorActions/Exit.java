package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import EditorGUI.GUIResources;
import EditorGUI.SceneSaveDialog;
import Utilities.GUIUtils;

public class Exit extends AbstractAction
	implements WindowListener
{	
	private GUIResources resources;
	
	public Exit() 
	{
		super("Exit");
	
		putValue(SHORT_DESCRIPTION, "Exit");
		putValue(LONG_DESCRIPTION, "Exit");
		
		putValue(NAME, "Exit");
		putValue(ACTION_COMMAND_KEY, "Exit");
		putValue(MNEMONIC_KEY, KeyEvent.VK_X);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				 KeyEvent.VK_X);
		
		KeyStroke keyStroke = KeyStroke.getKeyStroke(
				"alt F4");
		putValue(ACCELERATOR_KEY, keyStroke);
	}

	public void setResources(GUIResources resources)
	{
		this.resources = resources;
	}
	
	private void exit()
	{
		if(!resources.isSceneSaved() && false) //FIXME used for fast testing
		{
			int reply = JOptionPane.showConfirmDialog(
					null,
					"Do you want to save before exiting this scene?");
		
			if(reply == JOptionPane.CANCEL_OPTION)
				return;
			else if(reply == JOptionPane.NO_OPTION)
				System.exit(1);
			else
			{
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
		}
		else
			System.exit(1);

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		exit();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		exit();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
