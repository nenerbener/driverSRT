package com.nenerbener.driverSRT;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.System;
//import org.apache.commons.cli.Options;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jdom.Document;
/**
 * Driver class that demonstrates the simplest non-gui implementation of Google2SRT developed by [reference]. 
 * Note that this class was written to be called using ./build.xml "ant run" with the input args automatically
 * loaded from default.properties file, but can be run from commandline as below. It only reads and processes 1 video file.
 *
 * @author Marc McEachern
 * @version Driver 0.01C
 */
public class DriverSRT {
	
  private static final Logger LOG = LoggerFactory
      .getLogger(MethodHandles.lookup().lookupClass());

	/**
	 * Commandline execution -
	 * java com.nenerbener.driverSRT.DriverSRT -cp ./build/classes:./build/lib/*.jar:./build/resources output-directory input youtube-URL
	 * 
	 * @param args 
	 * <li>arg[0] is the output directory (does not need to be created a priori)
	 * <li>arg[1] is the inputC URL (e.g., https://www.youtube.com/watch?v=hyttxEhAR2k)
	 * <li>Output is a directory (arg[0], created or existing) with an extracted closed caption from arg[1] video.
	 * 
	 */
	private static String SETTING_DEBUG_OPTION_STR = "setDebug";
	private static String SETTING_INCLUDE_TITLE_OPTION_STR = "setIncludeTitle";
	private static String SETTING_INCLUDE_TRACK_TITLE_OPTION_STR = "setIncludeTrackTitle";
	private static String SET_REMOVE_TIMING_SUBTITLES_OPTION_STR = "setRemoveTimingSubtitles";

	Boolean settingDebugOption = false; // debug option default
	Boolean settingIncludeTitleOption = false; // include title option default
	Boolean settingIncludeTrackTitleOption = false; // include track title option default
	Boolean setRemoveTimingSubtitlesOption = true; // remove timing subtitles option default

	String inputFile; // global class version of inputFile
	String outputDir; // global class version of outputDir
			
	//appSetting is analogous to Nutch Configuration class
	Settings appSettings;
	ControllerDefault controller;

	public Document doc = null; //downloaded DOM CC document

	public DriverSRT(
			String inputFile,
			String outputDir,
			Boolean settingDebugOption,
			Boolean settingIncludeTitleOption,
			Boolean settingIncludeTrackTitleOption,
			Boolean setRemoveTimingSubtitlesOption) {

		// process commandline parameters
//		this.settingDebugOption = settingDebugOption; // debug option default
//		this.settingIncludeTitCleOption = settingIncludeTitleOption; // include title option default
//		this.settingIncludeTrackTitleOption = false; // include track title option default
//		this.setRemoveTimingSubtitlesOption = true; // remove timing subtitles option default

		// confirm input url exists
		if (!URLexists(inputFile)) {
			LOG.info("URL does not exist, returning from method. " + inputFile);
			return;
		}

		appSettings = new Settings();
//		appSettings.loadSettings();

		// set output directory and input URL
		appSettings.setURLInput(inputFile);
		appSettings.setOutput(outputDir);
		appSettings.setDEBUG(settingDebugOption);
		appSettings.setIncludeTitleInFilename(settingIncludeTitleOption);
		appSettings.setIncludeTrackNameInFilename(settingIncludeTrackTitleOption);
		appSettings.setRemoveTimingSubtitles(setRemoveTimingSubtitlesOption);
		appSettings.setLocaleLanguage("en");

		this.inputFile = inputFile;
		this.outputDir = outputDir;

//		controller = new ControllerDefault(appSettings);
//		LOG.info("youtube CC download controller initiated...");
//
//		LOG.info("Input URL: " + inputFile);
//		if (!controller.processInputURL()) {
//			LOG.info("Method exited abnormally. URL has not attached closed caption track.");
//			return;
//		} 
//		if (!controller.convertSubtitlesTracks()) {
//			LOG.info("Method abnormally. Could not download closed caption data successfully.");
//			return;
//		}
	}

//	class ProcessInputURLException extends Exception {
//		//parameterless constructor
//		ProcessInputURLException() {}
//		
//		// Constructor that accepts a message
//		public ProcessInputURLException(String message)
//		{
//			super(message);
//		}
//	}
//	
//	class ConvertSubtitlesTracksException extends Exception {
//		//parameterless constructor
//		ConvertSubtitlesTracksException() {}
//		
//		// Constructor that accepts a message
//		public ConvertSubtitlesTracksException(String message)
//		{
//			super(message);
//		}
//	}
	
//	public Document retrieveSRT() throws ProcessInputURLException, ConvertSubtitlesTracksException
	public Document retrieveSRT()
	{
		controller = new ControllerDefault(appSettings);
		LOG.info("youtube CC download controller initiated...");

		LOG.info("Input URL: " + inputFile);
		
		if (!controller.processInputURL()) {
			LOG.info("Method exited abnormally. URL has not attached closed caption track.");
			return doc;
//			ProcessInputURLException e = new ProcessInputURLException();
//			throw e;
		} 
		if (!controller.convertSubtitlesTracks()) {
			LOG.info("Method abnormally. Could not download closed caption data successfully.");
			return doc;
//			ConvertSubtitlesTracksException e = new ConvertSubtitlesTracksException();
//			throw e;
		}
		doc = controller.getDoc();
		return doc;
	}
	
