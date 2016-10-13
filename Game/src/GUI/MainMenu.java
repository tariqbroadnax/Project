package GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

public class MainMenu extends JPanel
{
	private double minWeightX;
	
	
	public MainMenu()
	{
		setLayout(new FlowLayout(FlowLayout.LEADING));
		
		minWeightX = .1;
	}
	
	public void setMenuItemWeightX(int menuItemWeightX)
	{
		this.minWeightX = menuItemWeightX;
	}
	
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		
		resizeComponents();
	}
	
	private void resizeComponents()
	{
		Component[] comps = getComponents();
		
		Dimension menuItemDim = 
				getMenuItemDimension();
				
		for(int i = 0; i < comps.length; i++)
			comps[i].setSize(menuItemDim);
	}
	
	protected void addImpl(Component comp, Object constraints,
			int index)
	{
		super.addImpl(comp, constraints, index);
		
		comp.setSize(getMenuItemDimension());
	}

	private Dimension getMenuItemDimension()
	{
		int count = getComponentCount();
		
		double totalWeightX =
				minWeightX * count,
			   actualWeightX = 
			   		totalWeightX < 1 ?
					minWeightX :
					1.0 / count;
		
		return new Dimension(
				(int)(actualWeightX * getWidth()),
				getHeight());
	}
}
