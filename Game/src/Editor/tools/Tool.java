package Editor.tools;

import java.awt.Graphics;

import EditorGUI.MouseListener;
import EditorGUI.MouseMotionListener;

public interface Tool 
	extends MouseListener, MouseMotionListener 
{
	public default void paint(Graphics g){}
	
	public default void prepare(){}
}
