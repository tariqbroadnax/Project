package Drivers;

import javax.swing.JFrame;

import Editor.comp.GraphicPreview;

public class TestDriver
{
	
	public static void main(String[] args)
	{	
		//EditorGUI editor = new EditorGUI();
	
		//new Game().start();
	
		JFrame frame = new JFrame();
		//EntityForm form = new EntityForm();
		//SpriteForm form = new SpriteForm();
		
//		DoubleField fld = new DoubleField();
	
		//AnimationList list = new AnimationList();
		GraphicPreview preview = new GraphicPreview();
		
		frame.add(preview);
		frame.setSize(400, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


