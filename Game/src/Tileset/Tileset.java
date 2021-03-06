package Tileset;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import Graphic.ImagePool;

public class Tileset 
	implements Serializable
{
	private File file;
	
	private int rows, cols;
	
	private Tile[][] tiles;
	
	public Tileset(File file, int rows, int cols)
	{
		file = file.getAbsoluteFile();
		
		this.file = file;
		this.rows = rows; this.cols = cols;

		ImagePool.instance.request(file);
		
		tiles = new Tile[cols][rows];		
	
		initTiles();
	}
	
	public Tileset(Tileset tileset)
	{
		file = tileset.file;
		
		rows = tileset.rows;
		cols = tileset.cols;
		
		tiles = tileset.tiles;
	
		ImagePool.instance.request(file);
	}
	
	public Tileset(String fileName, int rows, int cols)
	{
		this(new File(fileName), rows, cols);
	}
	
	private void initTiles()
	{
		for(int row = 0; row < rows; row++)
			for(int col = 0; col < cols; col++)
				tiles[col][row] = new Tile(this, row, col);
	}
	
	public Tile get(int row, int col)
	{		
		return tiles[col][row];
	}
	
	public BufferedImage Image()
	{
		BufferedImage img;
		
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			img = null;
		}
		
		return img;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public File getFile() {
		return file;
	}
	
	public String toString() 
	{
		return "Tileset " +
			 "\nfile name " + file.getName() +
			 "\nrows: " + rows + " cols: " + cols;
	}
	
	private void writeObject(ObjectOutputStream out) 
			throws IOException
    {
		out.writeObject(file);
		out.writeInt(rows);
		out.writeInt(cols);
    }
	
	private void readObject(ObjectInputStream in) 
			throws IOException, ClassNotFoundException
    {
		file = (File)in.readObject();
		rows = in.readInt();
		cols = in.readInt();
		tiles = new Tile[cols][rows];
		
		ImagePool.instance.request(file);
		
		initTiles();
    }
	
	protected void finalize() throws Throwable
	{
		ImagePool.instance.release(file);
		super.finalize();
	}
}
