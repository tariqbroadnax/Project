package Item;

public class DropItem
{
	public final Item item;
	
	public final double rate;
	
	public DropItem(Item item, double rate)
	{
		this.item = item;
		
		this.rate = rate;
	}
	
	public DropItem(DropItem src)
	{
		item = (Item)src.item.clone();
				
		rate = src.rate;
	}
	
	public String toString()
	{
		return item.toString2() + " drop rate: " + rate;
	}
}
