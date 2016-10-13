package EntityEditorGUI;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.LINE_END;
import static java.awt.GridBagConstraints.LINE_START;

import java.awt.BasicStroke;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import EditorGUI.SimpleDocumentListener;

public class StrokeEditor extends JPanel
{
	private JLabel widthLabel, capLabel,
				   joinLabel,
				   miterLimitLabel,
				   dashLabel;
	
	private JSlider widthSlider, miterLimitSlider,
					dashSlider;
	
	private IntTextField widthField, miterLimitField,
					 	 dashField;
	
	private SliderTextFieldConnection widthConn, miterLimitConn,
									  dashConn;

	private JComboBox<Cap> capBox;
	private JComboBox<Join> joinBox;
	
	private Collection<ActionListener> listeners;
	
	private enum Cap
	{
		Butt, Round, Square
	}
	
	private enum Join
	{
		Bevel, Miter, Round
	}
	
	private BasicStroke stroke;

	public StrokeEditor()
	{
		this(new BasicStroke(1.0f));
	}
	
	public StrokeEditor(BasicStroke stroke)
	{
		listeners = new LinkedList<ActionListener>();
		
		widthLabel = new JLabel("Width: ");
		capLabel = new JLabel("Cap: ");
		joinLabel = new JLabel("Join: ");
		miterLimitLabel = new JLabel("Miter Limit: ");
		dashLabel = new JLabel("Dash: ");
		
		widthSlider = new JSlider(0, 50, 1);
		miterLimitSlider = new JSlider(1, 100, 50);
		dashSlider = new JSlider(0, 50, 0);
		
		widthField = new IntTextField(0, 50, 1);
		miterLimitField = new IntTextField(1, 100, 50);
		dashField = new IntTextField(0, 50, 0);	
		
		widthConn = new SliderTextFieldConnection(
							widthSlider, widthField);
		miterLimitConn = new SliderTextFieldConnection(
							miterLimitSlider,
							miterLimitField);
		dashConn = new SliderTextFieldConnection(
							dashSlider, dashField);
		
		Cap[] caps = Cap.values();
		Join[] joins = Join.values();
	
		capBox = new JComboBox<Cap>(caps);		
		joinBox = new JComboBox<Join>(joins);
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = 
				new GridBagConstraints();
		
		c.fill = HORIZONTAL;
		
		c.insets.set(5, 5, 5, 5);
		
		addComponents(c);
	
		setStroke(stroke);
		
		addListeners();
	}
	
	private void addListeners()
	{
		JSlider[] sliders = {
				widthSlider, miterLimitSlider,
				dashSlider};
		
		for(JSlider slider : sliders)
			slider.addChangeListener(
					e -> updateStroke());
		
		JTextField[] fields = {
				widthField, miterLimitField,
				dashField};
		
		SimpleDocumentListener l =
				e -> updateStroke();
				
		for(JTextField field : fields)
			field.getDocument()
				 .addDocumentListener(l);
		
		capBox.addActionListener(
				e -> updateStroke());
		joinBox.addActionListener(
				e -> updateStroke());
	}
	
	private void addComponents(GridBagConstraints c)
	{
		c.weightx = 0;
		c.anchor = LINE_END;
		c.gridx = 0; c.gridy = 0;
		add(widthLabel, c);
		
		c.weightx = 0;
		c.anchor = LINE_START;
		c.gridx = 1; c.gridy = 0;
		add(widthSlider, c);
		
		c.weightx = 1;
		c.anchor = LINE_START;
		c.gridx = 2; c.gridy = 0;
		add(widthField, c);
		
		c.weightx = 0;
		c.anchor = LINE_END;
		c.gridx = 0; c.gridy = 1;
		add(capLabel, c);
		
		c.weightx = 1;
		c.anchor = LINE_START;
		c.gridwidth = 1;
		c.gridx = 1; c.gridy = 1;
		add(capBox, c);
		
		c.weightx = 0;
		c.anchor = LINE_END;
		c.gridwidth = 1;
		c.gridx = 0; c.gridy = 2;
		add(joinLabel, c);
		
		c.weightx = 1;
		c.anchor = LINE_START;
		c.gridwidth = 1;
		c.gridx = 1; c.gridy = 2;
		add(joinBox, c);
		
		c.weightx = 0;
		c.anchor = LINE_END;
		c.gridwidth = 1;
		c.gridx = 0; c.gridy = 3;
		add(miterLimitLabel, c);
		
		c.weightx = 1;
		c.anchor = LINE_START;
		c.gridx = 1; c.gridy = 3;
		add(miterLimitSlider, c);
		
		c.weightx = 1;
		c.anchor = LINE_START;
		c.gridx = 2; c.gridy = 3;
		add(miterLimitField, c);

		c.weightx = 0;
		c.anchor = LINE_END;
		c.gridwidth = 1;
		c.gridx = 0; c.gridy = 4;
		add(dashLabel, c);
		
		c.weightx = 1;
		c.anchor = LINE_START;
		c.gridx = 1; c.gridy = 4;
		add(dashSlider, c);
		
		c.weightx = 1;
		c.anchor = LINE_START;
		c.gridx = 2; c.gridy = 4;
		add(dashField, c);
	}

