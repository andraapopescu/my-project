package application.demo.security;

import application.demo.domain.User;

public interface LoginService {
	
	public void login (String username, String password);
	
	public void logOut();
	
	public void addLoginListener(LoginListener loginListener);
	
	public void removeLoginListener(LoginListener listener);
	
	
	public User getCurrentUser();
	
	public static interface LoginListener {		
		public void userLoggedIn(LoginEvent e);
		public void userLoggedOut(LoginEvent e);
		public void userLoginFailed(LoginEvent e);
		
	}
	
	public static final class LoginEvent {
		private final User user;
		
		public User getUser() {
			return user;
		}
		
		public LoginService getSource() {
			return source;
		}
		
		private final LoginService source;
		public LoginEvent(LoginService source, User user) {
			this.user = user;
			this.source = source;
		}
	}
	
}
		