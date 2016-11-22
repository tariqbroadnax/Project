package Dialogue;

import java.io.Serializable;

public abstract class Dialogue 
	implements Serializable
{
	private int id;
	
	private String txt;
	
	public Dialogue()
	{
		id = 0;
		txt = "";
	}
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	public void setText(String txt)
	{
		this.txt = txt;
	}
	
	public int getID()
	{
		return id;
	}
	
	public String getText()
	{
		return txt;
	}
}