	public BasicStroke getStroke() 
	{
		return stroke;
	}
	
	private void updateStroke()
	{
		float width = widthSlider.getValue();
		
		Cap capObj = (Cap)capBox.getSelectedItem();
		int cap = 0;
		
		switch(capObj)
		{
			case Butt:
				cap = BasicStroke.CAP_BUTT;
				break;
			case Round:
				cap = BasicStroke.CAP_ROUND;
				break;
			case Square:
				cap = BasicStroke.CAP_SQUARE;
				break;
		}
		
		Join joinObj = (Join)joinBox.getSelectedItem();
		int join = 0;
		
		switch(joinObj)
		{
			case Bevel:
				join = BasicStroke.JOIN_BEVEL;
				break;
			case Miter:
				join = BasicStroke.JOIN_MITER;
				break;
			case Round:
				join = BasicStroke.JOIN_ROUND;
				break;
		}
		
		float miterLimit =
				miterLimitSlider.getValue();
		
		float[] dash = {dashSlider.getValue()};

		float dashPhase = 0;
		
		BasicStroke newStroke;
		
		if(dash[0] == 0)
			newStroke = new BasicStroke(
					width, cap, join, miterLimit);
		else
			newStroke = new BasicStroke(
					width, cap, join, miterLimit,
					dash, dashPhase);
		
		if(newStroke.equals(stroke))
			return;
		else
		{	
			stroke = newStroke;
			notifyListeners();
		}
	}
	
	private void notifyListeners()
	{
		int id = ActionEvent.ACTION_PERFORMED;
		String command = "STROKE CHANGED";
		
		ActionEvent e = new ActionEvent(
				this, id, command);
		
		for(ActionListener listener : listeners)
			listener.actionPerformed(e);
	}
	
	public void addActionListener(
			ActionListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeActionListener(
			ActionListener listener)
	{
		listeners.remove(listener);
	}
	
	public void setStroke(BasicStroke stroke)
	{
		int width = (int)stroke.getLineWidth();
		int cap = stroke.getEndCap();
		int join = stroke.getLineJoin();
		int miterLimit = (int)stroke.getMiterLimit();
		int dash = stroke.getDashArray() == null ?
				0 : (int)stroke.getDashArray()[0];

		Cap capObj = null;
		switch(cap)
		{
			case BasicStroke.CAP_BUTT:
				capObj = Cap.Butt;
				break;
			case BasicStroke.CAP_ROUND:
				capObj = Cap.Round;
				break;
			case BasicStroke.CAP_SQUARE:
				capObj = Cap.Square;
				break;
		}
		
		Join joinObj = null;
		switch(join)
		{
			case BasicStroke.JOIN_BEVEL:
				joinObj = Join.Bevel;
				break;
			case BasicStroke.JOIN_MITER:
				joinObj = Join.Bevel;
				break;
			case BasicStroke.JOIN_ROUND:
				joinObj = Join.Round;
				break;
		}
		
		capBox.setSelectedItem(capObj);
		joinBox.setSelectedItem(joinObj);
		
		widthSlider.setValue(width);
		miterLimitSlider.setValue(miterLimit);
		dashSlider.setValue(dash);

		widthField.setIntValue(width);
		miterLimitField.setIntValue(miterLimit);
		dashField.setIntValue(dash);
		
	}
}
