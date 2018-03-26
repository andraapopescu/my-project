package application.demo.ui.login;

public class LoginPageCssHelper {
	private static String background = "http://www.splitshire.com/wp-content/uploads/2015/03/SplitShire-8098-1800x1200.jpg";

	public static String createStyleForLoginForm() {
		String result = ".loginForm { margin-top:200px !important; background-color:white; padding:25px; padding-left:40px; padding-right:40px;-webkit-box-shadow: -2px -1px 11px 0px rgba(0,0,0,0.75);-moz-box-shadow: -2px -1px 11px 0px rgba(0,0,0,0.75);box-shadow: -2px -1px 11px 0px rgba(0,0,0,0.75);  } .v-slot-passwordField{margin-top:10px;}";
		return result;
	}

	public static String createStyleForContainer() {
		String result = ".bigContainer { background: url(" + background + " ); background-size:1800px 1200px;}";
		return result;
	}

	public static String createStyleForSliders() {
		String result = ".sslider {margin-left:15px}";
		return result;
	}

	public static String createStyleForSkillLabels(String name, String color, String color2) {
		String result = ".valo-menu-" + name
				+ "{ background-color: #197de1;background-image: linear-gradient(to bottom, rgb(" + color2
				+ ") 2%, rgb(" + color
				+ ") 98%); border-radius: 7px; box-shadow: 0 2px 3px rgba(0, 0, 0, 0.05);color: white;display: block;font-size: 17px;padding:4px;height: 100%;line-height: 20px;margin: 15px auto;margin-top:5px;overflow: hidden;text-align: center; width: 100% !important;}";

		return result;
	}
}
