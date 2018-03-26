package application.demo.security;


public class ServiceProvider {
	
	private ServiceProvider() {
		
	}
	
	private final static ServiceProvider INSTANCE = new ServiceProvider();
	public static StringBuilder selectedButton = new StringBuilder("-1");
	
	public static final ServiceProvider getInstance() {
		return INSTANCE;
	}
	
	public LoginService getLoginService() {
		return this.loginService;
	}
	
	private LoginService loginService = new FilterLoginService();

	public static void setSelectedButton(String newString) {
		selectedButton = new StringBuilder(newString);
		
	}

}
