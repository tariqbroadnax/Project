package Editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import Editor.EditorResources;
import Editor.SnapSettings;
import Editor.SnapSettingsDialog;

public class OpenSnapSettings extends AbstractAction
{
	private SnapSettingsDialog dialog;
	
	public OpenSnapSettings(EditorResources resources)
	{
		super("Snap Settings");
		
		SnapSettings settings = resources.getSnapSettings();
		
		dialog = new SnapSettingsDialog(settings);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		dialog.setVisible(true);
	}

}
