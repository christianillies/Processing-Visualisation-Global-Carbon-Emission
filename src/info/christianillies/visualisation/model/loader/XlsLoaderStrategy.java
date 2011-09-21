package info.christianillies.visualisation.model.loader;

import info.christianillies.framework.common.FrameworkConstants;
import info.christianillies.framework.events.EventDispatcher;
import processing.core.PApplet;
import de.bezier.data.XlsReader;

/**
 * Loader class for loading xls files 
 * 
 * @author christian illies
 * 
 */
public class XlsLoaderStrategy extends EventDispatcher implements IXlsLoaderStrategy {

	/**
	 * xls reader to read xls files
	 */
	private XlsReader xlsReader;

	/**
	 * papplet instance
	 */
	private final PApplet pappletInstance;

	/**
	 * filename which contains xls data
	 */
	private String _fileName;

	/**
	 * constructor that assigns papplet
	 * 
	 * @param pappletInstance
	 */
	public XlsLoaderStrategy() {
		this.pappletInstance = FrameworkConstants.getPappletInstance();
		_fileName = null;
	}

	@Override
	public boolean loadFile() {
		boolean ret = false;

		xlsReader = new XlsReader(this.pappletInstance, _fileName);

		try {
			xlsReader.firstCell();
			
			/*
			 * if firstCell is successful, loading file was successful, so return value is true
			 */
			
			ret = true;
		} catch (NullPointerException e) {
			xlsReader = null;
		}

		return ret;
	}

	@Override
	public XlsReader getXlsReader() {
		return xlsReader;
	}

	@Override
	public String getFileName() {
		return _fileName;
	}

	@Override
	public void setFileName(String fileName) {
		_fileName = fileName;
	}

	@Override
	public void run() {
		if(loadFile()) {
			dispatchEvent(FILE_LOADED_SUCCESSFULLY);
		} else {
			dispatchEvent(FILE_LOAD_ERROR);
		}
	}
}
