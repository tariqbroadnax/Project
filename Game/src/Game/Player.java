package Game;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.time.Duration;

import Ability.ActiveAbility;
import AbilityInitiators.ProjectileInitiator;
import Dialogue.DialogueLog;
import EntityComponent.AbilityComponent;
import EntityComponent.EntityComponent;
import EntityComponent.GraphicsComponent;
import EntityComponent.InventoryComponent;
import EntityComponent.LifetimeComponent;
import EntityComponent.ModifierComponent;
import EntityComponent.RigidBodyComponent;
import EntityComponent.SpawnComponent;
import EntityComponent.StatsComponent;
import Graphic.AnimatedGraphic;
import Graphic.BeingGraphicsComponent;
import Graphic.LayeredGraphic;
import Graphic.ShapeGraphic;
import Graphic.Vector2D;
import Movement.MovementComponent;
import Movement.TargetedMovement;
import Stat.CoreStatType;
import TestEntity.TestEntity2;

public class Player extends Entity
{
	private KillLog killLog;
		
	private DialogueLog dialogueLog;
	
	private QuestLog questLog;
	
	private String name;
	
	public Player()
	{
		super();
		
		init();
	}
	
	public Player(Player player)
	{
		this();
		
		init();
		
		copy(player);
		
		name = player.name;
	}
	
	private void init()
	{
		killLog = new KillLog();
		dialogueLog = new DialogueLog();
		questLog = new QuestLog(this);
				
		add(createStatsComponent(),
			new PlayerMovementComponent(),
			new BeingGraphicsComponent(),
			createRigidBodyComponent(),
			createAbilityComponent(),
			createLifetimeComponent(),
			new InventoryComponent(),
			new ModifierComponent(),
			new SpawnComponent());		
	}
	
	public void update(Duration delta)
	{
		super.update(delta);
		//System.out.println(getLoc());
		questLog.update();
	}
	
	private EntityComponent createStatsComponent()
	{
		StatsComponent comp = 
				new StatsComponent();
				
		comp.getStats()
			.setCoreStatValue(CoreStatType.SPEED, 30);
		
		return comp;
	}
	
	private EntityComponent createGraphicsComponent()
	{
		GraphicsComponent comp = new GraphicsComponent();
		
		LayeredGraphic graphic = new LayeredGraphic();
	
		AnimatedGraphic ani = new AnimatedGraphic();
		
		ShapeGraphic shape1 = new ShapeGraphic(),
					 shape2 = new ShapeGraphic(),
					 shape3 = new ShapeGraphic();
		
		shape1.setPaint(Color.red);
		shape2.setPaint(Color.pink);
		shape3.setPaint(Color.orange);
		
		ani.addGraphic(shape1);
		ani.addGraphic(shape2);
		ani.addGraphic(shape3);
	
		
		graphic.addGraphic(ani, 
						   new Vector2D.Double(-5, -5));
		
		comp.setGraphic(graphic);
		
		return comp;
	}
	
	private EntityComponent createRigidBodyComponent()
	{
		RigidBodyComponent comp = new RigidBodyComponent();
		
		comp.getRigidBody()
			.addComponent(
					new Vector2D.Double(-5, -5),
					new Rectangle2D.Double(0, 0, 10, 10));
		
		//comp.addCollisionResponses(BodyType.STRUCTURE, new BodyPusher());
				
		return comp;
	}
	
	private EntityComponent createAbilityComponent()
	{
		TestEntity2 abilityEntity = new TestEntity2();
		
		ActiveAbility ability = new ActiveAbility();
		
		ability.addAbilityEntity(abilityEntity, new ProjectileInitiator());
		
		AbilityComponent comp = new AbilityComponent();
		
		comp.addActiveAbility(ability);
		
		return comp;
	}
	
	private EntityComponent createLifetimeComponent()
	{
		LifetimeComponent comp = 
				new LifetimeComponent();
		
		return comp;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public KillLog getKillLog()
	{
		return killLog;
	}
	
	public DialogueLog getDialogueLog()
	{
		return dialogueLog;
	}
	
	public QuestLog getQuestLog()
	{
		return questLog;
	}
	
	public String getName()
	{
		return name;
	}
	
	public PlayerMovementComponent getMovementComponent()
	{
		return (PlayerMovementComponent)
				get(MovementComponent.class);
	}
	
	public Object clone()
	{
		return new Player(this);
	}
}
