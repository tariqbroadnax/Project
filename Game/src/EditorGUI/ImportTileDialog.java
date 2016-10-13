package EditorGUI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import javax.swing.text.NumberFormatter;

public class ImportTileDialog extends JDialog
{
	private JLabel sourceLabel, colLabel, rowLabel;
	
	private JTextField sourceField;
	
	private JFormattedTextField colField, rowField;
	
	private JButton browseButton,
					importButton, cancelButton;
	
	private JScrollPane previewerScrollPane;
	private TileSetViewer previewer;
	
	private Path path;
	
	private GUIResources resources;
	
	public ImportTileDialog(GUIResources resources)
	{
		super(null, "Import Tile",
			  ModalityType.DOCUMENT_MODAL);
		
		this.resources = resources;
		
		sourceLabel = new JLabel("Source File: ");
		colLabel = new JLabel("Columns: ");
		rowLabel = new JLabel("Rows: ");
		
		sourceField = new JTextField(15);
		
		NumberFormatter formatter = createFormatter();
		
		colField = new JFormattedTextField(formatter);
		rowField = new JFormattedTextField(formatter);
				
		browseButton = new JButton("Browse");
		importButton = new JButton("Import");
		cancelButton = new JButton("Cancel");
	
		previewer = new TileSetViewer();
		previewerScrollPane = new JScrollPane(previewer);
		
		formatComponents();
		addComponents();
	
		sourceField.setEditable(false);
	}
	
	private void formatComponents()
	{
		addListeners();
		
		colField.setColumns(3); rowField.setColumns(3);

		previewerScrollPane.setPreferredSize(
				new Dimension(200, 200));
		
		setSize(400, 400);
	}
	
	private void addListeners()
	{
		browseButton.addActionListener(e -> browse());
		importButton.addActionListener(e -> importTiles());
		cancelButton.addActionListener(e -> dispose());
				
		addDocumentListeners(colField, rowField);
	}
	
	private void addDocumentListeners(JTextField... jtfs)
	{
		SimpleDocumentListener listener =
				e -> updateRowsAndCols();
			
		for(JTextField jtf : jtfs)
		{
			Document doc = jtf.getDocument();
			
			doc.addDocumentListener(listener);
		}
	}
	
	private void browse()
	{
		JFileChooser chooser = new JFileChooser();
		
		chooser.setFileSelectionMode(
				JFileChooser.FILES_AND_DIRECTORIES);
		
		FileNameExtensionFilter filter = 
				new FileNameExtensionFilter(
			        "JPG, PNG, & GIF Images", "jpg", "png", "gif");
		
		chooser.setFileFilter(filter);
		
		int returnVal = chooser.showDialog(this, "OK");
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = chooser.getSelectedFile();
			tryToLoadImage(selectedFile);
		}
	}
	
	private void tryToLoadImage(File file)
	{
		try {
			Path path = file.toPath();
			String fileName = path.toString();			
			BufferedImage img = ImageIO.read(file);
				
			this.path = path;
			sourceField.setText(fileName);
			previewer.setImage(img);
		} 
		catch(IOException e) 
		{
			String message = e.getMessage();
			JOptionPane.showMessageDialog(this, message);
		}
	}
	
	private void importTiles()
	{
		String fileName = path.toString();
		
		String colText = colField.getText(),
			   rowText = rowField.getText();
			
		int cols = Integer.parseInt(colText),
			rows = Integer.parseInt(rowText);
		
		try {
			resources.getImagePool()
					 .importImage(fileName, cols, rows);
			dispose();
		} catch (IOException e) 
		{
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}
	
	private void updateRowsAndCols()
	{
		String colText = colField.getText(),
			   rowText = rowField.getText();
		
		int cols, rows;
		
		try {
			cols = Integer.parseInt(colText);
		} catch(NumberFormatException e) {
			cols = 1;
		}
		
		try {
			rows = Integer.parseInt(rowText);
		} catch(NumberFormatException e) {
			rows = 1;
		}
		
		previewer.setRowsAndColumns(rows, cols);
	}
	
	private void addComponents()
	{
		Container container = getContentPane();
		
		container.setLayout(new GridBagLayout());
		
		GridBagConstraints c = 
				new GridBagConstraints();

		c.anchor = GridBagConstraints.LINE_END;

		c.insets.set(5, 5, 5, 5);
		
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 0; c.gridy = 0;
		container.add(sourceLabel, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridwidth = 3;
		c.gridx = 1; c.gridy = 0;
		container.add(sourceField, c);
		
		c.gridwidth = 1;
		c.gridx = 4; c.gridy = 0;
		container.add(browseButton, c);
		
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 0; c.gridy = 1;
		container.add(colLabel, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 1; c.gridy = 1;
		container.add(colField, c);
		
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 2; c.gridy = 1;
		container.add(rowLabel, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 3; c.gridy = 1;
		container.add(rowField, c);
		
		c.gridx = 1; c.gridy = 2;
		c.gridwidth = 3;
		container.add(previewerScrollPane, c);
		
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 1; c.gridy = 3;
		container.add(importButton, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 2; c.gridy = 3;
		container.add(cancelButton, c);
	}
	
	private NumberFormatter createFormatter()
	{
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(true);

		return formatter;
	}
				   
}
