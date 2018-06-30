package com.nenerbener.driverSRT;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import org.jdom.input.JDOMParseException;

public class DriverSRT {

	public static void main(String[] args) {

		// process commandline parameters
		// confirm 2 commandline options
		if (args.length != 2) {
			usage();
			System.exit(1);
		}
		// confirm output directory exists, if not create it
		Path path = Paths.get(args[0]);

		try {
			Files.createDirectory(path);
		} catch(FileAlreadyExistsException e){
			// the directory already exists.
			System.out.println("Output directory exists:" + args[0]);
		} catch (IOException e) {
			//something else went wrong
			System.out.println("Output directory problem, exiting...");
			usage();
			System.exit(-1);
		}


		// confirm input url exists
		if (!URLexists(args[1])) {
			System.out.println("URL does not exist, exiting...");
			usage();
			System.exit(-1);
		}

		ResourceBundle bundle = ControllerDefault.getBundle();
		Settings appSettings = new Settings(bundle);
		appSettings.loadSettings();

		// set output directory and input URL
		appSettings.setOutput(args[0]);
		appSettings.setURLInput(args[1]);

		ControllerDefault controller = new ControllerDefault(appSettings);

		controller.processInputURL();
		controller.convertSubtitlesTracks();
		System.out.println("Program exited normally.");
		//		System.exit(0);
	}

	public static boolean URLexists(String URLName){
		try {
			HttpURLConnection.setFollowRedirects(false);
			// note : you may also need
			//        HttpURLConnection.setInstanceFollowRedirects(false)
			HttpURLConnection con =
				(HttpURLConnection) new URL(URLName).openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}  

	public static void usage() {
		System.out.println("java driverSRT [Output directory] [input URL]");
	}
}
