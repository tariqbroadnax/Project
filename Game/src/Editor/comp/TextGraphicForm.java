package Editor.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.Border;

import Graphic.TextGraphic;

public class TextGraphicForm extends Form
	implements ValueListener, ActionListener
{
	private TextField txtFld;
	
	private Dimension2DForm charSizeForm;
	
	private ColorField colorFld;
	
	private JComboBox<String> fontNameBox;
	
	private JCheckBox boldBox, italicBox;
	
	private TextGraphic graph;

	public TextGraphicForm()
	{
		this(new TextGraphic());
	}
	
	public TextGraphicForm(TextGraphic graph)
	{
		JLabel txtLbl = new JLabel("Text"),
			   colorLbl = new JLabel("Color"),
			   fontNameLbl = new JLabel("Font Name"),
			   boldLbl = new JLabel("Bold"),
			   italicLbl = new JLabel("Italic");
		
		Border charSizeBorder = 
				BorderFactory.createTitledBorder("Character Size");
		
		txtFld = new TextField();
		charSizeForm = new Dimension2DForm();
		colorFld = new ColorField();
		
		String[] fontNames = {"CONSOLAS", "COURIER", "ARIAL"};
		
		fontNameBox = new JComboBox<String>(fontNames);
		
		boldBox = new JCheckBox();
		italicBox = new JCheckBox();
		
		charSizeForm.setBorder(charSizeBorder);
		
		addComponent(txtLbl, 0, 0, 1);
		addField(txtFld, 1, 0, 1);
		addField(charSizeForm, 0, 1, 2);
		addComponent(colorLbl, 0, 2, 1);
		addField(colorFld, 1, 2, 1);
		addComponent(fontNameLbl, 0, 3, 1);
		addComponent(fontNameBox, 1, 3, 1);
		addComponent(boldLbl, 0, 4, 1);
		addComponent(boldBox, 1, 4, 1);
		addComponent(italicLbl, 0, 5, 1);
		addComponent(italicBox, 1, 5, 1);
		
		txtFld.addValueListener(this);
		charSizeForm.addValueListener(this);
		colorFld.addValueListener(this);
		fontNameBox.addActionListener(this);
		boldBox.addActionListener(this);
		italicBox.addActionListener(this);
		
		setTextGraphic(graph);
	}
	
	public void setTextGraphic(TextGraphic graph)
	{
		this.graph = graph;
			
		updateFields();
	}
	
	public TextGraphic getTextGraphic() {
		return graph;
	}	
	
	private void updateValues()
	{				
		String text = txtFld.getText();
		
		double charWidth = charSizeForm.getWidthValue(),
			   charHeight = charSizeForm.getHeightValue();
		
		Color color = colorFld.getValue();
		
		String fontName = (String) fontNameBox.getSelectedItem();
		
		boolean bold = boldBox.isSelected(),
				italic = italicBox.isSelected();
		
		int style;
		if(bold && italic)
			style = Font.BOLD | Font.ITALIC;
		else if(bold)
			style = Font.BOLD;
		else if(italic)
			style = Font.ITALIC;
		else
			style = Font.PLAIN;
	
		graph.setText(text);
		graph.setCharWidth(charWidth);
		graph.setCharHeight(charHeight);
		graph.setPaint(color);
		graph.setFontName(fontName);
		graph.setFontStyle(style);
	}
	
	public void updateFields()
	{		
		fontNameBox.removeActionListener(this);
		
		String text = graph.getText();
		
		double charHeight = graph.getCharHeight(),
			   charWidth = graph.getCharWidth();
	
		Color color = (Color) graph.getPaint();
		
		String fontName = graph.getFontName();
		
		int style = graph.getFontStyle();
		
		txtFld.setText(text);
		
		charSizeForm.setValue(charWidth, charHeight);
		
		colorFld.setValue(color);
		
		fontNameBox.setSelectedItem(fontName);
		
		if((style & Font.BOLD) > 0)
			boldBox.setSelected(true);
		
		if((style & Font.ITALIC) > 0)
			italicBox.setSelected(true);
	
		fontNameBox.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		updateValues();
		notifyListeners();
	}

	@Override
	public void valueChanged() {
		updateValues();
		notifyListeners();
	}
}
