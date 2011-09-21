package info.christianillies.framework.controller;

import info.christianillies.framework.events.IEventListener;
import info.christianillies.framework.model.IDataModel;
import info.christianillies.framework.view.AbstractView;

/**
 * controller for datamodel and all views<br>
 * extends IEventListener listening for view events
 * @author christian illies
 */
public interface IController extends IEventListener {
	
	/**
	 * sets the model
	 * @param model to set
	 */
	void setModel(IDataModel model);
	
	/**
	 * returns the data model
	 * @return reference to the model
	 */
	IDataModel getModel();
	
	/**
	 * sets the view object
	 * @param view to set
	 */
	void setView(AbstractView view);
	
	/**
	 * gets the view object
	 * @return a reference to the current view object
	 */
	AbstractView getView();
}
