package info.christianillies.visualisation.view;

import info.christianillies.framework.view.AbstractView;

/**
 * abstract visualisation view can set indicator and current year. subclass has to draw the visualisation itself.
 * @author christian illies
 *
 */
public abstract class AbstractVisualisationView extends AbstractView {

	/**
	 * the current year to use
	 */
	protected String _currentYear;
	/**
	 * the current indicator index
	 */
	protected int _indicatorIndex;

	/**
	 * sets the current year to the overgiven parameter
	 * @param pYear as string in format YYYY (i.e. 1985)
	 */
	public void setCurrentYear(String pYear) {
		_currentYear = pYear;
	}
	
	/**
	 * sets the indicator. current possible values are 0 (carbon emission in kt), 1 (carbon emission in tons per capita)
	 * @param pIndicatorIndex as integer value, 0 or 1
	 */
	public void setIndicatorToUse(int pIndicatorIndex) {
		_indicatorIndex = pIndicatorIndex;
	}

	@Override
	public boolean equals(Object pObj) {
		return this.getClass().getCanonicalName().equals(pObj.getClass().getCanonicalName());
	}
	
	@Override
	public int hashCode() {
		return this.getClass().getCanonicalName().hashCode();
	}
}
