package Entity;

import Behaviour.WanderBehaviour;
import EntityComponent.AbilityComponent;
import EntityComponent.BehaviourComponent;
import EntityComponent.EffectComponent;
import EntityComponent.GraphicsComponent;
import EntityComponent.LifetimeComponent;
import EntityComponent.RigidBodyComponent;
import EntityComponent.StatsComponent;
import Movement.MovementComponent;

public class NPC extends Entity
{
	//private DialogueNode dialogueTree;
	
	public NPC()
	{
		super();
		
		//dialogueTree = new DialogueNode();
		
		setLoc(-20, -20);

		add(new MovementComponent());
		add(new GraphicsComponent(),
					   new RigidBodyComponent());
		add(new LifetimeComponent());
		add(new StatsComponent());
		add(new AbilityComponent(),
					   new EffectComponent());

		BehaviourComponent comp = new BehaviourComponent();
		WanderBehaviour beh = new WanderBehaviour();
		
		comp.addBehaviour(beh);
		
		add(comp);
	}
	
//	public DialogueNode getDialogueTree() {
	//	return dialogueTree;
	//}
}
	