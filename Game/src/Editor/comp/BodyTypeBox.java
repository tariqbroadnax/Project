package Editor.comp;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import EntityComponent.BodyType;

public class BodyTypeBox extends JComboBox<BodyType>
	implements ItemListener
{
	private List<ValueListener> listeners;
	
	public BodyTypeBox()
	{
		this(BodyType.DYNAMIC);
	}
	
	public BodyTypeBox(BodyType type)
	{
		listeners = new ArrayList<ValueListener>();
		
		BodyType[] vals = BodyType.values();
		
		DefaultComboBoxModel<BodyType> model =
				new DefaultComboBoxModel<BodyType>(vals);
		
		setModel(model);		
		
		setSelectedItem(type);
	}
	
	public void setBodyType(BodyType type) 
	{
		removeItemListener(this);
		
		setSelectedItem(type);
		
		addItemListener(this);
	}
	
	public BodyType getBodyType() {
		return (BodyType) getSelectedItem();
	}
	
	public void addValueListener(ValueListener list) {
		listeners.add(list);
	}
	
	public void removeValueListener(ValueListener list) {
		listeners.remove(list);
	}
	
	private void notifyListeners()
	{
		for(ValueListener list : listeners)
			list.valueChanged();
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		notifyListeners();
	}
}
