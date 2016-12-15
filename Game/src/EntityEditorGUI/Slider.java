package EntityEditorGUI;

import javax.swing.JSlider;

public class Slider extends JSlider 
{		
	public Slider(int floor, int ceil, int val) {
		super(floor,ceil,val);
	}

	public static class Float extends Slider
	{
		private float min, max;
		
		public Float(float min, float max, float val)
		{
			super((int)Math.floor(min),
				  (int)Math.ceil(max),
				  (int)val);
		
			if(min > max)
				throw new IllegalArgumentException(
						"min is greater than max");
			
			this.min = min;
			this.max = max;			
		}
		
		public void setFloatValue(float val) 
		{
			//System.out.printf("%f %f %f\n", min, max, val);
			
			if(!(min <= val && val <= max))
				throw new IllegalArgumentException(
						"value is not inside range");
			
			int intVal = (int)val;
			
			setValue(intVal);
		}
		
		public float getFloatValue() {
			
			int value = getValue();
			
			return value;
		}
	}
}
