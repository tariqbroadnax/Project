package Drivers;

import javax.swing.JFrame;

import Editor.comp.MovementEditor;

public class TestDriver
{
	public static void main(String[] args)
	{	
		//EditorGUI editor = new EditorGUI();
	
		//new Game().start();
	
		JFrame frame = new JFrame();
		MovementEditor e = new MovementEditor();
		
		frame.add(e);
		frame.setSize(400, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


