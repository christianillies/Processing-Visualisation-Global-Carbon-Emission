package info.christianillies.framework.common;

import processing.core.PApplet;

/**
 * global constants (i.e. papplet instance)
 * @author christian illies
 *
 */
public class FrameworkConstants {
	private static PApplet pappletInstance;

	/**
	 * registers a pappletinstance for the whole framework that can be used globally.
	 * @param pappletInstance instance from papplet
	 */
	public static void register(PApplet pappletInstance) {
		if(pappletInstance == null) {
			return;
		}
		FrameworkConstants.pappletInstance = pappletInstance;
	}
	
	/**
	 * returns the registered instance of papplet
	 * @return a papplet instance 
	 */
	public static PApplet getPappletInstance() {
		if(pappletInstance == null) {
			System.err.println("FrameworkConstants.getPappletInstance(): papplet instance is null. you have to register a instance with the mehtod 'FrameworkConstants.register(PApplet)'.");
		}
		return pappletInstance;
	}
	
	/**
	 * returns the current project dir. needed to use this application under different system (i.e. linux, windows, mac) to get the rights (image) paths.
	 * @return
	 */
	public static String getProjectDir() {
		return System.getProperty("user.dir") + "/";
	}
}
