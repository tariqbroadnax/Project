package Entity;

import java.awt.Color;
import java.awt.Font;

import EntityComponent.GraphicsComponent;
import EntityComponent.LifetimeComponent;
import Graphic.TextGraphic;
import Movement.Force;
import Movement.MovementComponent;

public class CombatText extends Entity
{
	public CombatText(String str)
	{
		GraphicsComponent graphComp = new GraphicsComponent();
		
		TextGraphic text = new TextGraphic(str);
		
		text.setCharHeight(3);
		text.setFontStyle(Font.BOLD);
		text.setLayer(1);
		text.setPaint(Color.white);
		
		graphComp.setGraphic(text);
		
		MovementComponent moveComp = new MovementComponent();
		
		Force force = new Force(30, Math.PI * 3 / 2);
		
		moveComp.getMovement()
				.addForce(force);
		
		LifetimeComponent lifeComp = new LifetimeComponent();

		lifeComp.getLifetime()
				.setRemaining(1500);
		
		add(graphComp, moveComp, lifeComp);
	}
}