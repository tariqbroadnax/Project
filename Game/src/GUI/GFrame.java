package GUI;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import Game.Updatable;
import Game.Updater;

public class GFrame extends JFrame
	implements WindowListener, ComponentListener,
			   Updatable
{
	private static final Dimension DEFAULT_DIMENSION =
			new Dimension(800, 600);
		
	private Timer timer;
	
	private boolean isFullScreen,
					requestFullScreen;
	
	private GraphicsDevice device;
	
	private Updater updater;

	public GFrame(Updater updater)
	{
		setSize(DEFAULT_DIMENSION);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.updater = updater;
		
		isFullScreen = false;
		
		timer = new Timer(1000, e -> updateTitle());
		
		device = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
				
		addWindowListener(this);
		addComponentListener(this);
		
		updater.addUpdatable(this);
		
		getRootPane().getInputMap(
				JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke("F"),
				"toggleFS");
		
		getRootPane().getActionMap()
					 .put("toggleFS", new MyAction());
		
		timer.start();
	}
	
	@Override
	public void update(Duration delta)
	{
		if(requestFullScreen == isFullScreen)
			return;
		
		isFullScreen = requestFullScreen;
		
		if(isFullScreen)
		{
			device.setFullScreenWindow(this);
			timer.stop();
		
		}
		else
		{
			device.setFullScreenWindow(null);
			timer.start();
		}
	}
	
	private class MyAction extends AbstractAction
	{
		public void actionPerformed(ActionEvent e) {
			toggleFullScreen();
		}	
	}
	
	public void toggleFullScreen()
	{
		setFullScreen(!isFullScreen);
	}
	
	public void setFullScreen(boolean isFullScreen)
	{
		requestFullScreen = isFullScreen;
	}

	private void updateTitle()
	{		
		setTitle("FPS: " + updater.getActualFrequency());
	}

	@Override
	public void windowActivated(WindowEvent e) {
		updater.play();
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		updater.stop();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		updater.pause();
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		updater.play();
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		updater.pause();
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		updater.start();
	}
	
	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentResized(ComponentEvent e) 
	{		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		createBufferStrategy(2);
	}	
}
