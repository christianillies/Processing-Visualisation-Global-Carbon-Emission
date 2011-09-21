package info.christianillies.visualisation.view;

import info.christianillies.framework.common.FrameworkConstants;
import info.christianillies.framework.events.Event;
import info.christianillies.framework.events.IEvent;
import info.christianillies.framework.model.IDataModel;
import info.christianillies.framework.view.AbstractView;
import info.christianillies.visualisation.model.XlsWorldDatabankModel;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import controlP5.Button;
import controlP5.CColor;
import controlP5.ControlEvent;
import controlP5.ControlFont;
import controlP5.ControlListener;
import controlP5.ControlP5;
import controlP5.Slider;

/**
 * graphical user interface with buttons and sub-views (i.e. legend).
 * @author christian illies
 */
public class GraphicalUserInterfaceView extends AbstractView {

	/**
	 * image dir
	 */
	private static final String IMAGE_PATH = FrameworkConstants.getProjectDir() + "images/layout/";
	/**
	 * header image path
	 */
	private static final String IMAGE_HEADER = IMAGE_PATH + "headergraphic.jpg";
	/**
	 * background image path
	 */
	private static final String IMAGE_BACKGROUND_WORLDMAP = IMAGE_PATH + "backgroundWorldmap.jpg";
	
	/**
	 * event for button 'by land'
	 */
	public static final IEvent BUTTON_BY_LAND_CLICK_EVENT = new Event("buttonByLandClicked");

	/**
	 * event for button 'per capita'
	 */
	public static final IEvent BUTTON_PER_CAPITA_CLICK_EVENT = new Event("buttonPerCapitaClicked");

	/**
	 * event for button 'info'
	 */
	public static final IEvent BUTTON_INFO_CLICK_EVENT = new Event("buttonInfoClicked");

	/**
	 * event for button 'topten'
	 */
	public static final IEvent BUTTON_TOPTEN_CLICK_EVENT = new Event("buttonTopTenClicked");
	
	/**
	 * papplet instance
	 */
	private PApplet _papplet;

	/**
	 * arraylist with all subviews
	 */
	private ArrayList<AbstractView> _componentList;

	/**
	 * background color 
	 */
	private int _backgroundColor;

	/**
	 * pimage instance for header bitmap
	 */
	private PImage _headerGraphic;
	/**
	 * pimage object for background image
	 */
	private PImage _backgroundImage;
	/**
	 * global color style for buttons other interface elements
	 */
	private CColor _colorStyle;
	/**
	 * framework controlp5 instance to display button and other interface elements
	 */
	private ControlP5 _controlp5;
	/**
	 * controlp5 year slider 
	 */
	private Slider _yearSlider;
	/**
	 * a list of all available buttons to check them for hover effects.
	 */
	private ArrayList<Button> _buttonList;
	
