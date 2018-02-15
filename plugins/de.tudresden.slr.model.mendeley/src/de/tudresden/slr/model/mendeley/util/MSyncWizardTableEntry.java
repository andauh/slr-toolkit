package de.tudresden.slr.model.mendeley.util;
import org.jbibtex.Key;
import org.jbibtex.Value;

public class MSyncWizardTableEntry {
	private Key key;
	private Value value1;
	private Value value2;
	private Value selected;
	private SyncItem syncItem;
	
	public MSyncWizardTableEntry(Key key, Value v1, Value v2, Value selected){
		this.key = key;
		this.value1 = v1;
		this.value2 = v2;
		this.selected = selected;
		
	}
	
	public Key getKey() {
		return key;
	}
	
	public Value getValue1() {
		return value1;
	}
	
	public Value getValue2() {
		return value2;
	}
	
	public Value getSelected() {
		return selected;
	}
	
	public void setSelected(Value selected) {
		this.selected = selected;
	}
	
	public SyncItem getSyncItem() {
		return syncItem;
	}
	
	public void setSyncItem(SyncItem syncItem) {
		this.syncItem = syncItem;
	}
	
	
}
