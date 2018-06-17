package application.demo.ui.login;

public class LoginPageCssHelper {
	private static String background = "http://cbnt.ru/images/u/analitics/planirovanie_chislennosti.jpg ";

	public static String createStyleForLoginForm() {
		String result = ".loginForm { margin-top:16% !important; width: 360px !important; height:250px !important; background-color:rgba(0, 0, 0, 0.5); padding:25px; margin-left: 15%!important; padding-left:40px; padding-right:40px;-webkit-box-shadow: -2px -1px 11px 0px rgba(0,0,0,0.75);-moz-box-shadow: -2px -1px 11px 0px rgba(0,0,0,0.75);box-shadow: -2px -1px 11px 0px rgba(0,0,0,0.75);  } .v-slot-passwordField{margin-top:10px;}";
		return result;
	}

	public static String createStyleForContainer() {
		String result = ".bigContainer { background: url(" + background + " );  background-repeat: no-repeat; background-size: 1924px 1000px; width: 100%; height: 100%; }";
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
