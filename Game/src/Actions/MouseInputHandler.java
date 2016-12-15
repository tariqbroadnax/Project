package Actions;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.time.Duration;
import java.util.List;

import Ability.RangeIndicator;
import Ability.TargetAbility;
import Ability.TargetPointAbility;
import Ability.TargetUnitAbility;
import Behaviour.BasicAttackBehaviour;
import Behaviour.PathingBehaviour;
import EditorGUI.MouseListener;
import EditorGUI.MouseMotionListener;
import Entity.Entity;
import Entity.Monster;
import Entity.Player;
import EntityComponent.AbilityComponent;
import EntityComponent.GraphicsComponent;
import Game.GameResources;
import Game.Scene;
import Game.Updatable;
import Graphic.Camera;
import Graphic.Graphic;
import Utilities.Collections2;

public class MouseInputHandler 
	implements MouseListener, MouseMotionListener,
			   Updatable
{
	private final long MOUSE_INPUT_PROCESSING_DELAY = 150;
	
	private long elapsed;
	
	private Scene scene;
	private Camera camera;
	
	private GraphicsComponent graphComp;
	private AbilityComponent abilityComp;
	
	private BasicAttackBehaviour attack;
	private PathingBehaviour pathing;
		
	private TargetAbility ability;
	private Graphic indicatorGraph;
		
	private boolean mousePressed;
	private MouseEvent e;

	public MouseInputHandler(GameResources resources) 
	{						
		scene = resources.scene;
		camera = resources.camera;
		
		resources.updater.addUpdatable(this);
		
		Player player = resources.player;
		
		graphComp = player.get(GraphicsComponent.class);
		abilityComp = player.get(AbilityComponent.class);
		
		pathing = player.getPathingBehaviour();
		attack = player.getAttackBehaviour();
		
		mousePressed = false;
	}
	
	@Override
	public void update(Duration delta)
	{
		if(mousePressed)
		{
			elapsed += delta.toMillis();
			
			if(elapsed > MOUSE_INPUT_PROCESSING_DELAY)
			{
				elapsed -= MOUSE_INPUT_PROCESSING_DELAY;
				processMouseInput();
			}
		}
	}
	
	public void prepareToCast(TargetAbility ability)
	{
		this.ability = ability;
		
		RangeIndicator indicator = 
				ability.getRangeIndicator();
		
		if(indicator != null)
		{
			indicatorGraph = indicator.getGraphic();
			graphComp.getDecorations()
					 .add(indicatorGraph);
		}
	}

	private void processMouseInput()
	{
		if(preparedToCast())
		{
			if(isLeftMouseButton(e))
				castAbility();
			
			unprepareToCast();
		}
		else
		{
			Entity entity = entityAtMouseLocation(e);
			
			if(entity != null && isLeftMouseButton(e))
				attackEntity(entity);
			else if(isRightMouseButton(e))
				moveToLocation();
		}
	}
	
	private void castAbility()
	{
		Point p = e.getPoint();
		Point2D.Double normLoc = 
				camera.normalLocation(p);
		
		if(ability instanceof TargetUnitAbility)
		{	
			TargetUnitAbility ability =
					(TargetUnitAbility) this.ability;
			
			Entity entity = Collections2.findFirst(
					scene.entitiesVisibleAtLocation(normLoc),
					ent -> ! (ent instanceof Player));
			
			ability.setTarget(entity);
		} 
		else 
		{
			TargetPointAbility ability =
					(TargetPointAbility) this.ability;
			
			ability.setTarget(normLoc);
		}
		
		abilityComp.cast(ability);
	}

	private boolean preparedToCast() {
		return ability != null;
	}

	private void unprepareToCast()
	{
		ability = null;
		graphComp.getDecorations()
				 .remove(indicatorGraph);
	}

	private void attackEntity(Entity entity)
	{
		pathing.clearTargets();
		attack.setTarget(entity);
	}
	
	private void moveToLocation()
	{
		Point2D.Double normLoc =
				camera.normalLocation(e.getPoint());
		
		attack.removeTarget();
		pathing.setTarget(normLoc);
	}
	
	private Entity entityAtMouseLocation(MouseEvent e)
	{
		Point2D.Double normLoc =
				camera.normalLocation(e.getPoint());
		
		List<Entity> entities = 
				scene.entitiesVisibleAtLocation(normLoc);

		Entity entity = Collections2.findFirst(entities,
				ent -> ent instanceof Monster);
		
		return entity;
	}
	
	public void mousePressed(MouseEvent e)
	{				
		mousePressed = true;
		// vvv causes input to process immediately
		elapsed = MOUSE_INPUT_PROCESSING_DELAY; 
		this.e = e;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		this.e = e;
	}
}