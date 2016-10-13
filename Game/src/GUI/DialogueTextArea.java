package GUI;

import javax.swing.JTextArea;
import javax.swing.Timer;

public class DialogueTextArea extends JTextArea
{
	private Timer timer;
	
	private String txt;
	
	private int i;
		
	private Runnable exe;
	
	public DialogueTextArea(String txt, Runnable exe)
	{
		this.txt = txt;
		
		this.exe = exe;
		
		i = 0;
		
		timer = new Timer(50, e -> updateText());
		
		setAlignmentX(LEFT_ALIGNMENT);
		
		timer.start();
	}
	
	private void updateText()
	{
		i++;
		
		super.setText(txt.substring(0, i));
		
		if(i == txt.length())
		{
			timer.stop();
			exe.run();
		}
	}
}
