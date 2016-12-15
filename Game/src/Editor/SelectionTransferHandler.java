package Editor;

import java.awt.geom.Point2D;

import Graphic.GraphicsContext;

public abstract class SelectionTransferHandler 
{
	public SelectionTransferHandler(
			EditorResources resources){}
	
	public abstract boolean canImport(Object obj);
		
	public abstract void setSelection(Object obj);
	
	public abstract void importSelection(
			Point2D.Double normLoc);

	public abstract void paintSelection(
			GraphicsContext gc,
			Point2D.Double normLoc);
}
