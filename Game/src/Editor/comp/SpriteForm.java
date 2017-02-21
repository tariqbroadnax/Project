package Editor.comp;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Graphic.Sprite;
import Maths.Dimension2D;

public class SpriteForm extends Form
	implements ValueListener, ActionListener
{
	private Sprite sprite;
	
	private FileField fileFld;
	
	private Dimension2DForm sizeForm;
	
	private JCheckBox tiledBox;
	
	private RectangleForm rectForm;
	
	public SpriteForm()
	{
		this(new Sprite());
	}
	
	public SpriteForm(Sprite sprite)
	{
		JLabel fileLbl = new JLabel("File"),
			   tiledLbl = new JLabel("Subimage?");
		
		fileFld = new FileField();
		
		sizeForm = new Dimension2DForm();
		
		tiledBox = new JCheckBox();
		
		sizeForm = new Dimension2DForm();

		rectForm = new RectangleForm();
				
		Border sizeBorder = BorderFactory.createTitledBorder("Size"),
			   rectBorder = BorderFactory.createLineBorder(Color.black);

		sizeForm.setBorder(sizeBorder);
		rectForm.setBorder(rectBorder);
		
		addComponent(fileLbl, 0, 0, 1);
		addField(fileFld, 1, 0, 2);
		addField(sizeForm, 0, 1, 3);
		addComponent(tiledLbl, 0, 2, 2);
		addComponent(tiledBox, 2, 2, 1);
		addField(rectForm, 0, 3, 3);
		
		fileFld.addValueListener(this);
		sizeForm.addValueListener(this);
		tiledBox.addActionListener(this);
		rectForm.addValueListener(this);
	
		FileFilter imageFilter = new FileNameExtensionFilter(
				"Image files", ImageIO.getReaderFileSuffixes());
		
		fileFld.setFileFilter(imageFilter);
		
		setSprite(sprite);
	}
	
	public void updateFields()
	{
		File file = sprite.getFile();
		
		Dimension2D.Double size = sprite.getSize();
		
		Rectangle tileBound = sprite.getTileBound();
		
		boolean tiled = tileBound != null;

		fileFld.setFile(file);
		
		sizeForm.setValue(size.width, size.height);
		
		tiledBox.setSelected(tiled);
		
		if(tiled)
		{
			rectForm.setRectangle(tileBound);
			rectForm.setVisible(true);
		}
		else
			rectForm.setVisible(false);
	}
	
	private void updateValues()
	{
		File file = fileFld.getFile();
		
		Dimension2D.Double size = sizeForm.getDimValue();
		
		boolean tiled = tiledBox.isSelected();
		
		Rectangle tileBound;
		if(tiled)
		{
			tileBound = rectForm.getRectangle();
			rectForm.setVisible(true);
		}
		else
		{
			tileBound = null;
			rectForm.setVisible(false);
		}
		
		sprite.setFile(file);
		sprite.setSize(size.width, size.height);
		sprite.setTileBound(tileBound);
	}

	@Override
	public void valueChanged() 
	{
		updateValues();
		notifyListeners();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		updateValues();
		notifyListeners();
	}

	public void setSprite(Sprite sprite)
	{
		this.sprite = sprite;
		
		updateFields();
	}
	
	public Sprite getSprite() {
		return new Sprite(sprite);
	}
}
