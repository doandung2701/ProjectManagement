package common.event;

import common.domain.Notification;

public class NotificationCreatedEvent implements NotificationEvent {
	private Notification notificaiton;

	public Notification getNotificaiton() {
		return notificaiton;
	}

	public void setNotificaiton(Notification notificaiton) {
		this.notificaiton = notificaiton;
	}

	public NotificationCreatedEvent(Notification notificaiton) {
		super();
		this.notificaiton = notificaiton;
	}

	public NotificationCreatedEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
}
