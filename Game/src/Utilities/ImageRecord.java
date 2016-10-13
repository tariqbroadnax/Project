package Utilities;

public class ImageRecord 
{
	public final String fileName;
	
	public final int imageID;
	
	public final int x, y, tileWidth, tileHeight;
	
	public ImageRecord(String fileName, int imageID,
			int x, int y, int tileWidth, int tileHeight)
	{
		this.fileName = fileName;
		this.imageID = imageID;
		
		this.x = x;
		this.y = y;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}
}
