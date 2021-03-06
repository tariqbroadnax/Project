package Editor.actions;

import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

import Editor.ActionSupportNotifier;
import Utilities.GUIUtils;

public class Paste extends AbstractAction
	implements PropertyChangeListener,
			   FlavorListener
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "Paste16.gif", largeIconFileName = PATH + "Paste24.gif";

	private Action THPaste, DEKPaste;
	
	private JComponent focusOwner;
	
	private ActionSupportNotifier notifier;
	private JTextComponent textComp;
	
	public Paste()
	{
		super("Paste");
		
		THPaste = TransferHandler.getPasteAction();
		DEKPaste = new DefaultEditorKit.PasteAction();
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Paste");
		putValue(LONG_DESCRIPTION, "Paste");
		
		putValue(NAME, "Paste");
		putValue(ACTION_COMMAND_KEY, "Paste");
		putValue(MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_P);
		
		KeyStroke keyStroke = KeyStroke.getKeyStroke(
				"control V");
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, smallIcon);
		
		setEnabled(false);
		
		KeyboardFocusManager manager = KeyboardFocusManager.
				getCurrentKeyboardFocusManager();
		
		manager.addPropertyChangeListener(this);
	
		Clipboard clip = Toolkit.getDefaultToolkit()
								.getSystemClipboard();
		
		clip.addFlavorListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{		
		e = new ActionEvent(focusOwner,
				ActionEvent.ACTION_PERFORMED,
				null);
		
		if(focusOwner instanceof JTextComponent)
			DEKPaste.actionPerformed(e);
		else
			THPaste.actionPerformed(e);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e) 
	{
		Object o = e.getNewValue();
		
		if (o instanceof JComponent)
		{			
			JComponent focusOwner = (JComponent) o;

			if(focusOwner instanceof JMenuItem ||
			   focusOwner instanceof JRootPane ||
			   focusOwner.getParent() instanceof JToolBar ||
			   focusOwner instanceof JPopupMenu)
				return;
		
			this.focusOwner = focusOwner;
			
			setEnabled(false);
			checkAndEnabled();
		}	
	}
	
	private void checkAndEnabled()
	{		
		if(focusOwner == null) return;
			
		Transferable t = Toolkit.getDefaultToolkit()
								.getSystemClipboard()
								.getContents(this);
										
		TransferHandler handler = focusOwner.getTransferHandler();
	
		if(handler == null) return;

		TransferSupport support = new TransferSupport(focusOwner, t);			
		
		boolean enabled = handler.canImport(support);

		setEnabled(enabled);
	}
	
	@Override
	public void flavorsChanged(FlavorEvent e) 
	{
		checkAndEnabled();
	}
}
