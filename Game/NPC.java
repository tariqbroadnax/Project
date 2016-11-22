package Game;

import java.util.ArrayList;

import Dialogue.Dialogue;
import Dialogue.EndDialogue;
import Dialogue.QuestDialogue;
import Dialogue.ResponseDialogue;
import EntityComponent.EntityComponent;
import EntityComponent.GraphicsComponent;
import EntityComponent.StatsComponent;
import Graphic.LayeredGraphic;
import Graphic.ShapeGraphic;
import Graphic.Vector2D;
import Movement.MovementComponent;

public class NPC extends Entity
{
	private Dialogue dialogue;
	
	private ArrayList<Quest> quests;
	
	public NPC()
	{
		this(-1);
	}
	
	public NPC(int entityID)
	{
		super();
		
		setEntityID(entityID);
		
		ResponseDialogue d1 = new ResponseDialogue();
		QuestDialogue d2 = new QuestDialogue();
		EndDialogue d3 = new EndDialogue();
		
		d1.setText("adsadsasdaasdads");
		d2.setText("afdadfdfaafdafdas");
		d3.setText("afdfadafdshagsdaadsf");
		
		d1.put("option 1", d2);
		d1.put("option 2", d3);
		
		dialogue = d1;
		
		add(new StatsComponent(),
			createGraphicsComponent(),
			new MovementComponent());
	}
	
	public void addQuest(Quest quest)
	{
		quests.add(quest);
	}
	
	public Quest getQuest(int i)
	{
		return quests.get(i);
	}
	
	public void setDialogue(Dialogue dialogue)
	{
		this.dialogue = dialogue;
	}
	
	public Dialogue getDialogue(Player talker)
	{
		talker.getDialogueLog()
			  .completeDialogue(dialogue.getID());
		
		return dialogue;
	}
	
	private EntityComponent createGraphicsComponent()
	{
		GraphicsComponent comp = new GraphicsComponent();
		
		LayeredGraphic graphic = new LayeredGraphic();
	
		graphic.addGraphic(new ShapeGraphic(), 
						   new Vector2D.Double(-5, -5));
		
		comp.setGraphic(graphic);
		
		return comp;
	}
}
	