	/**
	 * contructor assigns model and initialize all attributes
	 * @param model the data model
	 */
	public GraphicalUserInterfaceView(IDataModel model) {
		setModel(model);
		
		_papplet = FrameworkConstants.getPappletInstance();
		_componentList = new ArrayList<AbstractView>();
		_buttonList = new ArrayList<Button>();
		
		_backgroundColor = _papplet.color(48,42,39);
		
		_headerGraphic = _papplet.loadImage(IMAGE_HEADER);
		_backgroundImage = _papplet.loadImage(IMAGE_BACKGROUND_WORLDMAP);
		
//		_animator = new Tween(_papplet, 3f, Tween.SECONDS);
//		_animator.setEasing(Shaper.COSINE);
//		_animator.setPlayMode(Tween.REVERSE_REPEAT);
//		_animator.start();
		
		_colorStyle = new CColor();
		_colorStyle.setActive(		_papplet.color(164,166,106));
		_colorStyle.setAlpha(255);
		_colorStyle.setBackground(	_papplet.color(95,103,107));
		_colorStyle.setCaptionLabel(_papplet.color(230,245,154));
		_colorStyle.setForeground(	_papplet.color(200,222,89));
		_colorStyle.setValueLabel(	_papplet.color(230,245,154));
		
		_controlp5 = new ControlP5(_papplet);
		_controlp5.setAutoDraw(false);
		
		_controlp5.setColorActive(_colorStyle.getActive());
		_controlp5.setColorBackground(_colorStyle.getBackground());
		_controlp5.setColorForeground(_colorStyle.getForeground());
		_controlp5.setColorLabel(_colorStyle.getCaptionLabel());
		_controlp5.setColorValue(_colorStyle.getValueLabel());
		
		/*
		 * font style
		 */
		_controlp5.setControlFont(new ControlFont(_papplet.createFont("Verdana", 12)));
		
		/*
		 * buttons...
		 */
		int buttonIndex = 0;
		
		Button buttonByLand = _controlp5.addButton("PER LAND", 1, 291 + 80*buttonIndex + 5*buttonIndex++, 100, 80, 20);
		buttonByLand.addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent pArg0) {
				dispatchEvent(BUTTON_BY_LAND_CLICK_EVENT);
			}
		});
		
		Button buttonByCapita = _controlp5.addButton("PER CAPITA", 2, 291 + 80*buttonIndex + 5*buttonIndex++, 100, 80, 20);
		buttonByCapita.addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent pArg0) {
				dispatchEvent(BUTTON_PER_CAPITA_CLICK_EVENT);
			}
		});
		
		/*
		 * space
		 */
		buttonIndex++;
		
		/*
		 * more buttons...
		 */
		final Button topTenButton = _controlp5.addButton("TOP "+TopTenView.MAX_NUM_OF_VALUES, 3, 291 + 80*buttonIndex + 5*buttonIndex++, 100, 80, 20);
		topTenButton.addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent pArg0) {
				dispatchEvent(BUTTON_TOPTEN_CLICK_EVENT);
			}
		});
		topTenButton.setSwitch(true);
		
		final Button infoButton = _controlp5.addButton("Info", 3, 291 + 80*buttonIndex + 5*buttonIndex++, 100, 80, 20);
		infoButton.addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent pArg0) {
				dispatchEvent(BUTTON_INFO_CLICK_EVENT);
			}
		});
		infoButton.setSwitch(true);
		
		Button exitButton = _controlp5.addButton("Exit", 3, _papplet.width-50-80, 100, 80, 20);
		exitButton.addListener(new ControlListener() {
			@Override
			public void controlEvent(ControlEvent pArg0) {
				System.out.println("Application terminated.");
				System.exit(0);
			}
		});
		
		/*
		 * add buttons to an arraylist to add hover effects 
		 */
		_buttonList.add(infoButton);
		_buttonList.add(exitButton);
		_buttonList.add(buttonByCapita);
		_buttonList.add(buttonByLand);
		
		/*
		 * slider configuration
		 */ 
		 final XlsWorldDatabankModel tmpXlsModel = (XlsWorldDatabankModel) _model;
		 final String[] tmpYears = tmpXlsModel.getYears();
		 final int startYear = Integer.valueOf(tmpYears[0]);
		 final int endYear = Integer.valueOf(tmpYears[tmpYears.length-1]);
		 
		_yearSlider = _controlp5.addSlider("YEAR", startYear, endYear);
		_yearSlider.setNumberOfTickMarks((int) 51);
		_yearSlider.setDecimalPrecision(0);
		_yearSlider.setPosition(291, 130);
		_yearSlider.setWidth(_papplet.width-291-50);
		_yearSlider.setHeight(20);
		_yearSlider.setSliderMode(Slider.FLEXIBLE);
		_yearSlider.addListener(new ControlListener() {
			/**
			 * old year value
			 */
			private float oldValue = 0;
			@Override
			public void controlEvent(ControlEvent pArg0) {
				if(_yearSlider.value() != oldValue) {
					dispatchEvent(new Event(String.valueOf(_yearSlider.value())));
					oldValue = _yearSlider.value();
				}
			}
		});
	}

	@Override
	public void draw() {
		_papplet.background(_backgroundColor);
		_papplet.image(_headerGraphic, 0, 0);
		
		/*
		 * background image: worldmap
		 */
		final int imageX = _papplet.width/2 - _backgroundImage.width/2;
		final int imageY = _headerGraphic.height + 40;
		_papplet.image(_backgroundImage,  imageX, imageY);
		
		/*
		 * draw all sub-views
		 */
		for (int i = 0; i < _componentList.size(); i++) {
			_componentList.get(i).draw();
		}
		
		/*
		 * button hover ...
		 */
		checkButtonsForHoverEffect();
		
		/*
		 * draw control interface
		 */
		_controlp5.draw();
		
		/*
		 * mouse Event
		 */
		if(_papplet.mousePressed) {
			mousePressed();
		}
		
		/*
		 * key event
		 */
		if(_papplet.keyPressed) {
			keyPressed();
		}
	}
	
	/**
	 * checks all buttons for mouse position. if the mouse's over the button, the color of the label changes.
	 */
	private void checkButtonsForHoverEffect() {
		for (int i = 0; i < _buttonList.size(); i++) {
			final Button tmpButton = _buttonList.get(i);
			if(tmpButton.isInside()) {
				tmpButton.setColorCaptionLabel(_colorStyle.getBackground());
			} else {
				tmpButton.setColorCaptionLabel(_colorStyle.getCaptionLabel());
			}
		}
	}

	/**
	 * does something when a key is pressed
	 */
	protected void keyPressed() {
		dispatchEvent(KEY_PRESSED_EVENT);
	}

	/**
	 * does something if the mouse is pressed
	 */
	protected void mousePressed() {
		dispatchEvent(CLICK_EVENT);
	}

	@Override
	public void addViewComponent(AbstractView pComponent) {
		if(_componentList.contains(pComponent)) {
			return;
		}
		_componentList.add(pComponent);
	}
	
	@Override
	public AbstractView getChild(int pIndex) {
		return _componentList.get(pIndex);
	}
	
	@Override
	public ArrayList<AbstractView> getViewComponents() {
		return _componentList;
	}
	
	@Override
	public void removeViewComponent(AbstractView pComponent) {
		_componentList.remove(pComponent);
	}
	
	@Override
	public void removeAllViewComponents() {
		_componentList.clear();
	}
}
