package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioPlayer implements LineListener
{
	private ExecutorService service;
	
	private float volume = 1f; // [0, 100]
	
	private List<Clip> clips;
	
	public AudioPlayer()
	{
		service = Executors.newCachedThreadPool();	
		
		clips = new ArrayList<Clip>();
	}
	
	public void setVolume(float volume)
	{
		this.volume = volume;
		
		for(Clip clip : clips)
			setVolume(clip);
	}
	
	private void setVolume(Clip clip)
	{
		FloatControl gainControl = (FloatControl) 
				clip.getControl(FloatControl.Type.MASTER_GAIN);
	
		float min = gainControl.getMinimum(),
			  max = gainControl.getMaximum();
		
		gainControl.setValue(min + (max - min) * volume);
	}
	
	public void start(Clip clip)
	{
		setVolume(clip);
		
		service.execute(() -> clip.start());
	}

	@Override
	public void update(LineEvent e) 
	{
		if(e.getType() == LineEvent.Type.STOP)
		{
			Line line = e.getLine();
			
			line.removeLineListener(this);
			clips.remove(line);
		}
	}
}