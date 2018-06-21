import java.awt.Desktop;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class SRTDriver {

	public static void main(String[] args) {
		
		String URLInput = "https://www.youtube.com/watch?v=C9hJScrz4kY";
		String srtOutput = "/home/mm/tmp";

		ResourceBundle bundle = ControllerDefault.getBundle();
		Settings appSettings = new Settings(bundle);
		appSettings.loadSettings();

		appSettings.setURLInput(URLInput);
		appSettings.setOutput(srtOutput);

		ControllerDefault controller = new ControllerDefault(appSettings);

		controller.processInputURL();
		controller.convertSubtitlesTracks();
		System.exit(0);
	}

}
