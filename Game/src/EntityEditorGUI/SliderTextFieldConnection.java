package EntityEditorGUI;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

import EditorGUI.SimpleDocumentListener;

public class SliderTextFieldConnection 
{
	private JSlider slider;
	
	private IntTextField field;
	
	private ChangeListener sliderList;
	
	private SimpleDocumentListener fieldList;
	
	private boolean syncing;
	
	public SliderTextFieldConnection(
		JSlider slider, IntTextField field)
	{
		this.slider = slider;
		this.field = field;
		
		sliderList = e -> syncField();
	
		fieldList = e -> syncSlider();
			
		slider.addChangeListener(sliderList);
		
		field.getDocument()
			 .addDocumentListener(fieldList);
	}
	
	private void syncField()
	{
		if(syncing)
		{
			syncing = false;
			
			int sliderVal = slider.getValue();
			
			field.setIntValue(sliderVal);
		}
		else
			syncing = true;

	}
	private void syncSlider()
	{
		if(syncing)
		{
			syncing = false;
			
			int fieldVal = field.getIntValue();
			
			slider.setValue(fieldVal);
		}
		else
			syncing = true;
	}
	
	public void dispose()
	{
		slider.removeChangeListener(sliderList);
		field.getDocument()
		     .removeDocumentListener(fieldList);
	}
}
