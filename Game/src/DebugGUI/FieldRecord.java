package DebugGUI;

import java.util.function.Supplier;

public class FieldRecord 
{
	public final String fieldName;
	public final Supplier<String> valueSupplier;
	
	public FieldRecord(String fieldName, Supplier<String> valueSupplier)
	{
		this.fieldName = fieldName;
		this.valueSupplier = valueSupplier;
	}
}
