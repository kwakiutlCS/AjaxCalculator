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
	private boolean advancedMode = true;

	public boolean isModeAdvanced() {
		return advancedMode;
	}

	public void setModeAdvanced(boolean modeAdvanced) {
		this.advancedMode = modeAdvanced;
	}
	
	
}
