package Editor.selection;

public interface SelectionListener {
	public void selectionChanged();
	
	public default void selectionModified(){};
}
