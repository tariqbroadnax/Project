package Editor.comp;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JToggleButton;

import Editor.DoubleField;

public class MovementEditor extends Form
{
	private JLabel speedLbl, dirLbl;
	
	private DoubleField speedFld, dirFld;
	
	private JToggleButton enabledBtn;
	
	public MovementEditor()
	{
		setBackground(Color.pink);
		
		speedLbl = new JLabel("Speed: ");
		dirLbl = new JLabel("Direction: ");
		
		speedFld = new DoubleField();
		dirFld = new DoubleField();
		
		enabledBtn = new JToggleButton("Enabled");
	
		addLabel(speedLbl, 0, 0);
		addField(speedFld, 1, 0);
		addLabel(dirLbl, 0, 1);
		addField(dirFld, 1, 1);
		addButton(enabledBtn, 0, 2);
		
		enabledBtn.addActionListener(e -> updateBtnText());
	}
	
	private void updateBtnText()
	{
		if(!enabledBtn.isSelected())
			enabledBtn.setText("Enabled");
		else
			enabledBtn.setText("Disabled");
	}
}