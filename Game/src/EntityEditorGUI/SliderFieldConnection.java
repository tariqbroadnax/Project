package EntityEditorGUI;

import javax.swing.event.ChangeEvent;

public abstract class SliderFieldConnection
	implements javax.swing.event.ChangeListener,
			   ChangeListener
{
	protected boolean syncing = true;
	
	public static class Float 
		extends SliderFieldConnection
	{
		Slider.Float slider;
		NumberTextField.Float field;
		
		public Float(
				Slider.Float slider,
				NumberTextField.Float field) 
		{
			this.slider = slider;
			this.field = field;
		
			slider.addChangeListener(this);
			field.addChangeListener(this);
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			if(syncing)
			{
				syncing = false;
				
				float sliderVal = slider.getFloatValue();
				
				field.setFloatValue(sliderVal);	
				
				//System.out.println(slider.getFloatValue());
			}
			else
				syncing = true;
		}

		@Override
		public void fieldChanged() 
		{
			if(syncing)
			{
				syncing = false;
				
				float fieldVal = field.getFloatValue();
				
				slider.setFloatValue(fieldVal);					
			}
			else
				syncing = true;
		}
	}
}
