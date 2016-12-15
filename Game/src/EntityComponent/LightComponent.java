package EntityComponent;

import java.time.Duration;

import Game.Light;

public class LightComponent extends EntityComponent
{
	private Light light;
	
	public LightComponent()
	{
		light = new Light();
	}
	
	@Override
	public void update(Duration delta) {
		light.setLocation(parent.getLoc());
	}
	
	public void setLight(Light light) {
		this.light = light;
	}
	
	public Light getLight() {
		return light;
	}

	@Override
	protected EntityComponent _clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
