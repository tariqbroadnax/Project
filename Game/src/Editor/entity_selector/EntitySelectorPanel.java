package Editor.entity_selector;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Editor.EditorAssetListener;
import Editor.EditorResources;
import Editor.comp.GraphicPreview;
import Editor.selection.SelectionListener;
import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Graphic.Graphic;

public class EntitySelectorPanel extends JList<Entity>
	implements EditorAssetListener, ListSelectionListener,
			   SelectionListener
{
	private EditorResources resources;
	
	public EntitySelectorPanel(EditorResources resources)
	{
		this.resources = resources;
		
		setModel(new MyModel());
		setCellRenderer(new MyRenderer());
		
		setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		setVisibleRowCount(-1);		
		
		addListSelectionListener(this);
		
		resources.getEditorAssets()
		 		 .addEditorAssetListener(this);	
		
		resources.getSelectionHandler()
		 		 .addSelectionListener(this);
	}
	
	@Override
	public void entityAdded(Entity ent)
	{
		setVisibleRowCount(-1);		
	}
	
	@Override
	public void entityRemoved(Entity ent)
	{
		setVisibleRowCount(-1);	
	}
	
	private class MyModel implements ListModel<Entity>
	{
		@Override
		public Entity getElementAt(int index) 
		{
			return resources.getEditorAssets()
					.getEntities()
					.get(index);
		}

		@Override
		public int getSize() 
		{
			return resources.getEditorAssets()
							.getEntities()
							.size();
		}

		public void addListDataListener(ListDataListener l) {}
		public void removeListDataListener(ListDataListener l) {}
	}
	
	private class MyRenderer implements ListCellRenderer<Entity>
	{
		private GraphicPreview preview = new GraphicPreview();

		@Override
		public Component getListCellRendererComponent(
				JList<? extends Entity> list,
				Entity val, int index, boolean isSelected,
				boolean cellHasFocus) 
		{
			Graphic graph = val.get(GraphicsComponent.class)
							   .getGraphic();
			
			isSelected = resources.getSelectionHandler()
								  .isSelection(val);
			
			
			if(isSelected)
				preview.setBackground(Color.blue);
			else
				preview.setBackground(Color.white);
			
			preview.setGraphic(graph);
			
			return preview;
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
		Entity ent = getSelectedValue();
		
		resources.getSelectionHandler()
				 .setSelection(ent, false);
	}
	
	@Override
	public void selectionChanged() {
		repaint();
	}
	
	@Override
	public void selectionModified() {
		repaint();
	}
}