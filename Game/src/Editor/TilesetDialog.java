package Editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TilesetDialog extends JDialog
	implements DocumentListener, WindowListener
{
	private JLabel sourceLabel;
	private JButton browseBtn;
	private JLabel rowsLbl;
	private JSpinner rowsSpinner;
	private JLabel colsLbl;
	private JSpinner colsSpinner;
	private JButton openBtn;
	private JButton closeBtn;
	private JTextField sourceField;

	private JFileChooser fileChooser;
	
	private boolean validSource;
	
	public TilesetDialog() 
	{
		setModalityType(ModalityType.APPLICATION_MODAL);
		
		setSize(new Dimension(418, 175));
		setTitle("Tileset");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{26, 39, 65, 59, 64, 67, 0};
		gridBagLayout.rowHeights = new int[]{23, 20, 31, 23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		sourceLabel = new JLabel("Source");
		sourceLabel.setBackground(Color.WHITE);
		GridBagConstraints gbc_sourceLabel = new GridBagConstraints();
		gbc_sourceLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_sourceLabel.insets = new Insets(5, 5, 5, 5);
		gbc_sourceLabel.gridwidth = 2;
		gbc_sourceLabel.gridx = 0;
		gbc_sourceLabel.gridy = 0;
		getContentPane().add(sourceLabel, gbc_sourceLabel);
		
		sourceField = new JTextField();
		GridBagConstraints gbc_sourceField = new GridBagConstraints();
		gbc_sourceField.fill = GridBagConstraints.HORIZONTAL;
		gbc_sourceField.insets = new Insets(5, 5, 5, 5);
		gbc_sourceField.gridwidth = 3;
		gbc_sourceField.gridx = 2;
		gbc_sourceField.gridy = 0;
		getContentPane().add(sourceField, gbc_sourceField);
		sourceField.setColumns(10);
		
		sourceField.getDocument()
				   .addDocumentListener(this);
		
		browseBtn = new JButton("Browse");
		GridBagConstraints gbc_browseBtn = new GridBagConstraints();
		gbc_browseBtn.anchor = GridBagConstraints.NORTHWEST;
		gbc_browseBtn.insets = new Insets(5, 5, 5, 5);
		gbc_browseBtn.gridx = 5;
		gbc_browseBtn.gridy = 0;
		getContentPane().add(browseBtn, gbc_browseBtn);
		
		browseBtn.addActionListener(e -> browse());
		
		rowsLbl = new JLabel("Rows");
		GridBagConstraints gbc_rowsLbl = new GridBagConstraints();
		gbc_rowsLbl.anchor = GridBagConstraints.WEST;
		gbc_rowsLbl.insets = new Insets(5, 5, 5, 5);
		gbc_rowsLbl.gridx = 0;
		gbc_rowsLbl.gridy = 1;
		getContentPane().add(rowsLbl, gbc_rowsLbl);
		
		rowsSpinner = new JSpinner();
		GridBagConstraints gbc_rowsSpinner = new GridBagConstraints();
		gbc_rowsSpinner.insets = new Insets(5, 5, 5, 5);
		gbc_rowsSpinner.anchor = GridBagConstraints.NORTHWEST;
		gbc_rowsSpinner.gridx = 1;
		gbc_rowsSpinner.gridy = 1;
		getContentPane().add(rowsSpinner, gbc_rowsSpinner);
		
		SpinnerModel rowsModel = new SpinnerNumberModel(1, 1, 40, 1),
				 colsModel = new SpinnerNumberModel(1, 1, 40, 1);
	
		rowsSpinner.setModel(rowsModel);
		
		colsLbl = new JLabel("Columns");
		GridBagConstraints gbc_colsLbl = new GridBagConstraints();
		gbc_colsLbl.fill = GridBagConstraints.HORIZONTAL;
		gbc_colsLbl.insets = new Insets(5, 5, 5, 5);
		gbc_colsLbl.gridx = 3;
		gbc_colsLbl.gridy = 1;
		getContentPane().add(colsLbl, gbc_colsLbl);
		
		colsSpinner = new JSpinner();
		GridBagConstraints gbc_colsSpinner = new GridBagConstraints();
		gbc_colsSpinner.anchor = GridBagConstraints.NORTHWEST;
		gbc_colsSpinner.insets = new Insets(5, 5, 5, 5);
		gbc_colsSpinner.gridx = 4;
		gbc_colsSpinner.gridy = 1;
		getContentPane().add(colsSpinner, gbc_colsSpinner);
		colsSpinner.setModel(colsModel);
		
		openBtn = new JButton("Open");
		GridBagConstraints gbc_openBtn = new GridBagConstraints();
		gbc_openBtn.anchor = GridBagConstraints.NORTHWEST;
		gbc_openBtn.insets = new Insets(5, 5, 5, 5);
		gbc_openBtn.gridx = 2;
		gbc_openBtn.gridy = 3;
		getContentPane().add(openBtn, gbc_openBtn);
		openBtn.setEnabled(false);
	
		
		closeBtn = new JButton("Close");
		GridBagConstraints gbc_closeBtn = new GridBagConstraints();
		gbc_closeBtn.anchor = GridBagConstraints.NORTHWEST;
		gbc_closeBtn.insets = new Insets(5, 5, 5, 5);
		gbc_closeBtn.gridx = 3;
		gbc_closeBtn.gridy = 3;
		getContentPane().add(closeBtn, gbc_closeBtn);
		
		
		openBtn.addActionListener(e -> setVisible(false));
		closeBtn.addActionListener(e -> setVisible(false));
	
		fileChooser = new JFileChooser();
		
		FileNameExtensionFilter filter = 
				new FileNameExtensionFilter("img", "jpg", "jpeg", "png");

		fileChooser.setFileFilter(filter);
	
		validSource = false;
		
		addWindowListener(this);
	}
	
	private void browse()
	{
		int returnVal = fileChooser.showOpenDialog(this);
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			
			sourceField.setText(file.getAbsolutePath());
		}
	}
	
	public boolean validSource()
	{
		return validSource;
	}
	
	public File file() 
	{
		String fileName = sourceField.getText();
		File file = new File(fileName);
		
		return file;
	}
	
	public int rows() {
		return (int) rowsSpinner.getValue();
	}
	
	public int cols() {
		return (int) colsSpinner.getValue();
	}

	private void checkAndProcessSource()
	{
		String fileName = sourceField.getText();
		
		File file = new File(fileName);
		
		try {
			ImageIO.read(file);
			openBtn.setEnabled(true);
			sourceField.setForeground(Color.black);
			validSource = true;
		} catch(IOException ex) {
			openBtn.setEnabled(false);
			sourceField.setForeground(Color.red);
			if(isVisible())
			validSource = false;
		}
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) 
	{
		checkAndProcessSource();
	}

	@Override
	public void insertUpdate(DocumentEvent e) 
	{
		checkAndProcessSource();
	}

	@Override
	public void removeUpdate(DocumentEvent e) 
	{
		checkAndProcessSource();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		sourceField.setText("");	
		
	}

}
