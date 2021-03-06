package GUI;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.Duration;

import javax.swing.JFrame;
import javax.swing.Timer;

import Game.Updatable;
import Game.Updater;

public class GFrame extends JFrame
	implements WindowListener, ComponentListener,
			   Updatable
{
	private static final Dimension DEFAULT_SIZE =
			new Dimension(800, 600);
		
	private Timer timer;
	
	private GraphicsDevice device;
	
	private Updater updater;
	
	public GFrame(Updater updater)
	{
		this.updater = updater;
		
		setIgnoreRepaint(true);
		setSize(DEFAULT_SIZE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
				
		timer = new Timer(1000, e -> updateTitle());
		
		device = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
				
		addWindowListener(this);
		addComponentListener(this);
			
		timer.start();		
	}
	
	@Override
	public void update(Duration delta)
	{
		
	}
	
	public void setFullScreen(boolean isFullScreen)
	{
		device.setFullScreenWindow(isFullScreen ? this : null);
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
