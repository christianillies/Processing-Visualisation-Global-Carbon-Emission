package info.christianillies.visualisation.view;

import processing.core.PApplet;
import info.christianillies.framework.common.FrameworkConstants;
import info.christianillies.framework.view.AbstractView;

/**
 * shows a legend. small circles for small emissions and big circles for big emissions.
 * @author christian illies
 *
 */
public class LegendView extends AbstractView {

	/**
	 * papplet instance
	 */
	private PApplet _p;

	/**
	 * constructor assigns papplet
	 */
	public LegendView() {
		_p = FrameworkConstants.getPappletInstance();
	}
	
	@Override
	public void draw() {
		final int xPos = 30;
		final int yPos = _p.height-75;
		final int bigSize = 50;
		final int smallSize = 15;
		
		_p.fill(95,103,107);
		_p.ellipse(xPos, yPos, bigSize,bigSize);
		_p.ellipse(xPos, yPos+bigSize, smallSize, smallSize);
		
		_p.fill(230,245,154);
		_p.text("high emission", xPos+bigSize/2+10, yPos+2);
		_p.text("low emission", xPos+bigSize/2+10, yPos+bigSize+2);
	}

	@Override
	public boolean equals(Object pObj) {
		return this.getClass() == pObj.getClass();
	}
	
	@Override
	public int hashCode() {
		return this.getClass().getCanonicalName().hashCode();
	}
}
