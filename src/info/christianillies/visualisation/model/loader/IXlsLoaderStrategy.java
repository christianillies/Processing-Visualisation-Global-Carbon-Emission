package info.christianillies.visualisation.model.loader;

import de.bezier.data.XlsReader;

/**
 * xls file loader strategy extends ILoaderStrategy and adds a method to return a xlsReader to access the xls file.
 * @author christian illies
 * @see de.bezier.data.XlsReader
 */
public interface IXlsLoaderStrategy extends ILoaderStrategy {
	/**
	 * returns the xlsReader to access the already loaded xls file
	 * @return XlsReader
	 */
	XlsReader getXlsReader();
}
