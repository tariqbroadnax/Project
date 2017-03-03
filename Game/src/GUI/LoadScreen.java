package GUI;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.Timer;

import Graphic.ImagePool;

public class LoadScreen extends JPanel
{
	private File file = new File(
			"C:\\Users\\Tariq Broadnax\\Pictures\\thumb-1920-769587.png");
	
	private long fadeTime = 250, start;
	
	private Timer timer;
	
	enum Fade{IN, OUT};
	
	private Fade fade;
	
	private float alpha = 0f;
	
	public LoadScreen()
	{
		ImagePool.instance.request(file);
	
		timer = new Timer(50, e -> updateFade());
	}
	
	public void fadeIn()
	{
		start = System.currentTimeMillis();
	
		fade = Fade.IN;
		
		if(!timer.isRunning())
			timer.start();
	}
	
	public void fadeOut()
	{
		start = System.currentTimeMillis();
		
		fade = Fade.OUT;
		
		if(!timer.isRunning())
			timer.start();
	}
	
	private void updateFade()
	{
		long dt = System.currentTimeMillis() - start;
		
		if(dt > fadeTime)
		{
			dt = fadeTime;
			timer.stop();
		}
		
		if(fade == Fade.IN)
			alpha = (float) (dt * 1.0 / fadeTime);
		else if(fade == Fade.OUT)
			alpha = (float) (1 - dt * 1.0 / fadeTime);
				
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g.create();
		
        g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));

		BufferedImage img = ImagePool.instance.getImage(file);
		
		int x = getWidth()/2 - img.getWidth()/2,
			y = getHeight()/2 - img.getHeight()/2;
	
		g2d.drawImage(img, x, y, null);
	}
	
}
