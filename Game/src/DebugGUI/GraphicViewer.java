package DebugGUI;

import java.util.function.Supplier;

import Graphic.Graphic;
import Graphic.LayeredGraphic;

public class GraphicViewer extends Viewer
{
	public GraphicViewer(Supplier<? extends Graphic> supplier)
	{
		model.addFieldRecord("location.x", () -> supplier.get().getLoc().x + "");
		model.addFieldRecord("location.y", () -> supplier.get().getLoc().y + "");
		model.addFieldRecord("bound.x", () -> supplier.get().getBound() == null?
											  null:supplier.get().getBound().y + "");
		model.addFieldRecord("bound.y", () -> supplier.get().getBound() == null?
				  null:supplier.get().getBound().y + "");
		model.addFieldRecord("bound.width", () -> supplier.get().getBound() == null?
				  null:supplier.get().getBound().width + "");
		model.addFieldRecord("bound.height", () -> supplier.get().getBound() == null?
				  null:supplier.get().getBound().height + "");
	}
	
	public static GraphicViewer viewerOf(Supplier<Graphic> supplier)
	{
		Class<? extends Graphic> clas = supplier.get().getClass();
		
		if(clas.equals(LayeredGraphic.class))
			return new LayeredGraphicViewer(() -> (LayeredGraphic)supplier.get());
		else 
			return null;
			
	}
}
