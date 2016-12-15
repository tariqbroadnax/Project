package EditorGUI;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import Entity.Entity;

public class TransferableEntity implements Transferable
{
	private Entity entity;
	
	public TransferableEntity(Entity entity)
	{
		this.entity = entity;
	}
	
	@Override
	public Object getTransferData(DataFlavor arg0) throws UnsupportedFlavorException, IOException {
		return entity;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() 
	{
		DataFlavor entityFlavor = new DataFlavor(
				Entity.class, "Entity");
		
		return new DataFlavor[]{entityFlavor};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) 
	{
		return (flavor.getRepresentationClass() == 
				Entity.class);
	}

}
