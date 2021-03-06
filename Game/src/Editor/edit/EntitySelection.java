package Editor.edit;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import Entity.Entity;

public class EntitySelection implements Transferable, Serializable
{
	private List<Entity> ents;
	
	private boolean sceneSelection;
	
	public EntitySelection(List<Entity> ents, boolean sceneSelection)
	{
		this.ents = ents;
		
		this.sceneSelection = sceneSelection;
	}
	
	public List<Entity> get() {return ents;}
	
	public boolean sceneSelection() { return sceneSelection; }

	@Override
	public Object getTransferData(DataFlavor flavor) 
			throws UnsupportedFlavorException, IOException {
		return this;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() 
	{
		DataFlavor flavor = new DataFlavor(
				Entity.class, "Entity");	
		
		return new DataFlavor[]{flavor};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.getClass().equals(Entity.class);
	}
}
