package DebugGUI;

import java.util.function.Supplier;

import javax.swing.JLabel;

import Graphic.LayeredGraphic;

public class LayeredGraphicViewer extends GraphicViewer 
{
	public LayeredGraphicViewer(Supplier<LayeredGraphic> supplier) 
	{
		super(supplier);
	
		
		
		for(int i = 0; i < supplier.get().getGraphicLayerRecords().size(); i++)
		{
			JLabel label = new JLabel(
					supplier.get()
							.getGraphicLayerRecords()
							.get(i).graphic
							.getClass().getSimpleName());
			
			final int j = i;
			
			add(new GraphicViewer(() ->
						supplier.get()
						.getGraphicLayerRecords()
						.get(j).graphic));
		}
		
	}

}
