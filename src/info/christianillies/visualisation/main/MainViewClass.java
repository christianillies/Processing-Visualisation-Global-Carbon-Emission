/**
 * main elements for starting the application
 */
package info.christianillies.visualisation.main;

import info.christianillies.framework.common.FrameworkConstants;
import info.christianillies.framework.common.IErrorHandler;
import info.christianillies.framework.controller.IController;
import info.christianillies.visualisation.controller.VisualisationController;
import info.christianillies.visualisation.model.XlsWorldDatabankModel;
import info.christianillies.visualisation.model.loader.XlsLoaderStrategy;
import info.christianillies.visualisation.view.GraphicalUserInterfaceView;
import processing.core.PApplet;

/**
 * Main Class to start the application
 * @author christian illies
 * 
 */
public class MainViewClass extends PApplet implements IErrorHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 333943171356572410L;

	/**
	 * debug mode for more live debug-information
	 */
	public static boolean DEBUG_MODE = false;
	
	/**
	 * saves resources an call draw() only if mouse moved
	 */
	public static boolean SAVE_POWER = false;
	
	/**
	 * the controller
	 */
	private IController _controller;
	/**
	 * the model
	 */
	private XlsWorldDatabankModel _xlsmodel;
	/**
	 * the view
	 */
	private GraphicalUserInterfaceView _view;

	/**
	 * indicates moved pixels with the mouse (debug)
	 */
	private int mouseMoves;

	/**
	 * counts the mouse clicks (debug)
	 */
	private int mouseClicks;

	/**
	 * main-method for creating java application instead of applets
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PApplet.main(new String[]{"info.christianillies.visualisation.main.MainViewClass"});
	}

	@Override
	public void setup() {
		
		/* 
		 * window configuration
		 */
		size(1280, 768, JAVA2D);
		smooth();
		frameRate(32);
		
		/*
		 * register papplet instance to framework
		 */
		FrameworkConstants.register(this);
		
		/*
		 * initialize the data model
		 */
		_xlsmodel = new XlsWorldDatabankModel(FrameworkConstants.getProjectDir() + "datasource/global-co2-emission.xls");
		_xlsmodel.setLoadStrategy(new XlsLoaderStrategy());
		final long startTime = System.currentTimeMillis();
		_xlsmodel.loadXlsFile();
		final long endTime = System.currentTimeMillis();
		
		System.out.println("------------------");
		System.out.println();
		System.out.println("Global Carbon Emission Visualisation Application by Christian Illies");
		System.out.println("xls loading time: " + (endTime-startTime) + "ms");
		System.out.println();
		
		/*
		 * init the view object
		 */
		_view = new GraphicalUserInterfaceView(_xlsmodel);
//		_view.setModel(_xlsmodel);

		/*
		 * init the controller
		 */
		_controller = new VisualisationController();
		_controller.setModel(_xlsmodel);	
		_controller.setView(_view);	
		_view.setController(_controller);
	}

	@Override
	public void draw() {
		_view.draw();
		
		if(SAVE_POWER) {
			noLoop();
		}
	}
	
	
	@Override
	public void mousePressed() {
		if(SAVE_POWER) {
			loop();
		}
		if(MainViewClass.DEBUG_MODE) {
			mouseClicks++;
		}
	}
	
	@Override
	public void mouseMoved() {
		if(SAVE_POWER) {
			loop();
		}
		if(MainViewClass.DEBUG_MODE) {
			mouseMoves++;
		}
	}
	
	@Override
	public void mouseDragged() {
		if(SAVE_POWER) {
			loop();
		}
		if(MainViewClass.DEBUG_MODE) {
			mouseMoves++;
		}
	}
	
	@Override
	public void mouseClicked() {
		if(SAVE_POWER) {
			loop();
		}
	}
	
	@Override
	public void printErrorMessage(String errorMessage, String methodName) {
		System.err.println(this.getClass().getSimpleName() + "." + methodName
				+ "():" + errorMessage);
	}

	/**
	 * @return the view
	 */
	public GraphicalUserInterfaceView getView() {
		return _view;
	}

	/**
	 * @return the mouseMoved
	 */
	public int getMouseMoved() {
		return mouseMoves;
	}

	/**
	 * @return the mouseClicked
	 */
	public int getMouseClicked() {
		return mouseClicks;
	}
}
