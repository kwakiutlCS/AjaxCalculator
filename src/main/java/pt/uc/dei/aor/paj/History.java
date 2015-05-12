package pt.uc.dei.aor.paj;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class History implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7514545189871598589L;
	List<HistoryEntry> listEntry;
	
	
	//Constructor
	public History(){
		listEntry = new LinkedList<>();
	}

	public void addEntry(Screen exp, String res, long time) {
		String delta;
		if (time > 1000000) delta = String.valueOf(time/1000000)+"ms";
		else delta = String.valueOf(time/1000)+"\u03BCs";
		HistoryEntry entry = new HistoryEntry(exp, res, delta);
		listEntry.remove(entry);
		listEntry.add(0, entry);
	}

	public List<HistoryEntry> getListEntry() {
		return listEntry;
	}

	public void setListEntry(List<HistoryEntry> listEntry) {
		this.listEntry = listEntry;
	}
	
}
