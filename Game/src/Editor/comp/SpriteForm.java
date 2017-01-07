package Editor.comp;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class SpriteForm extends Form
{
	public SpriteForm()
	{
		JLabel filenameLbl = new JLabel("Filename"),
			   tiledLbl = new JLabel("Tiled");
		
		JButton browseBtn = new JButton("Browse");
		
		JTextField filenameFld = new JTextField();
	
		JCheckBox tiledBox = new JCheckBox();

		DimForm sizeForm = new DimForm();
		CellForm cellForm = new CellForm();		
		
		TitledBorder sizeBorder = BorderFactory.createTitledBorder("Size"),
				     cellBorder = BorderFactory.createTitledBorder("Cell");

		sizeForm.setBorder(sizeBorder);
		cellForm.setBorder(cellBorder);
		
		addComponent(filenameLbl, 0, 0, 1);
		addField(filenameFld, 1, 0, 1);
		addComponent(browseBtn, 2, 0, 1);
		addField(sizeForm, 0, 2, 3);
		addComponent(tiledLbl, 0, 3, 1);
		addComponent(tiledBox, 1, 3, 1);
		addField(cellForm, 0, 4, 3);
	}
	
}
