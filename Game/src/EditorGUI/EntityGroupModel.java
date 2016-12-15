package EditorGUI;

import java.util.ArrayList;

public class EntityGroupModel 
{
	private ArrayList<EntityGroup> entityGroups;
	
	public EntityGroupModel()
	{
		entityGroups = new ArrayList<EntityGroup>();
	}
	
	public void add(EntityGroup group)
	{
		entityGroups.add(group);
	}
	
	public void remove(EntityGroup group)
	{
		entityGroups.remove(group);
	}
}
