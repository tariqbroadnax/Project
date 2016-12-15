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
import javax.swing.event.ChangeEvent;

public class StrokeEditor extends JPanel
	implements ChangeNotifier,
			   ChangeListener,
			   javax.swing.event.ChangeListener,
			   ActionListener
{
	private JLabel widthLabel, capLabel,
				   joinLabel,
				   miterLimitLabel,
				   dashLabel;
	
	private Slider.Float widthSlider, miterLimitSlider,
					dashSlider;
	
	private NumberTextField.Float widthField,
								  miterLimitField,
								  dashField;
	
	private SliderFieldConnection.Float widthConn,
										miterLimitConn,
										dashConn;

	private JComboBox<Cap> capBox;
	private JComboBox<Join> joinBox;
	
	private Collection<ChangeListener> listeners;
	
	private boolean setting = false;
	
	private enum Cap
	{
		Butt, Round, Square
	}
	
	private enum Join
	{
		Bevel, Miter, Round
	}

	public StrokeEditor()
	{
		this(new BasicStroke(1.0f));
	}
	
	public StrokeEditor(BasicStroke stroke)
	{
		listeners = new LinkedList<ChangeListener>();
		
		widthLabel = new JLabel("Width: ");
		capLabel = new JLabel("Cap: ");
		joinLabel = new JLabel("Join: ");
		miterLimitLabel = new JLabel("Miter Limit: ");
		dashLabel = new JLabel("Dash: ");
		
		widthSlider = new Slider.Float(1, 50, 1);
		miterLimitSlider = new Slider.Float(0, 100, 50);
		dashSlider = new Slider.Float(0, 50, 0);
		
		widthField = new NumberTextField.Float(
				1, 50, 1);
		miterLimitField = new NumberTextField.Float(
				0, 100, 50);
		dashField = new NumberTextField.Float(
				0, 50, 0);	
		
		widthConn = new SliderFieldConnection.Float(
							widthSlider, widthField);
		miterLimitConn = new SliderFieldConnection.Float(
							miterLimitSlider,
							miterLimitField);
		dashConn = new SliderFieldConnection.Float(
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
					e -> notifyListeners());
		
		NumberTextField.Float[] fields = {
				widthField, miterLimitField,
				dashField};
				
		for(NumberTextField.Float field : fields)
			field.addChangeListener(this);
		
		capBox.addActionListener(this);
		joinBox.addActionListener(this);
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
		
		BasicStroke stroke;
		
		if(dash[0] == 0)
			stroke = new BasicStroke(
					width, cap, join, miterLimit);
		else
			stroke = new BasicStroke(
					width, cap, join, miterLimit,
					dash, dashPhase);
		
		//System.out.println("here " + width);

		return stroke;
	}
		
	public void setStroke(BasicStroke stroke)
	{
		setting = true;
		
		float width = stroke.getLineWidth();
		int cap = stroke.getEndCap();
		int join = stroke.getLineJoin();
		float miterLimit = stroke.getMiterLimit();
		float dash = stroke.getDashArray() == null ?
				0 : stroke.getDashArray()[0];

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
		
		widthSlider.setFloatValue(width);
		miterLimitSlider.setFloatValue(miterLimit);
		dashSlider.setFloatValue(dash);
	
		setting = false;
	}

	@Override
	public Collection<ChangeListener> getChangeListeners() {
		return listeners;
	}

	@Override
	public void fieldChanged() {
		if(!setting)
			notifyListeners();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!setting)
			notifyListeners();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(!setting)
			notifyListeners();
	}
}
