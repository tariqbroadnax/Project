package Editor.tile;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import Editor.comp.FileField;
import Editor.comp.Form;
import Tileset.Tileset;

public class OpenTilesetDialog extends JDialog
{
	private FileField fileFld;
	
	private JSpinner rowsSpinner, colsSpinner;
	
	private JButton openBtn, closeBtn;
			
	private JList<Tileset> list;
	
	public OpenTilesetDialog(JList<Tileset> list) 
	{
		this.list = list;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		
		JLabel fileLbl = new JLabel("Source"),
			   rowsLbl = new JLabel("Rows"),
			   colsLbl = new JLabel("Cols");
		
		fileFld = new FileField();
	
		rowsSpinner = new JSpinner();
		colsSpinner = new JSpinner();
		
		openBtn = new JButton("Open");
		closeBtn = new JButton("Close");
		
		SpinnerNumberModel rowsModel = new SpinnerNumberModel(1, 1, 40, 1),
						   colsModel = new SpinnerNumberModel(1, 1, 40, 1);

		rowsSpinner.setModel(rowsModel);
		colsSpinner.setModel(colsModel);
		
		FileNameExtensionFilter filter = 
				new FileNameExtensionFilter("img", "jpg", "jpeg", "png");

		fileFld.setFileFilter(filter);

		Form form = new Form();
		
		form.addComponent(fileLbl, 0, 0, 1);
		form.addField(fileFld, 1, 0, 1);
		form.addComponent(rowsLbl, 0, 1, 1);
		form.addComponent(rowsSpinner, 1, 1, 1);
		form.addComponent(colsLbl, 0, 2, 1);
		form.addComponent(colsSpinner, 1, 2, 1);
		
		JPanel btnPnl = new JPanel();
		
		btnPnl.add(openBtn); btnPnl.add(closeBtn);
		
		setLayout(new BorderLayout());
		
		add(form, BorderLayout.CENTER);
		add(btnPnl, BorderLayout.SOUTH);
		
		setSize(new Dimension(400, 200));
		
		setTitle("Tileset");
	
		openBtn.addActionListener(e -> openTileset());
		closeBtn.addActionListener(e -> close());
	
		fileFld.addValueListener(() -> openBtn.setEnabled(true));
	}
	
	private void openTileset()
	{
		File file = fileFld.getFile();
		
		int rows = (int) rowsSpinner.getValue(),
			cols = (int) colsSpinner.getValue();
	
		Tileset ts = new Tileset(file, rows, cols);
		
		TSModel model = (TSModel) list.getModel();
		
		model.addTileset(ts);
		
		list.setSelectedValue(ts, true);
	
		
		setVisible(false);
	}
	
	private void close()
	{
		setVisible(false);
	}
}
