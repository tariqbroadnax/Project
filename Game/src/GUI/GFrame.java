package GUI;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.Timer;

import Game.Resources;
import GameClient.GameClientResources;

public class GFrame extends JFrame
{
	private static final Dimension DEFAULT_DIMENSION =
			new Dimension(800, 600);
	
	private Resources resources;
	
	private Timer timer;
	
	private boolean isFullScreen;
	
	private GraphicsDevice device;

	public GFrame(Resources resources)
	{
		setSize(DEFAULT_DIMENSION);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.resources = resources;
		
		isFullScreen = false;
		
		timer = new Timer(1000, e -> updateTitle());
		
		device = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
				
		timer.start();
	}
	
	public void toggleFullScreen()
	{
		setFullScreen(!isFullScreen);
	}
	
	public void setFullScreen(boolean isFullScreen)
	{
		if(this.isFullScreen == isFullScreen) 
			return;
		
		this.isFullScreen = isFullScreen;
	
		if(isFullScreen)
		{
			device.setFullScreenWindow(this);
			
			resources.getGUI()
					 .getDebugPanel()
					 .setDebugTextEnabled(true);
			
			timer.stop();
		}
		else
		{
			device.setFullScreenWindow(null);
			
			resources.getGUI()
					 .getDebugPanel()
					 .setDebugTextEnabled(false);
			
			timer.start();
		}
	}

	private void updateTitle()
	{		
		setTitle("UPS: " + resources.getUpdater()
						  			.getActualFrequency());
	}
}
