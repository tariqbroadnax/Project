package Editor.comp;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import Editor.TextField;

public class FileField extends Form
	implements ActionListener
{
	private File file;
		
	private JFileChooser chooser;

	private TextField lbl;
	
	public FileField()
	{
		this(null);
	}

	public FileField(File file)
	{
		lbl = new TextField("Hello");
		
		JButton btn = new JButton("Browse");
		
		chooser = new JFileChooser();

		lbl.setEditable(false);
		lbl.setMaximumSize(new Dimension(0, 0));
		
		addField(lbl, 0, 0, 1);
		addComponent(btn, 1, 0, 1);

		btn.addActionListener(this);
		
		setFile(file);
	
		//label.setEditable(false);		
	}
	
	public void chooseFile()
	{
		int option = chooser.showOpenDialog(null);
		
		if(option == chooser.APPROVE_OPTION)
		{
			File file = chooser.getSelectedFile();
			
			setFile(file);
		}
	}
	
	public void setFile(File file)
	{
		this.file = file;
		
		if(file == null)
			lbl.setText(" ");
		else
		{
			String fileName = file.getAbsolutePath();
			
			lbl.setText(fileName);
		}
		
		repaint();
	}
	
	public void setFileFilter(FileFilter filter) {
		chooser.setFileFilter(filter);
	}
	
	public File getFile() 
	{
		return file;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		chooseFile();
		notifyListeners();
	}
}
