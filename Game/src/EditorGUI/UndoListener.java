package EditorGUI;

public interface UndoListener 
{
	public void undoOccurred(UndoManager src);
	
	public void redoOccurred(UndoManager src);
	
	public void editAdded(UndoManager src);	
}
