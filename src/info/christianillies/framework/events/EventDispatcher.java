/**
 * 
 */
package info.christianillies.framework.events;

import java.util.ArrayList;

/**
 * implementation of IEventDispatcher for classes that do not need to extend another class.
 * @author christian illies
 *
 */
public class EventDispatcher implements IEventDispatcher {
	
	/**
	 * all eventlisteners are stored in an arraylist
	 */
	private ArrayList<IEventListener> listeners = new ArrayList<IEventListener>();
	
	/**
	 * default constructor does nothing
	 */
	public EventDispatcher() {}

	@Override
	public void dispatchEvent(final IEvent event) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onEvent(event, this);
		}
	}

	@Override
	public void addEventListener(IEventListener eventlistener) {
		if(!listeners.contains(eventlistener))
		{
			listeners.add(eventlistener);
		}
	}

	@Override
	public IEventListener removeEventListener(IEventListener eventlistener) {
		IEventListener ret = null;
		int index = listeners.indexOf(eventlistener);
		if (index != -1) {
			ret = listeners.get(index);
			listeners.remove(index);
		}
		return ret;
	}
}
