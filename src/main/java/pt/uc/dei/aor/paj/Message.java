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
		int minute = date.get(Calendar.MINUTE);
		String mnt;
		if (minute < 10) mnt = "0"+minute;
		else mnt = String.valueOf(minute);
		return date.get(Calendar.HOUR_OF_DAY)+":"+mnt;
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
	
	public Calendar getCalendar() {
		return date;
	}
}
