package Game;

import Actions.ActionBuffer;
import Actions.PlayerMouseInputManager;
import Behaviour.BasicAttackBehaviour;
import EntityComponent.AbilityComponent;
import EntityComponent.BehaviourComponent;
import EntityComponent.GraphicsComponent;
import EntityComponent.LifetimeComponent;
import EntityComponent.ModifierComponent;
import EntityComponent.StatsComponent;
import GUI.GUI;
import Graphic.Camera;
import Graphic.Painter;
import Movement.MovementComponent;
import Utilities.ImagePool;

public class Game
{	
	private final GUI gui;
	
	private final Updater updater;
	
	private final Painter painter;
		
	private final ActionBuffer actionBuffer;
	
	private final Player player;
		
	private final PlayerMouseInputManager mouseInHandl;

	private final Camera camera;
	
	private final ImagePool imgPool;
		
	private final Scene scene;
	
	public Game()
	{
		player = new Player();
		
		camera = new Camera();
	
		imgPool = new ImagePool();
		
		actionBuffer = new ActionBuffer();
		
		scene = new Scene();
		
		updater = new Updater(60);
				
		GameResources resources =
				new GameResources(scene, 
								  null, updater,
								  camera, player,
								  actionBuffer);
		
	mouseInHandl = new PlayerMouseInputManager(resources);
		
		resources.manager = mouseInHandl;
		
		gui = new GUI(resources);

		painter = new Painter(gui, camera);	
		
		painter.addPaintable(scene);
		
		scene.addEntity(player);
		
		Debug_addTestEntity();
		
		camera.setFocus(player.getLoc());		

		updater.addUpdatable(
				delta -> actionBuffer.invokeAll(),
				delta -> scene.update(delta),
				delta -> painter.paint());
	}
	
	private void Debug_addTestEntity()
	{
		Entity testEntity = new Entity();
		
		testEntity.setLoc(-20, -20);

		testEntity.add(new GraphicsComponent());
		testEntity.add(new MovementComponent());
		testEntity.add(new LifetimeComponent());
		testEntity.add(new StatsComponent());
		testEntity.add(new ModifierComponent());
		testEntity.add(new AbilityComponent());
		
		testEntity.get(MovementComponent.class)
				  .getNormalMovement();
		
		testEntity.add(new BehaviourComponent());
	
		BasicAttackBehaviour beh = new BasicAttackBehaviour();
		
		beh.setTarget(player);
		
	//	testEntity.get(BehaviourComponent.class)
	//		  	  .addBehaviour(beh);
	
		scene.addEntity(testEntity);
	
	}
	
	public void start()
	{
		gui.setVisible(true);
	}
	
	public void stop() 
	{
		gui.setVisible(false);
		
		updater.stop();
	}
}
