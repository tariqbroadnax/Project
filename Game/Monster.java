package Game;

public class Monster extends Entity
{
	public int id;
	
	public String name;
	
	public Monster(int id)
	{
		super();
		
		id = 0;
		
		name = "MONSTER";
	}
	
	public Monster(Monster monster)
	{
		super(monster);
		
		id = monster.id;
		
		name = monster.name;
	}
	
	public int getID()
	{
		return id;
	}
	
	public Object clone()
	{
		return new Monster(this);
	}
}
