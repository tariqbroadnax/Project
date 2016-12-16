package Editor;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NONE;
import static java.awt.GridBagConstraints.WEST;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Dialog.ModalityType;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;

import EditorGUI.SimpleDocumentListener;
import Tileset.TileMap;

public class TileMapDialog extends JDialog
	implements SimpleDocumentListener
{
	private JPanel fieldPanel,
				   btnPanel;
	
	private JLabel xLabel, yLabel,
				   rowsLabel, colsLabel,
				   twidthLabel, theightLabel;
	
	private DoubleField xField, yField,
					    theightField, twidthField;
	
	private IntField rowsField, colsField;
	
	private JButton insertBtn, closeBtn;
	
	private EditorResources resources;
	
	public TileMapDialog(EditorResources resources)
	{		
		this.resources = resources;
				
		xLabel = new JLabel("x:");
		yLabel = new JLabel("y:");
		rowsLabel = new JLabel("Rows: ");
		colsLabel = new JLabel("Columns: ");
		twidthLabel = new JLabel("Tile Width: ");
		theightLabel = new JLabel("Tile Height: ");

		xField = new DoubleField();
		yField = new DoubleField();
		rowsField = new IntField(1, 200, 10);
		colsField = new IntField(1, 200, 10);
		theightField = new DoubleField(10);
		twidthField = new DoubleField(10);
		
		insertBtn = new JButton("Insert");
		closeBtn = new JButton("Close");
		
		fieldPanel = new JPanel();
		btnPanel = new JPanel();
		
		insertBtn.addActionListener(e -> insertTileMap());
		closeBtn.addActionListener(e -> setVisible(false));
		
		addToFieldPanel();
		addToBtnPanel();
		addPanels();
		
		addDocumentListeners();
		
		setTitle("Tile Map");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(350, 200);
	}
	
	public void reset()
	{
		xField.setValue(0);
		yField.setValue(0);
		
		rowsField.setValue(10);
		colsField.setValue(10);

		twidthField.setValue(10);
		theightField.setValue(10);
	}
	
	private void addToFieldPanel()
	{
		fieldPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
	
		gc.insets.set(5, 5, 5, 5);
		
		addLabel(xLabel, 0, 0, gc);
		addField(xField, 1, 0, gc);
		addLabel(yLabel, 2, 0, gc);
		addField(yField, 3, 0, gc);
		
		addLabel(rowsLabel, 0, 1, gc);
		addField(rowsField, 1, 1, gc);
		addLabel(colsLabel, 2, 1, gc);
		addField(colsField, 3, 1, gc);
		
		addLabel(twidthLabel, 0, 2, gc);
		addField(twidthField, 1, 2, gc);
		addLabel(theightLabel, 2, 2, gc);
		addField(theightField, 3, 2, gc);
	}
	
	private void insertTileMap()
	{
		double x = xField.getValue(),
			   y = yField.getValue();
		
		int rows = rowsField.getValue(),
			cols = colsField.getValue();
		
		double twidth = twidthField.getValue(),
			   theight = theightField.getValue();
		
		TileMap tm = new TileMap(rows, cols);

		tm.setLoc(x, y);
		tm.setTileSize(twidth, theight);
		
		resources.scene.addGraphic(tm);
		resources.notifyOfSceneChange();
		
		setVisible(false);
	}
	
	private void addLabel(JLabel label, int gridx, int gridy, GridBagConstraints gc)
	{
		gc.gridx = gridx;
		gc.gridy = gridy;
		gc.fill = NONE;
		gc.anchor = WEST;
		gc.weightx = 0;
		
		fieldPanel.add(label, gc);
	}

	private void addField(JTextField field, int gridx, int gridy, GridBagConstraints gc)
	{
		gc.gridx = gridx;
		gc.gridy = gridy;
		gc.fill = HORIZONTAL;
		//gc.anchor = WEST;
		gc.weightx = .5;
		
		fieldPanel.add(field, gc);
	}

	private void addToBtnPanel()
	{
		btnPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));

		btnPanel.add(insertBtn);
		btnPanel.add(closeBtn);
	}
	
	private void addPanels()
	{
		setLayout(new BorderLayout());
		
		add(fieldPanel, BorderLayout.NORTH);
		add(btnPanel, BorderLayout.SOUTH);	
	}

	private void addDocumentListeners()
	{
		JTextField fields[] = {
				xField, yField,
				rowsField, colsField,
				twidthField, theightField
		};
		
		for(JTextField field : fields)
		{
			Document doc = field.getDocument();
			doc.addDocumentListener(this);
		}
	}
	@Override
	public void documentChanged(DocumentEvent e) 
	{
		if(xField.validText() && yField.validText() &&
		   rowsField.validText() && colsField.validText() &&
		   twidthField.validText() && theightField.validText())
		{
			insertBtn.setEnabled(true);
		}
		else
			insertBtn.setEnabled(false);
	}
}
