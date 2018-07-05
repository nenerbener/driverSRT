package com.nenerbener.driverSRT;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// TODO: Auto-generated Javadoc

/**
 * Driver class that demonstrates the simplest non-gui implementation of Google2SRT developed by [reference]. 
 * Note that this class was written to be called using ./build.xml "ant run" with the input args automatically
 * loaded from default.properties file, but can be run from commandline as below. It only reads and processes 1 video file.
 *
 * @author Marc McEachern
 * @version Driver 0.01
 */
public class DriverSRT {
	
	/**
	 * Commandline execution -
	 * java com.nenerbener.driverSRT.DriverSRT -cp ./build/classes:./build/lib/*.jar:./build/resources output-directory input youtube-URL
	 * 
	 * @param args 
	 * <li>arg[0] is the output directory (does not need to be created a priori)
	 * <li>arg[1] is the input URL (e.g., https://www.youtube.com/watch?v=hyttxEhAR2k)
	 * <li>Output is a directory (arg[0], created or existing) with an extracted closed caption from arg[1] video.
	 * 
	 */
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

		Settings appSettings = new Settings();
		appSettings.loadSettings();

		// set output directory and input URL
		appSettings.setOutput(args[0]);
		appSettings.setURLInput(args[1]);

		ControllerDefault controller = new ControllerDefault(appSettings);

		if (!controller.processInputURL()) {
			System.out.println("Program exited abnormally. URL has not attached closed caption track.");
			System.exit(-1);
		} 
		if (!controller.convertSubtitlesTracks()) {
			System.out.println("Program exited abnormally. Could not download closed caption data successfully.");
			System.exit(-1);
		}
		System.out.println("Program exited normally.");
		//		System.exit(0);
	}

	/**
	 * returns true if URLName exists.
	 *
	 * @param URLName The URL (Youtube video link) to check if exists on internet
	 * @return true, if successful
	 */
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

	/**
	 * prints out to standard out the commandline usage. Does not exit() from program inside method.
	 */
	public static void usage() {
		System.out.println("java driverSRT [Output directory] [input URL]");
	}
}
