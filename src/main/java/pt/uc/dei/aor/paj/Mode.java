package pt.uc.dei.aor.paj;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class Mode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int MODES = 2;
	private boolean advancedMode = false;
	private int mode = 0;
	

	public boolean isModeAdvanced() {
		return advancedMode;
	}

	public void setModeAdvanced(boolean modeAdvanced) {
		this.advancedMode = modeAdvanced;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public void changeMode() {
		mode++;
		if (mode > MODES) mode = 0;
	}
	
}
