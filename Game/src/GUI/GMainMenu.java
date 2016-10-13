package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JToolBar;

import Actions.ToggleAbilityFrame;
import Actions.ToggleItemFrame;
import GameClient.GameClientResources;
import Utilities.FontUtilities;

public class GMainMenu extends JToolBar
{	
	private final static int BUTTON_COUNT = 6;
	
	private final static double BUTTON_TEXT_WIDTH_WEIGHT = .9;
	
	private Font buttonFont;
	
	private JButton abilityButton,
					itemButton,
					equipButton,
					questButton,
					statButton,
					partyButton,
					friendButton;
	
	public GMainMenu(GameClientResources resources)
	{
		super();
		
		setFloatable(false);
		
		buttonFont = new Font("Consolas", Font.PLAIN, 12);
		
		abilityButton = new JButton("ABILITY");
		itemButton = new JButton("ITEM");
		equipButton = new JButton("EQUIP");
		questButton = new JButton("QUEST");
		statButton = new JButton("STAT");
		friendButton = new JButton("FRIEND");
		
		setLayout(null);
	
		formatButtons();
		addButtons();
		
		itemButton.addActionListener(new ToggleItemFrame(resources));
		abilityButton.addActionListener(new ToggleAbilityFrame(resources));
		
	}
	
	private List<JButton> buttons()
	{
		return Arrays.asList(
				abilityButton, itemButton,
				equipButton, questButton,
				statButton, friendButton);
	}
	
	private void formatButtons()
	{
		for(JButton button : buttons())
		{
			button.setMargin(new Insets(0, 0, 0, 0));
		}
	}
	
	private void addButtons()
	{
		Dimension dim = buttons().get(0)
								 .getSize();
		
		int x = 0, y = 0;
		for(JButton button : buttons())
		{
			button.setLocation(x, y);
			
			add(button);
			
			x += dim.width;
		}
	}
	
	private String findLongestButtonText()
	{
		String text = "";
		
		for(JButton button : buttons())
			text = text.length() > button.getText().length() ?
				   text : button.getText();
		
		return text;
	}
	
	private Dimension findButtonDimension()
	{
		return new Dimension(
				getWidth() / BUTTON_COUNT,
				getHeight()); 
	}
	
	public void setSize(Dimension dim)
	{
		super.setSize(dim);
		removeAll();
		
		Dimension buttonDim = 
				findButtonDimension();
		
		Font buttonFont =
			findButtonFont(buttonDim);
		
		for(JButton button : buttons())
		{
			button.setFont(buttonFont);
			button.setSize(buttonDim);
		 }
		
		addButtons();	
	}
	
	private Font findButtonFont(Dimension buttonDim)
	{	
		int txtWidth = (int) (
				buttonDim.getWidth() *
				BUTTON_TEXT_WIDTH_WEIGHT);
	
		String longTxt = 
				findLongestButtonText();
			
		return FontUtilities.fontWithWidth(
				buttonFont, txtWidth,
				longTxt, this);
	}	
}
