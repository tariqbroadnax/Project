package Drivers;

import javax.swing.JFrame;

import Editor.comp.EntityForm;

public class TestDriver
{
	public static void main(String[] args)
	{	
		//EditorGUI editor = new EditorGUI();
	
		//new Game().start();
	
		JFrame frame = new JFrame();
		EntityForm form = new EntityForm();
		//MovementComponentForm form = new MovementComponentForm();
		
		frame.add(form);
		frame.setSize(400, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


