/**
 * 
 */
package info.christianillies.framework.events;

/**
 * implementation of a simple IEvent which just have a eventname.<br>
 * can be used in most cases when no further data is needed.<br>
 * all essential methods like equals, hasCode and toString are implemented, too.
 * @author christian illies
 *
 */
public class Event implements IEvent {

	/**
	 * the eventname
	 */
	private final String eventName;

	/**
	 * constructor sets the name of the event
	 */
	public Event(String eventName) {
		this.eventName = eventName;
	}

	@Override
	public String getEventName() {
		return eventName;
	}
	
	@Override
	public String toString() {
		return getEventName();
	}
	
	@Override
	public boolean equals(Object obj) {
		return eventName.equals(obj.toString());
	}
	
	@Override
	public int hashCode() {
		return eventName.hashCode();
	}
}
