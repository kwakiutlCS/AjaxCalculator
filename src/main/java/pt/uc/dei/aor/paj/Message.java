package pt.uc.dei.aor.paj;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Message {
	private String text;
	private User sender;
	private GregorianCalendar date;
	private User receiver;
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public String getDate() {
		return date.get(Calendar.HOUR)+":"+date.get(Calendar.MINUTE);
	}
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	
	
}
