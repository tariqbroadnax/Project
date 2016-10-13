package EditorGUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import Utilities.ImagePool;
import Utilities.ImagePoolListener;

public class ImageChooser extends JPanel
	implements ImagePoolListener
{
	private GUIResources resources;
	
	private HashMap<Integer, ImageButton> imgButtons;

	public ImageChooser(GUIResources resources)
	{
		this.resources = resources;
		
		imgButtons = new HashMap<Integer, ImageButton>();
			
		setBackground(Color.white);
		setLayout(new FlowLayout());
		
		resources.getImagePool()
				 .addListener(this);
	}
	
	public void addImage(int imgID)
	{		
		if(!imgButtons.containsKey(imgID))
		{
			ImageButton button = 
					new ImageButton(resources, imgID);
			
			button.addActionListener(e -> repaint());
			// updates unselected tiles when another is selected
			
			add(button);
			revalidate();
		}
	}
	
	public void removeImage(int imgID)
	{
		imgButtons.remove(imgID);
	}
	
	@Override
	public void imageImported(ImagePool src, int imageID) 
	{
		addImage(imageID);
	}
}
