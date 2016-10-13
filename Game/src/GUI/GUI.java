package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import static Maths.Direction.*;

import static Actions.PlayerMovementUpdater.*;

import Game.Resources;

public class GUI
{
	private GFrame frame;
	
	private GLayeredPane layeredPane;
	
	private GCanvas canvas;

	private HUD hud;
	
	private DebugPanel debugPanel;
	
	public GUI(Resources resources)
	{
		frame = new GFrame(resources);
		
		layeredPane = new GLayeredPane();
		
		canvas = new GCanvas();
		
		hud = new HUD(resources);
		
		debugPanel = new DebugPanel(resources);
		
		canvas.setPainter(resources.getPainter());
		canvas.setBackground(Color.white);
		canvas.setFocusable(true);
		
		frame.addWindowFocusListener(new RequestFocus(canvas));
		frame.setContentPane(layeredPane);
		
		layeredPane.addAndSetLayer(canvas, 0);
		layeredPane.addAndSetLayer(hud, 1);
		layeredPane.addAndSetLayer(debugPanel, 2);
				
		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
			  .put(KeyStroke.getKeyStroke("F11"),
				   "FS");
		
		canvas.getActionMap()
			  .put("FS", actionOf(e -> frame.toggleFullScreen()));
		
		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		  		.put(KeyStroke.getKeyStroke("W"), "up");
		
		canvas.getActionMap()
		  	  .put("up", new Move(resources, N));
	
		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
  		.put(KeyStroke.getKeyStroke("released W"), "up2");

		canvas.getActionMap()
  	  	.put("up2", new UnMove(resources, N));

		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
  			  .put(KeyStroke.getKeyStroke("S"), "down");

		canvas.getActionMap()
  	  		  .put("down", new Move(resources, S));
		
		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		  .put(KeyStroke.getKeyStroke("released S"), "down2");

	canvas.getActionMap()
  		  .put("down2", new UnMove(resources, S));
	
		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		  	  .put(KeyStroke.getKeyStroke("D"), "right");

		canvas.getActionMap()
  		  	  .put("right", new Move(resources, E));
	
		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
	  	  .put(KeyStroke.getKeyStroke("released D"), "right2");

	canvas.getActionMap()
	  	  .put("right2", new UnMove(resources, E));
	
		canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		  .put(KeyStroke.getKeyStroke("A"), "left");

	canvas.getActionMap()
  		  .put("left", new Move(resources, W));
	
	canvas.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
	  .put(KeyStroke.getKeyStroke("released A"), "left2");

canvas.getActionMap()
	  .put("left2", new UnMove(resources, W));
		//canvas.addMouseListener(new MovePlayer(resources));
		
		/*
		 * Frame
		 * LayeredPane
		 * panels (game > hud > debug)
		 */		
	}
	
	private AbstractAction actionOf(ActionListener listener)
	{
		return new AbstractAction() 
		{
			public void actionPerformed(ActionEvent e) 
			{listener.actionPerformed(e);}			
		};
	}
	
	public void repaintAll()
	{		
		layeredPane.repaint();
	}
	
	public void setVisible(boolean visible)
	{
		frame.setVisible(visible);
	}
	
	public GCanvas getCanvas()
	{
		return canvas;
	}
	
	/*public HUD getHUD()
	{
		return hud;
	}*/
	
	public DebugPanel getDebugPanel()
	{
		return debugPanel;
	}
}
