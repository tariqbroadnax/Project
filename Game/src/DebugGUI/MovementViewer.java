package DebugGUI;

import java.util.function.Supplier;

import Movement.Movement;

public class MovementViewer extends Viewer
{
	public MovementViewer(Supplier<Movement> movement)
	{
		model.addFieldRecord("Enabled", ()-> movement.get().isEnabled() + "");
		model.addFieldRecord("Speed", ()-> movement.get().getSpeed() + "");
	}
}