	/**
	 * returns true if URLName exists.
	 *
	 * @param URLName The URL (Youtube video link) to check if exists on internet
	 * @return true, if successful
	 */
//	public static boolean URLexists(String URLName){
	public boolean URLexists(String URLName){
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
	
	/**
	 * Common CLI implementation returns cmdline parameters
	 */
//	public static void validateOptions(
//			String[] args,
//			Boolean settingDebugOption,
//			Boolean settingIncludeTitleOption,
//			Boolean settingIncludeTrackTitleOption,
//			Boolean setRemoveTimingSubtitlesOption) {
//		Options options = new Options();
//		
//		
//	}

	public static void main(String[] args) {

		// process commandline parameters
		Boolean settingDebugOption = false; // debug option default
		Boolean settingIncludeTitleOption = false; // include title option default
		Boolean settingIncludeTrackTitleOption = false; // include track title option default
		Boolean setRemoveTimingSubtitlesOption = true; // remove timing subtitles option default

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
			LOG.info("Output directory exists:" + args[0]);
		} catch (IOException e) {
			//something else went wrong
			LOG.error("Output directory problem, exiting...");
			usage();
			System.exit(-1);
		}

		// confirm input url exists
//		if (!URLexists(args[1])) {
//			LOG.error("URL does not exist, exiting...");
//			usage();
//			System.exit(-1);
//		}

		Settings appSettings = new Settings();

		//Read properties from cmdline and set;
		settingDebugOption = 
				Boolean.parseBoolean(System.getProperty(SETTING_DEBUG_OPTION_STR));
		if(settingDebugOption) {
			appSettings.setDEBUG(true);
		} else {
			appSettings.setDEBUG(false);
		}
		settingIncludeTitleOption = 
				Boolean.parseBoolean(System.getProperty(SETTING_INCLUDE_TITLE_OPTION_STR));
		if(settingIncludeTitleOption) {
			appSettings.setIncludeTitleInFilename(true);
		} else {
			appSettings.setIncludeTitleInFilename(false);
		}
		settingIncludeTrackTitleOption = 
				Boolean.parseBoolean(System.getProperty(SETTING_INCLUDE_TRACK_TITLE_OPTION_STR));
		if(settingIncludeTrackTitleOption) {
			appSettings.setIncludeTrackNameInFilename(true);
		} else {
			appSettings.setIncludeTrackNameInFilename(false);
		}
		setRemoveTimingSubtitlesOption = 
				Boolean.parseBoolean(System.getProperty(SET_REMOVE_TIMING_SUBTITLES_OPTION_STR));
		if(setRemoveTimingSubtitlesOption) {
			appSettings.setRemoveTimingSubtitles(true);
		} else {
			appSettings.setRemoveTimingSubtitles(false);
		}

		// set output directory and input URL
		appSettings.setOutput(args[0]);
		appSettings.setURLInput(args[1]);

		ControllerDefault controller = new ControllerDefault(appSettings);
		LOG.info("youtube CC download controller initiated...");

		LOG.info("Input URL: " + args[1]);
		if (!controller.processInputURL()) {
			LOG.error("Program exited abnormally. URL has not attached closed caption track.");
			System.exit(-1);
		} 
		if (!controller.convertSubtitlesTracks()) {
			LOG.error("Program exited abnormally. Could not download closed caption data successfully.");
			System.exit(-1);
		}
		LOG.info("Program exited normally. Results are in " + args[0] + "/[youtube vid ref].srt");
			System.exit(0);
	}
}