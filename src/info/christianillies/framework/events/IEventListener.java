package info.christianillies.framework.events;

/**
 * Eventlistener is listening for dispatched events
 * @author christian Illies
 *
 */
public interface IEventListener {
	/**
	 * reacts on specific events
	 * @param event the event object
	 * @param dispatcher which sent off the event
	 */
	void onEvent(IEvent event, IEventDispatcher dispatcher);
}
