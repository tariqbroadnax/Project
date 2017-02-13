package Editor.edit;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

import Entity.Entity;

public class EntityList implements Transferable
{
	private List<Entity> ents;
	
	public EntityList(List<Entity> ents)
	{
		this.ents = ents;
	}
	
	public List<Entity> get() {return ents;}

	@Override
	public Object getTransferData(DataFlavor flavor) 
			throws UnsupportedFlavorException, IOException {
		return this;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() 
	{
		DataFlavor flavor = new DataFlavor(
				EntityList.class, "EntityList");	
		
		return new DataFlavor[]{flavor};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.getClass().equals(EntityList.class);
	}
}
