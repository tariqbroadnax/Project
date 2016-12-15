package Utilities;

import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class Components 
{
	public static void setUIFont(FontUIResource f)
	{
		Enumeration keys = UIManager.getDefaults()
								 	.keys();
		
		while(keys.hasMoreElements())
		{
			Object key = keys.nextElement();
			Object val = UIManager.get(key);
			
			if(val != null && val instanceof FontUIResource)
				UIManager.put(key, f);
		}
	}
}
