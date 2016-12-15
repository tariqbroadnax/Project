package EditorGUI;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSource;
import java.awt.event.InputEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Graphic.Graphic;

public class EntityTransferHandler 
extends TransferHandler
{
	private Entity entity;
	
	private Dimension screenSize;
	
	private Image img;
	
	public EntityTransferHandler(
			Entity entity, Dimension screenSize)
	{
		this.entity = entity;
		this.screenSize = screenSize;
	
		Graphic graphic = 
				entity.get(GraphicsComponent.class)
				      .getGraphic();

		img = graphic.projectedImage(screenSize);
		
		
		setDragImage(img);
	}
	
	@Override
	public boolean canImport(JComponent comp, DataFlavor[] transferFlavors)
	{
		setDragImage(img);

		return true;
	}
	
	@Override
	public boolean importData(TransferSupport support)
	{
		setDragImage(img);
		
		return true;
	}
	
	@Override
	public void exportAsDrag(JComponent comp, InputEvent e, int action)
	{
		setDragImage(img);

		super.exportAsDrag(comp, e, action);
	}
	
	@Override
	public int getSourceActions(JComponent c) {
		  System.out.println("getSourceActions");

		setDragImage(img);
		System.out.println(DragSource.isDragImageSupported());
			
		return COPY_OR_MOVE;
	}
	
	public Image getDragImage()
	{
		return super.getDragImage();
	}
	
	@Override
	public Transferable createTransferable(
			JComponent c) 
	{
		setDragImage(img);

		return new TransferableEntity(entity);
	}
	
	@Override
	public Icon getVisualRepresentation(Transferable t)
	{
		ImageIcon icon = new ImageIcon(getDragImage());
		
		return icon;
	}
	
	@Override
	public boolean canImport(TransferSupport support)
	{
		setDragImage(img);

		return true;
	}
	
}
