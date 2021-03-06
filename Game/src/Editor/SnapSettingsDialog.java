package Editor;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import Editor.comp.Dimension2DForm;
import Editor.comp.ValueListener;

public class SnapSettingsDialog extends JDialog
	implements ValueListener, WindowListener
{
	private Dimension2DForm dimForm;
	
	private SnapSettings settings;
	
	public SnapSettingsDialog(SnapSettings settings)
	{
		super(null, Dialog.ModalityType.APPLICATION_MODAL);
		
		dimForm = new Dimension2DForm();
	
		add(dimForm);		
		
		dimForm.addValueListener(this);
	
		setSnapSettings(settings);
		
		setTitle("Snap Settings");
		setSize(new Dimension(300, 200));

		addWindowListener(this);
	}
	
	public void setSnapSettings(SnapSettings settings)
	{
		this.settings = settings;
		
		double width = settings.getSnapWidth(),
			   height = settings.getSnapHeight();
		
		dimForm.setValue(width, height);
	}

	@Override
	public void valueChanged() 
	{
		double width = dimForm.getWidthValue(),
			   height = dimForm.getHeightValue();
		
		settings.setSnapWidth(width);
		settings.setSnapHeight(height);
	}

	@Override
	public void windowActivated(WindowEvent e) {}

	@Override
	public void windowClosed(WindowEvent e) {}

	@Override
	public void windowClosing(WindowEvent e) {
		valueChanged();
	}
	
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
	
}
