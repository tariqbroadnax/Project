package Drivers;

import java.awt.geom.Point2D;

import Editor.SnapSettings;
import Editor.SnapSettingsDialog;

public class TestDriver
{
	public static void main(String[] args)
	{	
		SnapSettings settings = new SnapSettings();
		
		for(int y = -50; y <= 50; y+=10)
			for(int x = -50; x <= 50; x+=10)
			{
				Point2D.Double loc = settings.snapLoc(x, y);
				System.out.println(x + " " + y + " " + loc.x + " " + loc.y);
			}
		}
}


