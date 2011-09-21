/**
 * 
 */
package info.christianillies.visualisation.model;

import info.christianillies.framework.common.IErrorHandler;
import info.christianillies.framework.events.EventDispatcher;
import info.christianillies.framework.events.IEvent;
import info.christianillies.framework.events.IEventDispatcher;
import info.christianillies.framework.events.IEventListener;
import info.christianillies.framework.model.IDataModel;
import info.christianillies.visualisation.model.loader.ILoaderStrategy;
import info.christianillies.visualisation.model.loader.IXlsLoaderStrategy;

import java.util.ArrayList;

import de.bezier.data.XlsReader;

/**
 * an xls model for standard xls files. first row with column descriptions and first column with country names for example.
 * 
 * @author christian illies
 * 
 */
public class XlsDataModel extends EventDispatcher
		implements
			IDataModel,
			IErrorHandler,
			IEventListener {

	/**
	 * the file name
	 */
	private String _xlsFileName;
	
	/**
	 * the file load strategy for different file types 
	 */
	private IXlsLoaderStrategy _loadStrategy;
	
	/**
	 * the xls reader for reading xls files
	 */
	protected XlsReader _xlsReader;
	
	/**
	 * contains the data of the model
	 */
	protected ArrayList<ArrayList<Object>> _modelData;

	/**
	 * constructor takes filename and papplet instance
	 * 
	 * @param xlsFileName
	 *            path to xls file
	 * @param pappletInstance
	 *            papplet instance
	 */
	public XlsDataModel(String xlsFileName) {
		this._xlsFileName = xlsFileName;
		this._xlsReader = null;
	}

	/**
	 * loads the xls file s because multiple threads could trigger
	 * exceptions.
	 * 
	 * @return true if loading was successful, otherwise false.
	 */
	public boolean loadXlsFile() {
		/*
		 * multi threaded
		
		Thread fileLoadThread = new Thread(_loadStrategy);
		fileLoadThread.start();
		
		return true;
		 */
		
		boolean ret = false;
		
		synchronized(_loadStrategy) {
			 ret = _loadStrategy.loadFile();
		}
		
		if (ret) {
			_xlsReader = _loadStrategy.getXlsReader();
			
			/*
			 * xls file is loaded now we can compute the data which can take a
			 * while
			 */
			loadModelData();

		} else {
			printErrorMessage("couldn't load the xls file", "loadXlsFile");
		}
		return ret;
	}

	/**
	 * loads the whole xls file and caches it into attributes<br>
	 * all rows will be saved in a dynamic array
	 */
	@SuppressWarnings("unchecked")
	protected void loadModelData() {
		
		ArrayList<ArrayList<Object>> rows = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> oneRow = new ArrayList<Object>();
		
		_xlsReader.firstRow();
		
		while(_xlsReader.hasMoreRows()) {
			oneRow.clear();
			try {
				oneRow.add(_xlsReader.getString());
			} catch(IllegalStateException exception) {
				oneRow.add(_xlsReader.getFloat());
			}
			
			while(_xlsReader.hasMoreCells()) {
				_xlsReader.nextCell();
				
				try {
					oneRow.add(_xlsReader.getString());
				} catch(IllegalStateException exception) {
					oneRow.add(_xlsReader.getFloat());
				}
			}
			
			rows.add((ArrayList<Object>) oneRow.clone());
			
			_xlsReader.nextRow();
		}
		_modelData = rows;
	}

	@Override
	public void printErrorMessage(String errorMessage, String methodName) {
		System.err.println(this.getClass().getSimpleName() + "." + methodName
				+ "(): " + errorMessage);
	}

	/*
	 * returns an object array with length 0 to avoid null pointer exceptions
	 * (non-Javadoc)
	 * 
	 * @see info.christianillies.framework.model.IDataModel#toArray()
	 */
	@Override
	public Object[] toArray() {
		if (_modelData == null) {
			printErrorMessage(
					"model data is null. you have to correctly load the xls file first.",
					"toArray");
			return new Object[0];
		}

		return _modelData.toArray();
	}

	/**
	 * sets the load strategy for loading the xls file
	 * 
	 * @param loadStrategy
	 *            an object that implements IXlsLoaderStrategy
	 */
	public void setLoadStrategy(IXlsLoaderStrategy loadStrategy) {
		this._loadStrategy = loadStrategy;
		loadStrategy.setFileName(_xlsFileName);
	}

	/**
	 * returns the xls file loader strategy
	 * 
	 * @return the loader strategy for loading xls files.
	 */
	public IXlsLoaderStrategy getLoadStrategy() {
		return _loadStrategy;
	}

	@Override
	public void onEvent(IEvent pEvent, IEventDispatcher pDispatcher) {
		if(pEvent.equals(ILoaderStrategy.FILE_LOADED_SUCCESSFULLY)) {
			/* event forwarding  */
			dispatchEvent(ILoaderStrategy.FILE_LOADED_SUCCESSFULLY);
			_xlsReader = _loadStrategy.getXlsReader();
			/*
			 * after xls file loads correctly we can initialize model data
			 */
			loadModelData();
		} else {
			dispatchEvent(ILoaderStrategy.FILE_LOAD_ERROR);
		}
	}

	/**
	 * @param xlsFileName the xlsFileName to set
	 */
	public void setXlsFileName(String xlsFileName) {
		_xlsFileName = xlsFileName;
	}

	/**
	 * @return the xlsFileName
	 */
	public String getXlsFileName() {
		return _xlsFileName;
	}
}
