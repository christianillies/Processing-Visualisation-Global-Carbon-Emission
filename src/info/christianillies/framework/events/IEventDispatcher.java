package info.christianillies.framework.events;

/**
 * event dispatcher are able to set off events, add and remove eventlisteners
 * 
 * @author christian illies
 *
 */
public interface IEventDispatcher {
	/**
	 * dispatches an event and notify all eventlistener
	 * @param event this event will be dispatched
	 */
	void dispatchEvent(IEvent event);
	
	/**
	 * adds a listener to a list that will be notified when an event is dispatched
	 * @param eventlistener
	 */
	void addEventListener(IEventListener eventlistener);
	
	/**
	 * removes an eventlistener and returns the object or NULL if no object were found
	 * @param eventlistener eventlistener to remove
	 * @return the removed eventlistener or NULL if no object were removed
	 */
	IEventListener removeEventListener(IEventListener eventlistener);
}
