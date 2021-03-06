package Editor.actions;

import java.awt.KeyboardFocusManager;
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
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

import Editor.ActionSupportListener;
import Editor.ActionSupportNotifier;
import Utilities.GUIUtils;

public class Copy extends AbstractAction 
	implements PropertyChangeListener,
			   ActionSupportListener,
			   CaretListener
{
	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "Copy16.gif",
							   largeIconFileName =
			PATH + "Copy24.gif";
	
	private Action THCopy,
				   DEKCopy;
	
	private JComponent focusOwner;
	
	private ActionSupportNotifier notifier;
	private JTextComponent textComp;
	
	public Copy()
	{
		super("Copy");
	
		THCopy = TransferHandler.getCopyAction();
		DEKCopy = new DefaultEditorKit.CopyAction();
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Copy");
		putValue(LONG_DESCRIPTION, "Copy");
		
		putValue(NAME, "Copy");
		putValue(ACTION_COMMAND_KEY, "Copy");
		putValue(MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_C);
		
		KeyStroke keyStroke = 
				KeyStroke.getKeyStroke("control C");
		
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, smallIcon);
	
		setEnabled(false);
		
		KeyboardFocusManager manager = KeyboardFocusManager.
				getCurrentKeyboardFocusManager();
		
		manager.addPropertyChangeListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		e = new ActionEvent(focusOwner,
				   			ActionEvent.ACTION_PERFORMED,
				   			null);
		
		if(notifier != null)
			THCopy.actionPerformed(e);
		else
			DEKCopy.actionPerformed(e);
		System.out.println("Copy!!!");
	}

	private void removeListeners()
	{
		if(notifier != null)
		{
			notifier.removeActionSupportListener(this);
			notifier = null;
		}
		else if(textComp != null)
		{
			textComp.removeCaretListener(this);
			textComp = null;
		}
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
									
			removeListeners();
			
			if(focusOwner instanceof ActionSupportNotifier)
			{
				notifier = (ActionSupportNotifier) focusOwner;
				
				notifier.addActionSupportListener(this);
			
				checkAndEnabled();
			}
			else if(focusOwner instanceof JTextComponent)
			{
				textComp = (JTextComponent) focusOwner;
				
				textComp.addCaretListener(this);
			
				checkAndEnabled();
			}
			else
				setEnabled(false);
		}
		
	}
	
	private void checkAndEnabled()
	{		
		boolean enabled;
		
		if(textComp != null)
			enabled = textComp.getSelectedText() != null;
		else
			enabled = notifier.actionSupported(THCopy);
	
		setEnabled(enabled);
	}

	@Override
	public void actionSupportChanged(
			ActionSupportNotifier src) 
	{
		checkAndEnabled();
	}

	@Override
	public void caretUpdate(CaretEvent e) 
	{
		checkAndEnabled();
	}
}
