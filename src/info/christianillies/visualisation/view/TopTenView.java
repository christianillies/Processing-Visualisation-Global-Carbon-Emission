package info.christianillies.visualisation.view;

import info.christianillies.framework.common.FrameworkConstants;
import info.christianillies.framework.view.AbstractView;
import info.christianillies.visualisation.model.XlsWorldDatabankModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import processing.core.PApplet;

/**
 * shows a small panel about countries with the most carbon emission.
 * @author christian illies
 *
 */
public class TopTenView extends AbstractView {
	/**
	 * singleton instance
	 */
	private static final TopTenView instance = new TopTenView();
	
	/**
	 * papplet instance
	 */
	private PApplet _papplet;

	/**
	 * max values. default 10 for top TEN. could be more and the panel size will grow automatically.
	 */
	public static final int MAX_NUM_OF_VALUES = 20;
	
	/**
	 * private constructor assigns papplet.
	 */
	private TopTenView() {
		_papplet = FrameworkConstants.getPappletInstance();
	}
	
	/**
	 * returns the singleton instance
	 * @return singleton instance
	 */
	public static TopTenView getInstance(){
		return instance;
	}
	
	@Override
	public void draw() {
		/*
		 * save styles
		 */
		_papplet.pushStyle();
		
		/*
		 * position,colors and size
		 */
		final int lineHeight = 18;
		
		final int rectWidth			= 180;
		final int rectHeight		= lineHeight * MAX_NUM_OF_VALUES + 10;
		final int rectX 			= _papplet.width - rectWidth - 15;
		final int rectY 			= 220;
		
		final int startYPos = rectY + 20;
		final int textXPos = rectX + 10;
		
		final int backgroundColor 	= _papplet.color(0,200);
		final int textColor 		= _papplet.color(164,166,106);
		
		/*
		 * Background
		 */
		_papplet.fill(backgroundColor);
		_papplet.rect(rectX, rectY, rectWidth, rectHeight);

		/*
		 * text headline
		 */
		_papplet.fill(textColor);
		_papplet.text("Top Ten", rectX, rectY);
		
		int lineIndex = 0;
		
		final String[] topTenValues = getTopTenValues();
		
		/*
		 * text top ten
		 */
		for (int i = 0; i < topTenValues.length; i++) {
			_papplet.text((i+1)+". ", textXPos, startYPos + lineHeight*lineIndex++);
			if(i < 9) {
				_papplet.text("  ");
			}
			_papplet.text(topTenValues[i]);
		}
		
		/*
		 * restore saved styles
		 */
		_papplet.popStyle();
	}

	/**
	 * returns topten values as string array or null if model is null 
	 * @return
	 */
	private String[] getTopTenValues() {
		if(getModel() == null) {
			return null;
		}
		/*
		 * constant attributes
		 */
		final XlsWorldDatabankModel xlsmodel = (XlsWorldDatabankModel) getModel();
		final String[] countryNames = xlsmodel.getCountryNames();
		final HashMap<Float, String> dataValues = new HashMap<Float, String>();
		final String[] ret = new String[MAX_NUM_OF_VALUES];
		
		for (int i = 0; i < countryNames.length; i++) {
			dataValues.put(xlsmodel.setCountry(i).value(), countryNames[i]);
		}
		
		if(dataValues.size() <= 1) {
			return new String[0];
		}
		
		Set<Float> valuesAsCollection = dataValues.keySet();
		List<Float> valuesAsSortedList = new ArrayList<Float>();
		valuesAsSortedList.addAll(valuesAsCollection);
		
		Collections.sort(valuesAsSortedList);
		
		for (int i = 0; i < MAX_NUM_OF_VALUES; i++) {
			final float currentKey = valuesAsSortedList.get(dataValues.size() - 1 - i);
			final String currentCountry = dataValues.get(currentKey);
			ret[i]  = currentCountry;
//			ret[i] += " (" + String.valueOf(Math.round(currentKey)) + ")";
		}
		
		return ret;
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
