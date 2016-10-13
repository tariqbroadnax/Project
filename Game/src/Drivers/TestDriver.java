package Drivers;

import javax.swing.JFrame;

import EditorGUI.EditorGUI;

public class TestDriver
{
	public static void main(String[] args)
	{	
		EditorGUI editor = new EditorGUI();
		
		JFrame frame = new JFrame();
		
		//EntityEditor editor = new EntityEditor();
		
		frame.setSize(300, 600);
		// frame.add(new GraphicsComponentEditor());
		//frame.add(editor);
		//frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
	}
}


