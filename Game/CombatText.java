package Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;
import java.time.Duration;

import EntityComponent.GraphicsComponent;
import EntityComponent.LifetimeComponent;
import EntityComponent.StatsComponent;
import Graphic.TextGraphic;
import Movement.LooseMovement;
import Movement.MovementComponent;
import Stat.StatType;
import Stat.Stats;

public class CombatText extends Entity
{
	public CombatText()
	{
		this(new Point2D.Double(), "MOM");
	}
	
	public CombatText(Point2D.Double start, String text)
	{
		super();
	
		loc.setLocation(start);
		
		add(createStatsComponent(),
			createMovementComponent(),
			createGraphicsComponent(text),
			createLifetimeComponent());		
	}
	
	private StatsComponent createStatsComponent()
	{
		StatsComponent comp =
				new StatsComponent();
		
		Stats stats = comp.getStats();
		
		stats.setBaseStatValue(StatType.SPEED, 20);
				
		return comp;
	}
	
	private MovementComponent createMovementComponent()
	{
		MovementComponent comp =
				new MovementComponent();
		
		LooseMovement movement =
				new LooseMovement();

		movement.setDirection(3 * Math.PI / 2.0);
		
		comp.setNormalMovement(movement);
		
		return comp;
	}
	
	private GraphicsComponent createGraphicsComponent(String text)
	{
		GraphicsComponent comp = 
				new GraphicsComponent();
		
		TextGraphic graphic = new TextGraphic();
		
		if(Double.parseDouble(text) < 0)
		{
			graphic.setText(text.substring(1));
			graphic.setPaint(Color.red);
		}
		else
		{
			graphic.setText(text);
			graphic.setPaint(Color.green);
		}
		
		comp.setGraphic(graphic);
		
		return comp;
	}
	
	private LifetimeComponent createLifetimeComponent()
	{
		LifetimeComponent comp =
				new LifetimeComponent();
		
		comp.getLifetime()
			.setLength(Duration.ofMillis(1500));
		
		return comp;
	}
}
