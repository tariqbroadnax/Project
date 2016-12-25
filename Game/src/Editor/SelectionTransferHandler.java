package Editor;

import EditorGUI.MouseListener;
import EditorGUI.MouseMotionListener;
import Graphic.GraphicsContext;

public abstract class SelectionTransferHandler 
	implements MouseListener, MouseMotionListener
{
	public SelectionTransferHandler(){}
	
	public abstract boolean setSelection(Object obj);

	public abstract void paintSelection(GraphicsContext gc);
}
