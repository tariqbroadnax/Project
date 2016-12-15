package Editor.layer_selector;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import Game.Scene;
import Graphic.Graphic;

public class LayerSelectorPanel extends JPanel
{
	public LayerSelectorPanel(Scene scene)
	{
		JToolBar toolbar = new JToolBar();

		JList<Graphic> list = new JList<Graphic>();
		JList<Graphic> visibList = new JList<Graphic>();
		
		list.setFixedCellHeight(25);
		visibList.setFixedCellHeight(25);
		
		BackgroundModel model = new BackgroundModel(scene);
		
		BackgroundCellRenderer renderer =
				new BackgroundCellRenderer();
		BackgroundVisibilityCellRenderer visibRenderer =
				new BackgroundVisibilityCellRenderer();
		
		ToggleLayerVisibility toggle = 
				new ToggleLayerVisibility(visibList);
		
		list.setModel(model);
		list.setCellRenderer(renderer);
		
		visibList.setModel(model);
		visibList.setCellRenderer(visibRenderer);
		visibList.addMouseListener(toggle);
		
		toolbar.add(new CreateLayer(list));
		toolbar.add(new DeleteLayer(list));
		toolbar.add(new OpenProperties(list));
		
		setLayout(new BorderLayout());
		
		add(toolbar, BorderLayout.NORTH);
		add(list, BorderLayout.CENTER);
		add(visibList, BorderLayout.WEST);
	
		setPreferredSize(new Dimension(200, 200));
	}
}
