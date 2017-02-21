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

public class Cut extends AbstractAction
	implements PropertyChangeListener,
			   ActionSupportListener,
			   CaretListener
{
	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "Cut16.gif",
							   largeIconFileName =
			PATH + "Cut24.gif";
	
	private Action THCut, DEKCut;
	
	private JComponent focusOwner;
	
	private ActionSupportNotifier notifier;
	private JTextComponent textComp;
	
	public Cut()
	{
		super("Cut");
		
		THCut = TransferHandler.getCutAction();
		DEKCut = new DefaultEditorKit.CutAction();
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Cut");
		putValue(LONG_DESCRIPTION, "Cut");
		
		putValue(NAME, "Cut");
		putValue(ACTION_COMMAND_KEY, "Cut");
		putValue(MNEMONIC_KEY, KeyEvent.VK_T);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_T);
		
		KeyStroke keyStroke = 
				KeyStroke.getKeyStroke("control X");
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
			THCut.actionPerformed(e);
		else
			DEKCut.actionPerformed(e);
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
			enabled = notifier.actionSupported(THCut);
	
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
