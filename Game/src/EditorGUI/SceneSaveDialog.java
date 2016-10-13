package EditorGUI;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SceneSaveDialog extends JDialog
{
	private JLabel nameLabel, dirLabel;
	
	private JTextField nameField,
					   dirField;
	
	private JButton browseButton,
					saveButton, cancelButton;
		
	private Path dir;
	
	private GUIResources resources;
	
	public SceneSaveDialog(GUIResources resources)
	{
		super(resources.frame, "Save Scene",
			  ModalityType.DOCUMENT_MODAL);
	
		this.resources = resources;
		
		nameLabel = new JLabel("File Name: ");
		dirLabel = new JLabel("Source Folder: ");
		
		nameField = new JTextField(10);
		dirField = new JTextField(15);
		
		browseButton = new JButton("Browse ");
		saveButton = new JButton("Save");
		cancelButton = new JButton("Cancel");
		
		setSize(400, 200);
		setResizable(false);
		
		addComponents();
		
		browseButton.addActionListener(e -> browse());
		cancelButton.addActionListener(e -> setVisible(false));
		saveButton.addActionListener(e -> save());
		
		saveButton.setEnabled(false);
		
		dirField.setEditable(false);
	}
	
	private void save()
	{	
		Path path = buildPath();
		
		boolean pathCreated = createPath(path);
		
		if(pathCreated)
		{
			boolean saveSuccessful = saveToPath(path);
		
			if(saveSuccessful)
			{
				resources.setSceneFile(path);
				dispose();
			}
			else
				deleteCreatedPath(path);
		}	
	}
	
	private void browse()
	{
		JFileChooser chooser = new JFileChooser();
		
		chooser.setFileSelectionMode(
				JFileChooser.DIRECTORIES_ONLY);
		
		int returnVal = chooser.showDialog(
				getOwner(), "OK");
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			dir = chooser.getSelectedFile().toPath();
			dirField.setText(dir.toString());
			saveButton.setEnabled(true);
		}
	}
	
	private void addComponents()
	{
		Container container = getContentPane();
		
		container.setLayout(new GridBagLayout());
		
		GridBagConstraints c = 
				new GridBagConstraints();

		c.anchor = GridBagConstraints.LINE_START;
		
		c.insets.set(5, 5, 5, 5);
		
		c.gridx = 0; c.gridy = 0;
		container.add(nameLabel, c);
		
		c.gridx = 1; c.gridy = 0;
		container.add(nameField, c);
		
		c.gridx = 0; c.gridy = 1;
		container.add(dirLabel, c);
		
		c.gridx = 1; c.gridy = 1;
		container.add(dirField, c);
		
		c.gridx = 2; c.gridy = 1;
		container.add(browseButton, c);
		
		c.gridx = 0; c.gridy = 2;
		container.add(saveButton, c);
		
		c.gridx = 1; c.gridy = 2;
		container.add(cancelButton, c);
		
	}
	
	private Path buildPath()
	{
		String fileName = 
				dir.toString() + "\\" +
				nameField.getText() +
				".scn";
				
		return Paths.get(fileName);
	}
	
	private boolean createPath(Path path)
	{
		try {
			Files.createFile(path);								
			return true;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					getParent(), e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean saveToPath(Path path)
	{
		try(ObjectOutputStream outStream =
				new ObjectOutputStream(
				new FileOutputStream(
						path.toString())))
		{
			outStream.writeObject(resources.getScene());
			return true;
		} catch (IOException e) 
		{
			JOptionPane.showMessageDialog(
					getParent(), e.getMessage());
			e.printStackTrace();
			return false;
		} 
	}
	
	private void deleteCreatedPath(Path path)
	{
		try {
			Files.delete(path);
		} catch(IOException e) {
			JOptionPane.showMessageDialog(
					getParent(), e.getMessage());
		}
	}
}
