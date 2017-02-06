package Item;

public class Item
{	
	private int id;

	private String name;
	
	public Item()
	{
		id = -1;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
