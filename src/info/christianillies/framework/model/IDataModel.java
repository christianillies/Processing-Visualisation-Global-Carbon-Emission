package info.christianillies.framework.model;

import info.christianillies.framework.events.Event;
import info.christianillies.framework.events.IEvent;
import info.christianillies.framework.events.IEventDispatcher;


/**
 * interface for different datamodels (i.e. xml, xls, txt, sql, ...)
 * @author christian illies
 *
 */
public interface IDataModel extends IEventDispatcher {
	
	/**
	 * Event for every model that changed its state
	 */
	IEvent MODEL_HAS_CHANGED = new Event("modelHasChanged");
	
	/**
	 * Event for successfully loaded model data
	 */
	IEvent MODEL_DATA_LOADED = new Event("modelDataLoaded");
	
	/**
	 * converts data to an array
	 * @return an array with all of the data
	 */
	Object[] toArray();
	
}
