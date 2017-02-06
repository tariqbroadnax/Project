package Game;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent.Type;

import Actions.ActionComponent;
import Entity.Entity;
import EntityComponent.AbilityComponent;
import EntityComponent.GraphicsComponent;
import GUI.UI;
import Inventory.InventoryComponent;
import Movement.MovementComponent;
import Quest.KillTask;
import Quest.Quest;
import Quest.QuestComponent;
import Quest.Task;
import Utilities.Scheduler;

public class Game
{
	private Scene scene;

	private Entity player;
	
	private UI ui;
	
	private Updater updater;
	private Scheduler scheduler;
	private AudioPlayer audioPlayer;
		
	public Game()
	{	
		scene = new Scene();
		
		updater = new Updater(60);

		scheduler = new Scheduler();
		
		updater.addUpdatable(
				delta -> scene.update(delta),
				delta -> scheduler.update(delta));
		
		player = new Entity();
		
		ui = new UI(scene, updater, player);
		
		audioPlayer = new AudioPlayer();
		// TESTING
		
		player.add(new GraphicsComponent(),
				   new MovementComponent(),
				   new AbilityComponent(),
				   new ActionComponent(),
				   new InventoryComponent(),
				   new QuestComponent());
		
		scene.addEntityNow(player);
		
		Quest quest = new Quest();

		Task task = new KillTask();
		
		quest.addTask(task);
		
		player.get(QuestComponent.class)
			  .addQuest(quest);

		for(int i = 0; i < 2500; i += 500)
		{
			scheduler.schedule(
					()-> player.get(AbilityComponent.class)
							   .notifyEntityKilled(new Entity()), i);
		}
		
		Entity npc = new Entity();
		
		npc.add(new GraphicsComponent());
		
		npc.setLoc(0, -50);
		
		scene.addEntity(npc);
		
		try {
			File file = new File("C:\\Users\\Tariq Broadnax\\Downloads\\Deep.wav");
			System.out.println(file.exists());
			Clip clip = AudioSystem.getClip();
			
			for(javax.sound.sampled.AudioFileFormat.Type type : AudioSystem.getAudioFileTypes())	
				System.out.println(type.getExtension());
			
			clip.open(AudioSystem.getAudioInputStream(file));
			
			audioPlayer.start(clip);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void start()
	{
		ui.setGUIVisible(true);
		updater.start();
	}
}
