package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Collection;
import java.util.function.Supplier;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Utilities.FontUtilities;

public class FieldLabel extends JPanel
{
	private Collection<Field> fields;
	private double nameLabelWeight;
	
	public FieldLabel()
	{
		fields = new LinkedList<Field>();
		
		nameLabelWeight = 0.5;
	}
	
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
	
		String name = nameLabel.getText(),
			   value = valueLabel.getText();
		
		Dimension nameLabelDim = new Dimension(
				(int)(nameLabelWeight * width),
				height),
				  valueLabelDim = new Dimension(
				(int)((1 - nameLabelWeight) * width),
				height);
		
		nameLabel.setSize(nameLabelDim);
		valueLabel.setSize(valueLabelDim);
		
		Font font = name.length() > value.length() ?
				FontUtilities.fontWithDimension(
						nameLabel.getFont(), nameLabelDim,
						name, this) :
				FontUtilities.fontWithDimension(
						valueLabel.getFont(), valueLabelDim,
						value, this);

		nameLabel.setFont(nameBal);
	}
	
	private void updateValue()
	{
	
	}
	
	private class Field
	{
		String name;
		
		Supplier<String> value;

	}

}
