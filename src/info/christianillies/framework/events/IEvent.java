package info.christianillies.framework.events;

/**
 * simple event interface
 * @author christian illies
 *
 */
public interface IEvent {
	/**
	 * returns data as string that can be used from the eventlistener
	 * @return string
	 */
	String getEventName();
}
