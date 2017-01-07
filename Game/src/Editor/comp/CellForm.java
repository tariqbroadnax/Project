package Editor.comp;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class CellForm extends Form
{
	public CellForm()
	{
		JLabel rowLbl = new JLabel("Row"),
			   colLbl = new JLabel("Column");

		SpinnerNumberModel rowModel = new SpinnerNumberModel(0, 0, 39, 1),
						   colModel = new SpinnerNumberModel(0, 0, 39, 1);
		
		JSpinner rowSpinner = new JSpinner(rowModel),
				 colSpinner = new JSpinner(colModel);
		
		addComponent(rowLbl, 0, 0, 1);
		addComponent(rowSpinner, 1, 0, 1);
		addComponent(colLbl, 0, 1, 1);
		addComponent(colSpinner, 1, 1, 1);
		
		JPanel fill = new JPanel();
		addField(fill, 2, 1, 2);
	}
}
