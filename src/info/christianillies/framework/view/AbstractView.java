package info.christianillies.framework.view;

import info.christianillies.framework.controller.IController;
import info.christianillies.framework.events.Event;
import info.christianillies.framework.events.EventDispatcher;
import info.christianillies.framework.events.IEvent;
import info.christianillies.framework.events.IEventDispatcher;
import info.christianillies.framework.events.IEventListener;
import info.christianillies.framework.model.IDataModel;

import java.util.ArrayList;

/**
 * abstract view class for all viewable objects.<br>
 * extends EventDispatcher because of clickEvents for example<br>
 * integrates the composite pattern for displaying other view objects
 * @author christian illies
 *
 */
public abstract class AbstractView extends EventDispatcher implements IEventListener {

	/**
	 * event for clicks
	 */
	public static final IEvent CLICK_EVENT = new Event("viewHasBeenClicked");

	/**
	 * will be dispatched if key was pressed
	 */
	public static final IEvent KEY_PRESSED_EVENT = new Event("keyPressedEvent");

	/**
	 * the model
	 */
	protected IDataModel _model;
	
	/**
	 * the controller
	 */
	protected IController _controller;

	/**
	 * default constructor.
	 */
	public AbstractView() {
		_model = null;
		_controller = null;
	}
	
	/**
	 * constructor assigns the model and controller to the view
	 * @param model reference to the model object
	 * @param controller reference to the controller object
	 */
	public AbstractView(IDataModel model, IController controller) {
		_model = model;
		_controller = controller;
	}
	
	/**
	 * draws the component and its childcomponents 
	 */
	public abstract void draw();
	
	/**
	 * reacts on model change events
	 */
	@Override
	public void onEvent(IEvent pEvent, IEventDispatcher pDispatcher) {
		/*
		 * does nothing
		 */
	}
	
	/**
	 * sets the data model 
	 * @param model 
	 */
	public void setModel(IDataModel pModel) {
		if(_model != null) {
			_model.removeEventListener(this);
		}
		_model = pModel;
		_model.addEventListener(this);
	}

	/**
	 * returns the reference to the model
	 * @return reference to the data model
	 */
	public IDataModel getModel() {
		return _model;
	}

	/**
	 * sets the controller to the overgiven
	 * @param controller
	 */
	public void setController(IController pController) {
		_controller = pController;
	}

	/**
	 * returns the controller reference
	 * @return reference to the controller
	 */
	public IController getController() {
		return _controller;
	}

	/**
	 * add components to this object
	 * @param comp new child-screencomponent (leaf or another composite)
	 */
	public void addViewComponent(AbstractView component){ }
	
	/**
	 * removes a childcomponent from the list
	 * @param comp the object that have to be removed
	 */
	public void removeViewComponent(AbstractView component) { }
	
	/**
	 * removes all child-components
	 */
	public void removeAllViewComponents() {}
	
	/**
	 * returns all components as arraylist
	 * @return arraylist
	 */
	public ArrayList<AbstractView> getViewComponents() { return null; }
	
	/**
	 * returns a specific components with the aid of pIndex
	 * @param pIndex index in the arraylist
	 * @return child component
	 */
	public AbstractView getChild(int pIndex) { return null; }
}
