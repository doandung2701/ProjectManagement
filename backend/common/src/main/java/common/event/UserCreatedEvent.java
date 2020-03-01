package common.event;

import common.domain.User;

public class UserCreatedEvent implements UserEvent{
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserCreatedEvent(User user) {
		super();
		this.user = user;
	}

	public UserCreatedEvent() {
		super();
	}
	
}
