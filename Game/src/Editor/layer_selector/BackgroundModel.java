package Editor.layer_selector;

import javax.swing.AbstractListModel;

import Game.Scene;
import Graphic.Graphic;

public class BackgroundModel extends
	AbstractListModel<Graphic>
{
	private Scene scene;
	
	public BackgroundModel(Scene scene)
	{
		this.scene = scene;
	}
	
	public void setScene(Scene scene)
	{
		this.scene = scene;
	}
	
	@Override
	public Graphic getElementAt(int index) 
	{
		return scene.getBackgroundLayer(index);
	}

	@Override
	public int getSize() 
	{
		return scene.getBackgroundLayerCount();
	}
}
