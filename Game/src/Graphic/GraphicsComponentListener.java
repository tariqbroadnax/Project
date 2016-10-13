package Graphic;

import EntityComponent.GraphicsComponent;

public interface GraphicsComponentListener 
{
	public void graphicChanged(
			GraphicsComponent src, 
			Graphic newGraphic, Graphic oldGraphic);
}
