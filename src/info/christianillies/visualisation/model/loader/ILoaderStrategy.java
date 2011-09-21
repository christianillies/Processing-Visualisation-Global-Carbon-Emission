package info.christianillies.visualisation.model.loader;

import info.christianillies.framework.events.Event;
import info.christianillies.framework.events.IEvent;
import info.christianillies.framework.events.IEventDispatcher;


/**
 * strategy pattern to load files. extends runnable to allocate loading files that can take a while.<br>
 * event FILE_LOADED_SUCCESSFULLY will be dispatched if the file was successfully loaded.
 * 
 * @author christian illies
 */
public interface ILoaderStrategy extends Runnable, IEventDispatcher {

	/**
	 * Event for successful file loading
	 */
	IEvent FILE_LOADED_SUCCESSFULLY = new Event("fileLoadedSuccessfully");
	
	/**
	 * dispatched if file loading fails
	 */
	IEvent FILE_LOAD_ERROR = new Event("fileLoadError");
	
    /**
     * loads a file and returns true on success. alternativly you can run this method in a separate thread to improve performance. runnable interface is already implemented.
     * 
     * @return true on success, otherwise false.
     */
    boolean loadFile();

    /**
     * @param fileName
     *            the fileName to set
     */
    public void setFileName(String fileName);

    /**
     * @return the fileName
     */
    public String getFileName();
}
