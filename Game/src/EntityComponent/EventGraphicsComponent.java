package EntityComponent;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import Ability.AbilityListener;
import Ability.ActiveAbility;
import Entity.Entity;
import Graphic.Animation;
import Graphic.Sprite;
import Movement.Cardinal;
import Movement.MovementComponent;
import Movement.MovementListener;

public class EventGraphicsComponent extends EntityComponent
	implements MovementListener, AbilityListener, 
			   CombatListener
{
	private static final long ATTACK_GRAPH_DUR = 500;
	
	private Map<Cardinal, GraphicBody> standbyGraphs,
									   moveGraphs,
									   castingGraphs,
									   attackGraphs,
									   attackLagGraphs,
									   attackedGraphs,
									   deathGraphs,
									   curr, defaultGraphs;
				
	private long elapsed = 0;
	
	private Cardinal c;
	
	public EventGraphicsComponent()
	{
		standbyGraphs = new HashMap<Cardinal, GraphicBody>();
		moveGraphs = new HashMap<Cardinal, GraphicBody>();
		castingGraphs = new HashMap<Cardinal, GraphicBody>();
		attackGraphs = new HashMap<Cardinal, GraphicBody>();
		attackLagGraphs = new HashMap<Cardinal, GraphicBody>();
		attackedGraphs = new HashMap<Cardinal, GraphicBody>();
		deathGraphs = new HashMap<Cardinal, GraphicBody>();
		
		for(Cardinal c : Cardinal.values())
		{
			standbyGraphs.put(c, new GraphicBody());
			moveGraphs.put(c, new GraphicBody());
			castingGraphs.put(c, new GraphicBody());
			attackGraphs.put(c, new GraphicBody());
			attackLagGraphs.put(c, new GraphicBody());
			attackedGraphs.put(c, new GraphicBody());
			deathGraphs.put(c, new GraphicBody());
		}
		
		curr = defaultGraphs = standbyGraphs;
	
		_TEST();
	}
	
	public EventGraphicsComponent(EventGraphicsComponent comp)
	{
		standbyGraphs = new HashMap<Cardinal, GraphicBody>();
		moveGraphs = new HashMap<Cardinal, GraphicBody>();
		castingGraphs = new HashMap<Cardinal, GraphicBody>();
		attackGraphs = new HashMap<Cardinal, GraphicBody>();
		attackLagGraphs = new HashMap<Cardinal, GraphicBody>();
		attackedGraphs = new HashMap<Cardinal, GraphicBody>();
		deathGraphs = new HashMap<Cardinal, GraphicBody>();
		
		for(Cardinal c : Cardinal.values())
		{
			standbyGraphs.put(c, (GraphicBody) comp.getStandbyGraphicBody(c).clone());
			moveGraphs.put(c, (GraphicBody) comp.getMovementGraphicBody(c).clone());
			castingGraphs.put(c, (GraphicBody) comp.getCastingGraphicBody(c).clone());
			attackGraphs.put(c, (GraphicBody) comp.getAttackGraphicBody(c).clone());
			attackLagGraphs.put(c, (GraphicBody) comp.getAttackLagGraphicBody(c).clone());
			attackedGraphs.put(c, (GraphicBody) comp.getAttackedGraphicBody(c).clone());
			deathGraphs.put(c, (GraphicBody) comp.getDeathGraphicBody(c).clone());
		}
		
		curr = defaultGraphs = standbyGraphs;
	}
	
	private void _TEST()
	{		
		String fileName = "ss.png";
				
		int rows = 8, cols = 8;
		
		Sprite nSpr = new Sprite(fileName, 8, rows, cols),
			   sSpr = new Sprite(fileName, 32, rows, cols),
			   eSpr = new Sprite(fileName, 0, rows, cols),
			   wSpr = new Sprite(fileName, 56, rows, cols);
		
		defaultGraphs.get(Cardinal.NORTH).addGraphic(nSpr);
		defaultGraphs.get(Cardinal.SOUTH).addGraphic(sSpr);
		defaultGraphs.get(Cardinal.EAST).addGraphic(eSpr);
		defaultGraphs.get(Cardinal.WEST).addGraphic(wSpr);

		Animation n = Animation.of(fileName, rows, cols, 8, 15),
				  s = Animation.of(fileName, rows, cols, 32, 39),
				  e = Animation.of(fileName, rows, cols, 0, 7),
				  w = Animation.of(fileName, rows, cols, 56, 63);
		
		moveGraphs.get(Cardinal.NORTH).addGraphic(n);
		moveGraphs.get(Cardinal.SOUTH).addGraphic(s);
		moveGraphs.get(Cardinal.EAST).addGraphic(e);
		moveGraphs.get(Cardinal.WEST).addGraphic(w);
	}
	
	public void start()
	{
		MovementComponent moveComp = parent.get(MovementComponent.class);
		
		c = moveComp.getDirection();
		
		moveComp.addMovementListener(this);
	}
	
	@Override
	public void update(Duration delta) 
	{
		if(curr == attackGraphs)
		{
			elapsed += delta.toMillis();
			
			if(elapsed > ATTACK_GRAPH_DUR)
				curr = defaultGraphs;
		}
	
		Cardinal c = parent.get(MovementComponent.class)
						   .getDirection();
			
		GraphicBody body = curr.get(c);
		
		if(body.size() > 0)
		{
			parent.get(GraphicsComponent.class)
				  .setGraphic(body);
		}
	}
	
	@Override
	public void directionChanged(MovementComponent src) {
		c = src.getDirection();
	}
	
	@Override
	public void movementStarted(MovementComponent src) {
		curr = defaultGraphs = moveGraphs;
	}
	
	@Override
	public void movementStopped(MovementComponent src) {
		curr = defaultGraphs = standbyGraphs;
	}
	
	@Override
	public void castingStarted(ActiveAbility src) {
		curr = castingGraphs;
	}
	
	@Override
	public void castingStopped(ActiveAbility src) 
	{
		if(curr == castingGraphs)
			curr = defaultGraphs = standbyGraphs;
	}
	
	@Override
	public void basicAttackStarted() {
		curr = defaultGraphs = attackGraphs;
	}
	
	@Override
	public void basicAttackStopped() 
	{
		if(curr == attackGraphs)
			curr = defaultGraphs = standbyGraphs;
	}
	
	@Override
	public void entityAttacks(Entity ent) 
	{
		curr = attackedGraphs;
		elapsed = 0;
	}
	
	@Override
	public void entityKills(Entity ent) {
		curr = deathGraphs;
	}
	
	@Override
	public void basicAttackWaiting() {
		curr = defaultGraphs = attackLagGraphs;
	}
	
	public GraphicBody getStandbyGraphicBody(Cardinal c) {
		return standbyGraphs.get(c);
	}
	
	public GraphicBody getMovementGraphicBody(Cardinal c) {
		return moveGraphs.get(c);
	}
	
	public GraphicBody getCastingGraphicBody(Cardinal c) {
		return moveGraphs.get(c);
	}
	
	public GraphicBody getAttackGraphicBody(Cardinal c) {
		return attackGraphs.get(c);
	}
	
	public GraphicBody getAttackLagGraphicBody(Cardinal c) {
		return attackLagGraphs.get(c);
	}
	
	public GraphicBody getAttackedGraphicBody(Cardinal c) {
		return attackedGraphs.get(c);
	}
	
	public GraphicBody getDeathGraphicBody(Cardinal c) {
		return deathGraphs.get(c);
	}

	@Override
	protected EntityComponent _clone() {
		return new EventGraphicsComponent(this);
	}
}
