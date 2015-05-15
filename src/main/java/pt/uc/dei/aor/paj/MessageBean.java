package pt.uc.dei.aor.paj;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class MessageBean {
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void prepareSecret(String username) {
		text = "/secret "+username+" ";
	}
}
