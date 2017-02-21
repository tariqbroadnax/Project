package Entity;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import EntityComponent.EntityComponent;
import Game.Scene;

public class Entity
	implements Serializable, Cloneable, Transferable
{		
	private String name;
	
	private int id;
	
	private Scene sceneLoc;
	
	protected Point2D.Double loc;

	private Map<Class<? extends EntityComponent>,
				EntityComponent> comps;
	
	private List<EntityListener> lists;
	
	public Entity()
	{		
		name = "Unnamed";
		
		id = -1;
		
		comps = new HashMap<Class<? extends EntityComponent>,
							EntityComponent>();
				
		loc = new Point2D.Double();
	
		lists = new ArrayList<EntityListener>();
	}
	
	public Entity(Entity e)
	{		
		this();
		
		copy(e);
	}

	public void update(Duration delta)
	{		
		updateComponents(delta);
	}
	
	public void updateComponents(Duration delta)
	{
		for(EntityComponent comp : comps.values())
			comp.update(delta);
	}
	
	public void copy(Entity model)
	{
		for(EntityComponent comp : model.comps.values()) 
		{
			//System.out.println(comp.getClass()); // FIXME DELETEME
			add((EntityComponent)comp.clone());
		}
		
		name = model.name;
		id = model.id;
		
		setLoc(model.loc);
	}

	public void add(EntityComponent comp)
	{
		if(comp == null)
			throw new NullPointerException();
	
		else if(comp.containsParent())
			throw new IllegalArgumentException(
					"Component already contains parent");
		else
		{
			Class<? extends EntityComponent> c =
					comp.getClass();
			
			comps.put(c, comp);
	
			// !!! IMPORTANT !!!
			comp.setParent(this);
		}
	}
	
	public void add(EntityComponent... comps)
	{
		for(EntityComponent comp : comps)
			add(comp);
			
	}
	
	public Collection<Class<? extends EntityComponent>> 
		   getComponentClasses()
	{
		return comps.keySet();
	}
	
	public <E extends EntityComponent> void remove(
			Class<E> compClass)
	{
		comps.remove(compClass);
	}
	
	@SuppressWarnings("unchecked")
	public <E extends EntityComponent> E get(
			Class<E> compClass)
	{
		return (E) comps.get(compClass);
	}
	
	public Collection<EntityComponent> getComponents()
	{
		return comps.values();
	}
	
	public <E extends EntityComponent> boolean contains(
			Class<E> compClass)
	{
		return comps.containsKey(compClass);
	}
	
	public void setLoc(Point2D.Double loc)
	{
		if(loc == null)
			throw new NullPointerException();
		
		setLoc(loc.x, loc.y);	
	}
	
	public void setSceneLoc(Scene sceneLoc) {
		this.sceneLoc = sceneLoc;
	}
	
	public Scene getSceneLoc() {
		return sceneLoc;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLoc(double x, double y)
	{
		Point2D.Double prevLoc = (Point2D.Double) loc.clone();
		
		loc.x = x;
		loc.y = y;
				
		
		List<EntityListener> copy = new ArrayList<EntityListener>(lists);
		
		for(EntityListener list : copy)
			list.locationChanged(prevLoc, loc);
	}
	
	public void move(double xOff, double yOff) {
		setLoc(loc.x + xOff, loc.y + yOff);
	}
	
	public String getName() {
		return name;
	}
	
	public int getID() {
		return id;
	}
	
	public Point2D.Double getLoc() {
		return loc;
	}
	
	public void addEntityListener(EntityListener list) {
		lists.add(list);
	}
	
	public void removeEntityListeners(EntityListener list) {
		lists.remove(list);
	}

	public Object clone()
	{
		return new Entity(this);
	}
	
	public String toString()
	{
		String str = super.toString();
	
		str += "\nComponents: ";
		
		for(EntityComponent comp : comps.values())
			str += '\n' + comp.toString() + "\n--------------------";
		
		return str;
	}
	
	public String toString2()
	{
		return super.toString();
	}

	@Override
	public Object getTransferData(DataFlavor flavor) 
			throws UnsupportedFlavorException, IOException {
		return this;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() 
	{
		DataFlavor entFlavor = new DataFlavor(Entity.class, "Entity");
		
		return new DataFlavor[]{entFlavor};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.getClass().equals(Entity.class);
	}
}
