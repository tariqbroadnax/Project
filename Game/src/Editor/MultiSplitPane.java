package Editor;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class MultiSplitPane extends JPanel
{
	private JSplitPane l_rPane,
					   rl_rrPane,
					   rlt_rlbPane;
					   
	public MultiSplitPane()
	{
		l_rPane = new JSplitPane(HORIZONTAL_SPLIT);
		rl_rrPane = new JSplitPane(HORIZONTAL_SPLIT);
		rlt_rlbPane = new JSplitPane(VERTICAL_SPLIT);
			
		l_rPane.setResizeWeight(0);
		rl_rrPane.setResizeWeight(1);
		rlt_rlbPane.setResizeWeight(1);
		
		setLayout(new BorderLayout());
		add(l_rPane, BorderLayout.CENTER);
		
		l_rPane.setRightComponent(rl_rrPane);
		rl_rrPane.setLeftComponent(rlt_rlbPane);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		//System.out.println(getSize());
		//System.out.println(l_rPane.getSize());
	}
	
	public void setLeftComponent(Component comp)
	{
		l_rPane.setLeftComponent(comp);
	
		l_rPane.setDividerLocation(100);
	}
	
	public void setRightComponent(Component comp)
	{
		rl_rrPane.setRightComponent(comp);	
		
		rl_rrPane.setDividerLocation(500);
	}
	
	public void setCenterComponent(Component comp)
	{
		rlt_rlbPane.setTopComponent(comp);
	}
	
	public void setBottomComponent(Component comp)
	{
		rlt_rlbPane.setBottomComponent(comp);
	
		rlt_rlbPane.setDividerLocation(400);

	}
}

